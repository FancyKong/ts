/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.web.controller;

import com.jdkcc.ts.service.api.UserFacade;
import com.jdkcc.ts.service.dto.request.UserCreateReqDto;
import com.jdkcc.ts.service.dto.response.Response;
import com.jdkcc.ts.service.dto.response.UserInfoResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jiangjiaze
 * @version Id: UserController.java, v 0.1 2016/9/28 0028 上午 8:56 FancyKong Exp $$
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserFacade userService;

    @RequestMapping("{id}/info")
    @ResponseBody
    public Response<UserInfoResDto> getUserInfo(@PathVariable("id") long id) {
        Response<UserInfoResDto> response = new Response<>(true, userService.getUserInfo(id));
        return response;
    }

    @RequestMapping("register")
    @ResponseBody
    public Response<Boolean> register(UserCreateReqDto reqDto) {
        return new Response<>(true, userService.register(reqDto));
    }


}
