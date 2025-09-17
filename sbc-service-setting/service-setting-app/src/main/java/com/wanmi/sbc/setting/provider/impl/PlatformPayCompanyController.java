package com.wanmi.sbc.setting.provider.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.PayCompanyIncision;
import com.wanmi.sbc.setting.api.provider.PlatformPayCompanyProvider;
import com.wanmi.sbc.setting.api.response.PlatformPayCompanyListResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformPayCompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 插件可用支付方式服务
 * @author malianfeng
 * @date 2021/8/3 19:42
 */
@Slf4j
@RestController
@Validated
public class PlatformPayCompanyController implements PlatformPayCompanyProvider {

    @Autowired private PayCompanyIncision payChannelIncisionService;

    @Override
    public BaseResponse<PlatformPayCompanyListResponse> payCompanyList() {
        List<PlatformPayCompanyVO> platformPayCompanyList =
                payChannelIncisionService.getPayCompanyList();
        return BaseResponse.success(new PlatformPayCompanyListResponse(platformPayCompanyList));
    }
}
