package com.ask.simple.dao;

import com.ask.base.dao.BaseRepository;
import com.ask.simple.entity.Food;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends BaseRepository<Food,Long> {
}
