package com.ask.base.service.impl;
import com.ask.base.dao.FileInfoAbstructRepository;
import com.ask.base.entity.BaseEntity;
import com.ask.base.entity.FileInfoDetails;
import com.ask.base.service.FileInfoAbstactService;
import com.ask.base.service.FileStorageService;
import com.ask.orm.componet.feature.DynamicTypeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class FileInfoAbstractServiceImpl<T extends BaseEntity,ID extends Serializable> extends BaseServiceImpl<T ,ID> implements FileInfoAbstactService<T ,ID> {

    @Autowired
    private FileInfoAbstructRepository<T,ID> fileInfoRepository;
    @Autowired
    private FileStorageService storageService;

    @Override
    @Transactional
    public T save(InputStream inputStream, T _info, UserDetails userDetails) throws Exception {
        T dto = save(_info, userDetails);
        FileInfoDetails fileInfoDetails = (FileInfoDetails)dto;
        if (storageService.save(fileInfoDetails, inputStream)){
            fileInfoDetails.setPreviewPath("/preview/" + fileInfoDetails.getId());
            return dto;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }

    @Override
    @Transactional
    public T save(File file, T _info, UserDetails userDetails) throws Exception {
        _info = save(_info,userDetails);
        FileInfoDetails fileInfoDetails =(FileInfoDetails)_info;
        if (storageService.save(fileInfoDetails, file)){
            fileInfoDetails.setPreviewPath("/preview/" + fileInfoDetails.getId());
            return _info;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }
    @Override
    @Transactional
    public T save(File[] src, T fileInfo, UserDetails userDetails) throws Exception {
        fileInfo = save(fileInfo,userDetails);
        FileInfoDetails fileInfoDetails = (FileInfoDetails)fileInfo;
        if (storageService.save(fileInfoDetails.getPath(), src)){
            fileInfoDetails.setPreviewPath("/preview/" + fileInfoDetails.getId());
            return fileInfo;
        }else {
            throw new RuntimeException("上传文件异常");
        }
    }
    private T save(T fileInfo, UserDetails userDetails) throws Exception {
        FileInfoDetails fileInfoDetails = (FileInfoDetails)fileInfo;
        FileInfoDetails fileInfoDetails1 = findByFileName(fileInfoDetails.getFilename());
        fileInfoDetails =ObjectUtils.isEmpty(fileInfoDetails1)
                                ? (FileInfoDetails) fileInfoRepository.save(fileInfo)
                                :fileInfoDetails1;
        String storagePath = storageService.getRelativePath(fileInfoDetails.getFilename(),userDetails);
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
    private FileInfoDetails findByFileName (String fileName) {
       DynamicTypeSelect dynamicTypeSelect = getBaseRepository().getDynamicTypeSelect();
        dynamicTypeSelect.dynamicBuild(ele -> {
            Predicate predicate =ele.flat.addEq("filename",fileName, JoinType.LEFT).and();
            ele.query.where(predicate);
            return predicate;
        });
        List<T> list = dynamicTypeSelect.getResult();
        return list.isEmpty()? null: (FileInfoDetails) list.get(0);
    }

}
