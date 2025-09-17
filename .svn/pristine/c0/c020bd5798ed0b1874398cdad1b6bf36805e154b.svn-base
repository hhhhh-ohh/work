package com.wanmi.sbc.marketing.provider.impl.preferential;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialProvider;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialAddRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialRequest;
import com.wanmi.sbc.marketing.preferential.service.MarketingPreferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author edz
 * @className PreferentialController
 * @description 加价购写操作
 * @date 2022/11/17 17:23
 **/
@Validated
@RestController
public class PreferentialController implements PreferentialProvider {

    @Autowired
    private MarketingPreferentialService marketingPreferentialService;

    /**
     * @description 新增加价购活动
     * @author  edz
     * @date: 2022/11/18 17:14
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Override
    public BaseResponse add(@RequestBody @Valid PreferentialAddRequest preferentialRequest){
        marketingPreferentialService.add(preferentialRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 加价购活动更新
     * @author  edz
     * @date: 2022/11/21 10:51
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Override
    public BaseResponse modify(PreferentialAddRequest preferentialRequest) {
        marketingPreferentialService.modify(preferentialRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
