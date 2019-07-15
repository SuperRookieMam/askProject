package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("result")
public class ResultController extends BaseController<Result,Long> {

}
