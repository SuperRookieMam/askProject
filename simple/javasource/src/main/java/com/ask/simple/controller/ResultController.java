package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 答案接口
 * @author  yinjie
 * @date 2019年7月16日09:23:27
 */
@RestController
@RequestMapping("result")
public class ResultController extends BaseController<Result,Long> {

}
