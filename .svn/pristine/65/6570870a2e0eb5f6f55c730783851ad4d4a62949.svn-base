package com.wanmi.sbc.crm.api.provider.lifecyclegroup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.crm.api.request.customgroup.CustomGroupRequest;
import com.wanmi.sbc.crm.api.request.lifecyclegroup.LifeCycleGroupStatisticsPageRequest;
import com.wanmi.sbc.crm.api.request.lifecyclegroup.LifeCycleGroupStatisticsRequest;
import com.wanmi.sbc.crm.api.response.lifecyclegroup.LifeCycleGroupStatisticsHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @ClassName LifeCycleGroupProvider
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/25 19:13
 * @Version 1.0
 **/
@FeignClient(value = "${application.crm.name}",contextId = "LifeCycleGroupProvider")
public interface LifeCycleGroupProvider {

    @PostMapping("/crm/${application.crm.version}/lifecyclegroup/statistics/overview")
    BaseResponse overview();

    @PostMapping("/crm/${application.crm.version}/lifecyclegroup/statistics/get-history-list")
    BaseResponse<LifeCycleGroupStatisticsHistoryResponse> getHistoryList(@RequestBody @Valid LifeCycleGroupStatisticsRequest request);

    @PostMapping("/crm/${application.crm.version}/lifecyclegroup/statistics/page")
    BaseResponse page(@RequestBody @Valid LifeCycleGroupStatisticsPageRequest request);
}
