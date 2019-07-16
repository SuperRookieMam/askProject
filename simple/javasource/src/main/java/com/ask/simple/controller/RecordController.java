package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Record;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinjie
 * 答题记录
 * @date 2019年7月16日09:22:51
 */
@RestController
@RequestMapping("record")
public class RecordController extends BaseController<Record,Long> {

}
