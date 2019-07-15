package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Record;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("record")
public class RecordController extends BaseController<Record,Long> {

}
