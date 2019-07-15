package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.Record;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends BaseRepository<Record,Long> {
}
