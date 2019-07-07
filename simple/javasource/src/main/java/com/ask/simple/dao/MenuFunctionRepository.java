package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.MenuFunction;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuFunctionRepository extends BaseRepository<MenuFunction,Long> {
}
