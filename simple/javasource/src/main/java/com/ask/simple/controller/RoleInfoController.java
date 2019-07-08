package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.RoleInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roleInfo")
public class RoleInfoController extends BaseController<RoleInfo,Long> {
}
