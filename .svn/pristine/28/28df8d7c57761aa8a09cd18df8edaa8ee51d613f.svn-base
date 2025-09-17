package com.wanmi.sbc.empower.api.provider.wechatwaybill;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetByIdRequest;
import com.wanmi.sbc.empower.api.request.wechatshareset.WechatShareSetInfoRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.WechatWaybillStatusRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.WechatWaybillTokenRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * <p>微信物流信息服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@FeignClient(value = "${application.empower.name}", contextId = "WechatWaybillProvider")
public interface WechatWaybillProvider {

    /**
     * 微信物流信息token同步API
     *
     * @author lq
     * @return 微信物流信息token同步API {@link }
     */
    @PostMapping("/empower/${application.empower.version}/wechatwaybill/test-get-waybill_token")
    BaseResponse testGetWaybillToken();

    /**
     * 微信物流信息状态同步API
     *
     * @author lq
     * @return 微信物流信息状态同步 {@link }
     */
    @PostMapping("/empower/${application.empower.version}/wechatwaybill/test-get-waybill_status")
    BaseResponse testGetWaybillStatus();

    /**
     * 获取物流信息token
     *
     * @author lq
     * @param wechatWaybillTokenRequest 查询微信物流token请求参数 {@link WechatWaybillTokenRequest}
     * @return 微信物流信息toekn同步 {@link }
     */
    @PostMapping("/empower/${application.empower.version}/wechatwaybill/get-waybill_token")
    BaseResponse getWaybillToken(@RequestBody @Valid WechatWaybillTokenRequest
                                         wechatWaybillTokenRequest);

    /**
     * 获取物流信息状态
     *
     * @author lq
     * @param wechatWaybillStatusRequest 查询微信物流状态请求参数 {@link WechatWaybillStatusRequest}
     * @return 获取物流信息状态 {@link }
     */
    @PostMapping("/empower/${application.empower.version}/wechatwaybill/get-waybill_status")
    BaseResponse getWaybillStatus(@RequestBody @Valid WechatWaybillStatusRequest
                                         wechatWaybillStatusRequest);

}

