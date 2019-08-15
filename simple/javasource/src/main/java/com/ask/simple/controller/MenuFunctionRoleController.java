package com.ask.simple.controller;

import com.ask.base.componet.dto.ResultDto;
import com.ask.base.controller.BaseController;
import com.ask.simple.componet.annotation.CurrentUser;
import com.ask.simple.entity.MenuFunction;
import com.ask.simple.entity.MenuFunctionRole;
import com.ask.simple.entity.OAthUserDetailes;
import com.ask.simple.service.MenuFunctionRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menuFunctionRole")
public class MenuFunctionRoleController extends BaseController<MenuFunctionRole,Long> {
    @Autowired
    private MenuFunctionRoleService menuFunctionRoleService;
    @GetMapping("menus")
    @ApiOperation(value="获取登陆用户能访问的菜单", notes="getMenus")
    public ResultDto getMenus(@CurrentUser OAthUserDetailes userDetailsDto) {
      List<MenuFunction> list = menuFunctionRoleService.getMenuFunctionByUserName(userDetailsDto.getUsername());
      return ResultDto.success(list);
    }
    @GetMapping("user")
    @ApiOperation(value="获取当前用户", notes="getUser")
    public ResultDto getUser(@CurrentUser OAthUserDetailes userDetailsDto) {
        return ResultDto.success(userDetailsDto);
    }
}
