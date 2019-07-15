package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends BaseRepository<Result,Long> {
}
