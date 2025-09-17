package com.wanmi.sbc.empower.api.provider.channel.linkedmall.signature;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallSignatureVerifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * linkedmall验签
 */
@FeignClient(value = "${application.empower.name}" ,contextId = "LinkedMallSignatureProvider")
public interface LinkedMallSignatureProvider {
    /**
     * linkedmall验签
     * @param request
     * @return
     */
    @PostMapping("empower/${application.empower.version}/linkedmall/signature/verify")
    BaseResponse<Boolean> verifySignature(@RequestBody @Valid LinkedMallSignatureVerifyRequest request);
}
