package com.ask.simple.service.impl;

import com.ask.base.service.impl.BaseServiceImpl;
import com.ask.orm.componet.feature.DynamicTypeSelect;
import com.ask.simple.entity.UserRole;
import com.ask.simple.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Long> implements UserRoleService {
    @Override
    public List<UserRole> getUserRoleByUsername(String username){
        DynamicTypeSelect dynamicTypeSelect = getBaseRepository().getDynamicTypeSelect();
        dynamicTypeSelect.dynamicBuild(ele ->{
            Predicate predicate = ele.flat.addEq("userName",username, JoinType.LEFT).and();
            ele.query.where(predicate);
            return  predicate;
        });
        return dynamicTypeSelect.getResult();
    }
}
