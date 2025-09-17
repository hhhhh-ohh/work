package com.wanmi.sbc.country;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.country.PlatformCountryProvider;
import com.wanmi.sbc.setting.api.response.country.PlatformCountryListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * @author houshuai
 * @date 2021/4/26 16:00
 * @description <p> 国家地区查询接口 </p>
 */
@RestController
@Validated
@RequestMapping(value = "/country")
@Tag(name = "PlatformCountryController", description = "国家地区查询API")
public class PlatformCountryController {

    @Autowired
    private PlatformCountryProvider platformCountryProvider;

    @GetMapping(value = "/list-all")
    @Operation(summary = "查询全部国家地区")
    public BaseResponse<PlatformCountryListResponse> findAll(){
        return platformCountryProvider.findAll();
    }
}
