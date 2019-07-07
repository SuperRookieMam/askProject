package com.ask.simple.service;

import com.ask.base.service.BaseService;
import com.ask.simple.entity.OAthUserDetailes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OAthUserDetailesService extends BaseService<OAthUserDetailes, Long> ,UserDetailsService {

    OAthUserDetailes  findByUsername(String username);

    OAthUserDetailes findByCredentials(String credentials);
}
