package com.ask.simple.service.impl;

import com.ask.base.service.impl.BaseServiceImpl;
import com.ask.orm.componet.feature.DynamicTypeSelect;
import com.ask.simple.entity.OAthUserDetailes;
import com.ask.simple.service.OAthUserDetailesService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class OAthUserDetailesServiceImpl extends BaseServiceImpl<OAthUserDetailes, Long> implements OAthUserDetailesService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAthUserDetailes oAthUserDetailes = findByUsername(username);
        if (ObjectUtils.isEmpty(oAthUserDetailes)){
            throw new UsernameNotFoundException("用户不存在");
        }
        return oAthUserDetailes;
    }

    @Override
    public OAthUserDetailes  findByUsername(String username){
        DynamicTypeSelect dynamicTypeSelect = getBaseRepository().getDynamicTypeSelect();
        dynamicTypeSelect.dynamicBuild(ele->{
            Predicate predicate = ele.flat.addEq("username",username, JoinType.LEFT).and();
            ele.query.where(predicate);
            return predicate;
        });
        List<OAthUserDetailes> list =dynamicTypeSelect.getResult();
        return list.size()>0?list.get(0):null;
    }
    @Override
    public OAthUserDetailes findByCredentials(String credentials){
        DynamicTypeSelect dynamicTypeSelect = getBaseRepository().getDynamicTypeSelect();
        dynamicTypeSelect.dynamicBuild(ele->{
            Predicate predicate = ele.flat.addEq("credentials",credentials, JoinType.LEFT).and();
            ele.query.where(predicate);
            return predicate;
        });
        List<OAthUserDetailes> list =dynamicTypeSelect.getResult();
        return list.size()>0?list.get(0):null;
    }
}
