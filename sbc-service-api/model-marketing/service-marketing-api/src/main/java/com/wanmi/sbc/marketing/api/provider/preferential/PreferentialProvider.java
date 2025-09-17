package com.wanmi.sbc.marketing.api.provider.preferential;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialAddRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description
 * @author edz
 * @date 2022/11/18 16:33
 */
@FeignClient(value = "${application.marketing.name}", contextId = "PreferentialProvider")
public interface PreferentialProvider {

    /**
     * @description 新增加价购活动
     * @author  edz
     * @date: 2022/11/18 17:15
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/add")
    BaseResponse add(@RequestBody @Valid PreferentialAddRequest preferentialRequest);

    /**
     * @description 更新加价购活动
     * @author  edz
     * @date: 2022/11/21 10:53
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/modify")
    BaseResponse modify(@RequestBody @Valid PreferentialAddRequest preferentialRequest);
}
