package com.ask.base.service.impl;

import com.ask.base.componet.util.MyFileUtils;
import com.ask.base.dao.FileInfoAbstructRepository;
import com.ask.base.entity.BaseEntity;
import com.ask.base.entity.FileInfoDetails;
import com.ask.base.service.FileInfoAbstactService;
import com.ask.base.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Optional;

public abstract class FileInfoAbstractServiceImpl<T extends BaseEntity,ID extends Serializable> implements FileInfoAbstactService<T ,ID> {

    @Autowired
    private FileInfoAbstructRepository<T,ID> fileInfoRepository;
    @Autowired
    private FileStorageService storageService;
    @Override
    @Transactional
    public T save(InputStream inputStream, T _info) throws Exception {
        T dto = save(_info);
        FileInfoDetails fileInfoDetails = (FileInfoDetails)dto;
        if (storageService.save(fileInfoDetails.getPath(), inputStream)){
            return dto;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }

    @Override
    @Transactional
    public T save(File file, T _info) throws Exception {
        _info = save(_info);
        FileInfoDetails fileInfoDetails =(FileInfoDetails)_info;
        if (storageService.save(fileInfoDetails.getPath(), file)){
            return _info;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }
    @Override
    @Transactional
    public T save(File[] src, T fileInfo) throws IOException {
        fileInfo = save(fileInfo);
        FileInfoDetails fileInfoDetails = (FileInfoDetails)fileInfo;
        if (storageService.save(fileInfoDetails.getPath(), src)){
            return fileInfo;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }
    private T save(T fileInfo) {
        FileInfoDetails fileInfoDetails = (FileInfoDetails)fileInfoRepository.save(fileInfo);
        String filename = fileInfoDetails.getFilename();
        String ext = MyFileUtils.getExt(filename);
        String storagePath = fileInfoDetails.getId() + "." + ext;
        fileInfoDetails.setPath(storagePath);
        fileInfo = (T) fileInfoDetails;
        fileInfo = fileInfoRepository.save(fileInfo);
        return fileInfo;
    }

    @Override
    @Transactional
    public void delete(ID id) throws Exception {
        Optional<T> optional = fileInfoRepository.findById(id);
        if (optional.isPresent()) {
             FileInfoDetails fileInfoDetails =(FileInfoDetails)optional.get();
            storageService.delete(fileInfoDetails.getPath());
        }
        fileInfoRepository.deleteById(id);
    }

    @Override
    public T  findById(ID id){
      Optional<T> optional =  fileInfoRepository.findById(id);
      return optional.isPresent()?optional.get():null;
    }
}
