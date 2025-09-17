package com.wanmi.sbc.marketing.api.provider.fullreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnAddRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnModifyRequest;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: xufeng
 * @Description: 满返优惠服务修改操作接口
 * @Date: 2022-04-06 15:56
 */
@FeignClient(value = "${application.marketing.name}", contextId = "FullReturnProvider")
public interface FullReturnProvider {


    /**
     * 新增满返数据
     * @param addRequest 新增参数 {@link FullReturnAddRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/full-return/add")
    BaseResponse<FullReturnAddResponse> add(@RequestBody @Valid FullReturnAddRequest addRequest);

    /**
     * 修改满返数据
     * @param modifyRequest 修改参数 {@link FullReturnModifyRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/full-return/modify")
    BaseResponse modify(@RequestBody @Valid FullReturnModifyRequest modifyRequest);
}
