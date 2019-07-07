package com.ask.simple.service;


import com.ask.base.service.BaseService;
import com.ask.simple.entity.MenuFunction;
import com.ask.simple.entity.MenuFunctionRole;

import java.util.List;

public interface MenuFunctionRoleService extends BaseService<MenuFunctionRole, Long> {

    List<MenuFunction> getMenuFunctionByUserName(String userName);
}
