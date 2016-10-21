/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.api;

import com.jdkcc.ts.service.dto.response.UserInfoResDto;
import com.jdkcc.ts.service.dto.request.UserCreateReqDto;

/**
 * @author Jiangjiaze
 * @version Id: UserFacade.java, v 0.1 2016/9/28 0028 上午 8:59 FancyKong Exp $$
 */
public interface UserFacade {
    boolean register(UserCreateReqDto reqDto);
    UserInfoResDto getUserInfo(long id);
}
