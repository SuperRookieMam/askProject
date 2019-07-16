package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.Food;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜品接口
 * @author  yinjie
 * @date 2019年7月16日09:23:04
 */
@RestController
@RequestMapping("food")
public class FoodController extends BaseController<Food,Long> {

}
