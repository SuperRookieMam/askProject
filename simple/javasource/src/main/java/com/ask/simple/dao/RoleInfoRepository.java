package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.RoleInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleInfoRepository extends BaseRepository<RoleInfo,Long> {
}
