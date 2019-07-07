package com.ask.simple.service.impl;

import com.ask.base.service.impl.BaseServiceImpl;
import com.ask.orm.componet.feature.DynamicTypeSelect;
import com.ask.simple.entity.MenuFunction;
import com.ask.simple.entity.MenuFunctionRole;
import com.ask.simple.entity.UserRole;
import com.ask.simple.service.MenuFunctionRoleService;
import com.ask.simple.service.MenuFunctionService;
import com.ask.simple.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuFunctionRoleServiceImpl extends BaseServiceImpl<MenuFunctionRole, Long> implements MenuFunctionRoleService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuFunctionService menuFunctionService;

    @Override
    public List<MenuFunction>  getMenuFunctionByUserName(String userName) {
      List<UserRole> userRoles = userRoleService.getUserRoleByUsername(userName);
      if (userRoles.isEmpty()){
          return Collections.emptyList();
      }
      DynamicTypeSelect dynamicTypeSelect = getBaseRepository().getDynamicTypeSelect();
      dynamicTypeSelect.dynamicBuild(ele -> {
            Predicate predicate = ele.flat.addEq("roleId",userRoles.get(0).getRoleId(), JoinType.LEFT).and();
            ele.query.where(predicate);
           return predicate;
        });
        List<MenuFunctionRole> menuFunctionRoles =  dynamicTypeSelect.getResult();
        if (menuFunctionRoles.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> functionIds = menuFunctionRoles.stream()
                                                  .map(ele -> ele .getMenuFunctionId())
                                                  .collect(Collectors.toList());
        DynamicTypeSelect dynamicTypeSelect1 = menuFunctionService.getBaseRepository().getDynamicTypeSelect();
        dynamicTypeSelect1.dynamicBuild(ele -> {
           Predicate predicate = ele.flat.addIn("id",JoinType.LEFT,functionIds.toArray(new Long[0])).and();
           ele.query.where(predicate);
           return predicate;
        });
        return  dynamicTypeSelect1.getResult();
    }

}
