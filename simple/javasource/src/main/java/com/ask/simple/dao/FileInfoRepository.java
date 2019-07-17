package com.ask.simple.dao;

import com.ask.base.dao.FileInfoAbstructRepository;
import com.ask.simple.entity.FileInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends FileInfoAbstructRepository<FileInfo,String> {
}
