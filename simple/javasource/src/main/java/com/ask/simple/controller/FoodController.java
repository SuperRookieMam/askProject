package com.ask.simple.controller;

import com.ask.base.componet.dto.ResultDto;
import com.ask.base.componet.util.ParamUtil;
import com.ask.base.controller.BaseController;
import com.ask.base.service.BaseService;
import com.ask.orm.componet.feature.PageInfo;
import com.ask.simple.entity.Food;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品接口
 * @author  yinjie
 * @date 2019年7月16日09:23:04
 */
@RestController
@RequestMapping("food")
public class FoodController extends BaseController<Food,Long> {
    @Autowired
    BaseService<Food,Long> baseService;
    @Value("${serverUrl}")
    private String serverUrl;

    @GetMapping("/url/{id}")
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="findById")
    public ResultDto findById1(@PathVariable("id") Long id){
        ResultDto resultDto=super.findById(id);
        Food  food=(Food)resultDto.getData();
        food.setImgUrl(serverUrl+food.getImgUrl());
        food.setProcessUrls(serverUrl+food.getProcessUrls());
        return ResultDto.success(food);
    }

    @GetMapping(value = "/url/list")
    @ResponseBody
    @ApiOperation(value="根据参数查询", notes="findByParams")
    public ResultDto findByParams1(@RequestParam("dynameicParams") String dynamicParams){
        ResultDto resultDto = super.findByParams(dynamicParams);
        List<Food> foods=(List<Food>) resultDto.getData();
        for(Food food : foods){
            food.setImgUrl(serverUrl+food.getImgUrl());
            food.setProcessUrls(serverUrl+food.getProcessUrls());
        }
        return ResultDto.success(foods);
    }
    @GetMapping(value = "/url/page")
    @ResponseBody
    @ApiOperation(value="根据参数查询", notes="findPageParams")
    public ResultDto findPageParams1(@RequestParam("dynameicParams") String dynamicParams){
        ResultDto resultDto = super.findPageParams(dynamicParams);
        PageInfo<Food> foodPageInfo=(PageInfo<Food>)resultDto.getData();
        List<Food> foods=foodPageInfo.getList();
        for(Food food : foods){
            food.setImgUrl(serverUrl+food.getImgUrl());
            food.setProcessUrls(serverUrl+food.getProcessUrls());
        }
        foodPageInfo.setList(foods);
        return ResultDto.success(foodPageInfo);
    }

}
