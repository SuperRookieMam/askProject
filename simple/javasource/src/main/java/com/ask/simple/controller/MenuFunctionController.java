package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.MenuFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menuFunction")
public class MenuFunctionController extends BaseController<MenuFunction,Long> {

}
