package com.wanmi.sbc.empower.api.provider.sellplatform.apply;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformApplySceneRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformFinishAccessRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.apply.PlatformCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className PlatformApplyProvider
 * @description 自定义交易组件申请
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformApplyProvider")
public interface PlatformApplyProvider {

    /**
     * 开通自定义交易组件
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/register_apply/")
    BaseResponse registerApply(@RequestBody @Valid ThirdBaseRequest request);

    /**
     * 获取接入状态接口
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/register_check/")
    BaseResponse<PlatformCheckResponse> registerCheck(@RequestBody @Valid ThirdBaseRequest request);

    /**
     * 完成接入任务
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/register_finish_access/")
    BaseResponse registerFinishAccess(@RequestBody @Valid PlatformFinishAccessRequest request);

    /**
     * 场景接入申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/register_apply_scene/")
    BaseResponse registerApplyScene(@RequestBody @Valid PlatformApplySceneRequest request);

}
