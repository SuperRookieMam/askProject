package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Exam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exam")
public class ExamController extends BaseController<Exam,Long> {

}
