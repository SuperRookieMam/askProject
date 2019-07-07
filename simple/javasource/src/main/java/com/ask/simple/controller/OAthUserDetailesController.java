package com.ask.simple.controller;

import com.ask.base.controller.BaseController;
import com.ask.simple.entity.OAthUserDetailes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oAthUserDetailes")
public class OAthUserDetailesController extends BaseController<OAthUserDetailes,Long> {

}
