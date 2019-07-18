package com.ask.base.service;

import com.ask.base.entity.BaseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

public interface FileInfoAbstactService<T extends BaseEntity,ID extends Serializable> extends BaseService<T,ID>{

    @Transactional
    T save(InputStream inputStream, T _info, UserDetails userDetails) throws Exception;
    @Transactional
    T  save(File file, T _info, UserDetails userDetails) throws Exception;

    @Transactional
    T  save(File[] src, T fileInfo, UserDetails userDetails) throws Exception;

    @Transactional
    void delete(ID id) throws Exception;
}
