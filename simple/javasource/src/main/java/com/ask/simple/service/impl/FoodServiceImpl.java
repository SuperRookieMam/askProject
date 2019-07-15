package com.ask.simple.service.impl;

import com.ask.base.service.impl.BaseServiceImpl;
import com.ask.simple.entity.Food;
import com.ask.simple.service.FoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl extends BaseServiceImpl<Food, Long> implements FoodService {
}
