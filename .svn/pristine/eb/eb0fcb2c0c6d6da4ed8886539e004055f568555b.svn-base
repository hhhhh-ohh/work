package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformApplySceneRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformFinishAccessRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 代销申请处理
 * @author malianfeng
 * @date 2022/4/22 19:03
 */
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformApplyProvider")
public interface SellPlatformApplyProvider {

    /**
     * 开通自定义交易组件
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/register-apply/")
    BaseResponse registerApply(@RequestBody @Valid SellPlatformBaseRequest request);


    /**
     * 完成接入任务
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/register_finish_access/")
    BaseResponse registerFinishAccess(@RequestBody @Valid SellPlatformFinishAccessRequest request);

    /**
     * 场景接入申请
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/register_apply_scene/")
    BaseResponse registerApplyScene(@RequestBody @Valid SellPlatformApplySceneRequest request);

    /**
     * 场景接入申请
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/sell-platform/register_check/")
    BaseResponse registerCheck(@RequestBody @Valid SellPlatformBaseRequest request);
}
