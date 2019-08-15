package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userRole")
public class UserRoleController extends BaseController<UserRole,Long> {

}
