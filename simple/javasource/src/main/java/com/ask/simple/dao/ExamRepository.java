package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends BaseRepository<Exam,Long> {
}
