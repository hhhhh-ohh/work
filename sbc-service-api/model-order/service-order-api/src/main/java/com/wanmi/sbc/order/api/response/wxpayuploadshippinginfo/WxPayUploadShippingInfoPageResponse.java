package com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.WxPayUploadShippingInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>微信小程序支付发货信息分页结果</p>
 * @author 吕振伟
 * @date 2023-07-24 13:53:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayUploadShippingInfoPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信小程序支付发货信息分页结果
     */
    @Schema(description = "微信小程序支付发货信息分页结果")
    private MicroServicePage<WxPayUploadShippingInfoVO> wxPayUploadShippingInfoVOPage;
}
