package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Food;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("food")
public class FoodController extends BaseController<Food,Long> {

}
