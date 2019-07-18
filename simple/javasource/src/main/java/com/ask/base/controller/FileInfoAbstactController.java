package com.ask.base.controller;
import com.ask.base.componet.config.FileConfig;
import com.ask.base.componet.dto.ResultDto;
import com.ask.base.componet.util.MyFileUtils;
import com.ask.base.entity.BaseEntity;
import com.ask.base.entity.FileInfoDetails;
import com.ask.base.service.FileInfoAbstactService;
import com.ask.base.service.FileStorageService;
import com.ask.base.service.ThumbnailService;
import com.ask.codecreate.util.MyClassUtil;
import com.ask.simple.componet.annotation.CurrentUser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

public class FileInfoAbstactController<T extends BaseEntity,ID extends Serializable>{
    @Autowired
    private FileConfig config;
    @Autowired
    private FileInfoAbstactService<T,ID> fileInfoService;
    @Autowired
    @Lazy
    private ThumbnailService thumbnailService;

    @Autowired
    private FileStorageService fileStorageService;
    /**
     *一次性上传文件
     * */
    @PostMapping
    @Transactional
    public List<T> upload(@CurrentUser UserDetails userDetails, MultipartHttpServletRequest request) throws Exception {
        List<T> result = new ArrayList<>();
        Iterator<String> filenames = request.getFileNames();
        while (filenames.hasNext()) {
            MultipartFile file = request.getFile(filenames.next());
            if (!Objects.isNull(file)) {
                FileInfoDetails fileInfoDetails = (FileInfoDetails) MyClassUtil.getFirstClass(this.getClass()).newInstance();
                if (StringUtils.isNotBlank(file.getOriginalFilename())) {
                    fileInfoDetails.setFilename(MyFileUtils.getOriginalFilename(file.getOriginalFilename()));
                }
                fileInfoDetails.setSize(file.getSize());
                fileInfoDetails = (FileInfoDetails)fileInfoService.save(file.getInputStream(),(T) fileInfoDetails,userDetails);
                result.add((T) fileInfoDetails);
                // 创建缩略图
                 thumbnailService.createThumbnail(fileInfoDetails);
            }
        }
        return result;
    }
    /**
     * 分块上传文件
     *
     * @param chunkIndex
     * @param tempId
     * @param chunkCount
     * @param filename
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(headers = { "chunk-index" })
//	@ApiOperation("文件分块上传文件")''
    public T upload(
            @RequestHeader("chunk-index") Long chunkIndex, // 块
            @RequestHeader("file-id") String tempId,
            @RequestHeader("chunk-count") int chunkCount,
            @RequestParam("filename") String filename,
            @CurrentUser UserDetails userDetails,
            MultipartHttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Collection<MultipartFile> files = request.getFileMap().values();
        if (!CollectionUtils.isEmpty(files)) {
            MultipartFile mFile = files.iterator().next();
            String tempName = config.getTempPath() + tempId + "." + chunkIndex;
            mFile.transferTo(new File(tempName));
        }
        // 这个标记代表块完成
        if (chunkIndex == chunkCount - 1) {
            File target = new File(config.getTempPath() + tempId
                    + "." + StringUtils.substringAfter(filename, "."));
            File[] src = new File[chunkCount];
            for (int i = 0; i < chunkCount; i++) {
                src[i] = new File(config.getTempPath() + tempId + "." + i);
            }
            FileInfoDetails fileInfoDetails = (FileInfoDetails)MyClassUtil.getFirstClass(this.getClass()).newInstance();
            fileInfoDetails.setFilename(filename);
            fileInfoDetails.setSize(target.length());
            fileInfoDetails =(FileInfoDetails) fileInfoService.save(src, (T) fileInfoDetails,userDetails);
            FileUtils.forceDelete(target);
            return (T) fileInfoDetails;
        } else {
            return null;
        }
    }
    //	@ApiOperation("预览文件")
    // 使用produces指定可以接受的accept类型，当accept中包含如下信息时，返回图片
    @GetMapping(value = "preview/{id}", produces = {
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/*" })
    public void preview(@PathVariable("id") ID id, HttpServletResponse response) {
        try {
            FileInfoDetails file = (FileInfoDetails)fileInfoService.findById(id);
            String fileName = file.getFilename();
            String ext = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
            if (!ObjectUtils.isEmpty(config.getMime()) && StringUtils.isNotBlank(ext)) {
                response.setContentType(config.getMime().get(ext));
            }
            try (InputStream is = fileStorageService.getInputStream(file.getPath());
                 OutputStream os = response.getOutputStream();) {
                 IOUtils.copy(is, os);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    /**
     * 获取文件的缩略图
     *
     * @param id       文件的ID号
     * @param level    缩略图的级别
     * @param response
     */
    @GetMapping(value = "thumbnails/{id}")
    public void preview(
            @PathVariable("id") ID id,
            @RequestParam(value = "level", defaultValue = "1") int level,
            HttpServletResponse response) {
        FileInfoDetails file =(FileInfoDetails) fileInfoService.findById(id);
        if (!ObjectUtils.isEmpty(file)) {
            try (InputStream iStream = thumbnailService.getStream(file.getPath(), level);
                 OutputStream oStream = response.getOutputStream()) {
                IOUtils.copy(iStream, oStream);
                response.setContentType("image/jpeg");
            } catch (FileNotFoundException e) {
                response.setStatus(404);
            } catch (IOException e) {
                response.setStatus(500);
            }
        } else {
            response.setStatus(404);
        }
    }

    /*文件下载*/
    @GetMapping(value = "download")
    public void downLoad(
            @RequestParam("path") String path,
            HttpServletRequest request,
            HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 清空输出流
            response.reset();
            response.setCharacterEncoding("UTF-8");
            String resultFileName = URLEncoder.encode(System.currentTimeMillis()+"."+MyFileUtils.getExt(path),"UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);
            response.setContentType(config.getMime().get(MyFileUtils.getExt(path)));
            inputStream =   fileStorageService.getInputStream(path);
            outputStream = new BufferedOutputStream(response.getOutputStream());
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inputStream.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*文件下载*/
    @DeleteMapping("{id}")
    public ResultDto delete(@PathVariable("id") ID id) throws Exception {
        fileInfoService.delete(id);
        return ResultDto.success(1);
    }
}
