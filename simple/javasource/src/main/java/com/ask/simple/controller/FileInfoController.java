package com.ask.simple.controller;

import com.ask.base.controller.FileInfoAbstactController;
import com.ask.simple.entity.FileInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fileInfos")
public class FileInfoController extends FileInfoAbstactController<FileInfo,String> {

}
