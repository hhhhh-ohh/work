package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGetAllCateRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelCateGetAllResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 类目查询接口
 * @author  hanwei
 * @date 2021/6/1
 **/
@FeignClient(value = "${application.empower.name}" ,contextId = "ChannelCateQueryProvider")
public interface ChannelCateQueryProvider {
    /**
     * 查询业务库全量类目
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/channel/cate/get-all-cate")
    BaseResponse<ChannelCateGetAllResponse> getAllCate(@RequestBody @Valid ChannelGetAllCateRequest request);

}
