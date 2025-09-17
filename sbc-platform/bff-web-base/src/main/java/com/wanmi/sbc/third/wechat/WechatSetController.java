package com.wanmi.sbc.third.wechat;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.third.wechat.WechatSetService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Tag(name = "WechatSetController", description = "第三方登录-微信 API")
@RestController
@Validated
@RequestMapping("/third/wechat")
public class WechatSetController {

    @Autowired
    private WechatSetService wechatSetService;

    /**
     * 根据类型获取
     *
     * @return
     */
    @Operation(summary = "获取微信设置开关")
    @Parameter(name = "terminalType", description = "类型终端", required = true)
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'thirdWeChatStatusTerminalType:'+#terminalType")
    @RequestMapping(value = "/status/{terminalType}", method = RequestMethod.GET)
    public BaseResponse<DefaultFlag> status(@PathVariable String terminalType) {
        return BaseResponse.success(wechatSetService.getStatus(TerminalType.valueOf(terminalType)));
    }
}
