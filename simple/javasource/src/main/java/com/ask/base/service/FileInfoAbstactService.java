package com.ask.base.service;

import com.ask.base.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface FileInfoAbstactService<T extends BaseEntity,ID extends Serializable>{

    @Transactional
    T save(InputStream inputStream, T _info) throws Exception;
    @Transactional
    T  save(File file, T _info) throws Exception;

    @Transactional
    T  save(File[] src, T fileInfo) throws IOException;

    @Transactional
    void delete(ID id) throws Exception;

    T  findById(ID id);
}
