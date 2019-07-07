package com.ask.simple.service;

import com.ask.base.service.BaseService;
import com.ask.simple.entity.UserRole;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole, Long> {

    List<UserRole> getUserRoleByUsername(String username);
}
