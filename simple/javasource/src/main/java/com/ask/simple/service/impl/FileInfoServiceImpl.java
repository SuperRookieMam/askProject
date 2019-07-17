package com.ask.simple.service.impl;

import com.ask.base.service.impl.FileInfoAbstractServiceImpl;
import com.ask.simple.entity.FileInfo;
import com.ask.simple.service.FileInfoService;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServiceImpl extends FileInfoAbstractServiceImpl<FileInfo,String> implements FileInfoService {
}
