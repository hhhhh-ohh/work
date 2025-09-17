package com.wanmi.sbc.order.api.provider.thirdplatformreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderApplyRequest;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderSyncRequest;
import com.wanmi.sbc.order.api.request.thirdplatformreturn.ThirdPlatformReturnOrderAutoApplySubRequest;
import com.wanmi.sbc.order.api.response.returnorder.ReturnReasonListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>linkedMall退单服务接口</p>
 * @Author: daiyitian
 * @Description: 退单服务接口
 * @Date: 2018-12-03 15:40
 */
@FeignClient(value = "${application.order.name}", contextId = "ThirdPlatformReturnOrderProvider")
public interface ThirdPlatformReturnOrderProvider {

    /**
     * 查询所有退货原因
     *
     * @return 退货原因列表 {@link ReturnReasonListResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-return/apply")
    BaseResponse apply(@RequestBody @Valid ThirdPlatformReturnOrderApplyRequest request);

    /**
     * 同步退单状态
     *
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-return/sync-status")
    BaseResponse syncStatus(ThirdPlatformReturnOrderSyncRequest request);

    /**
     * 同步退单状态
     *
     * @param request 申请请求结构 {@link ThirdPlatformReturnOrderAutoApplySubRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-return/auto-apply")
    BaseResponse autoApplyBySub(@RequestBody @Valid ThirdPlatformReturnOrderAutoApplySubRequest request);
}
