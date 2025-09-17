package com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo;

import com.wanmi.sbc.order.bean.vo.WxPayUploadShippingInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>微信小程序支付发货信息列表结果</p>
 * @author 吕振伟
 * @date 2023-07-24 13:53:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayUploadShippingInfoListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信小程序支付发货信息列表结果
     */
    @Schema(description = "微信小程序支付发货信息列表结果")
    private List<WxPayUploadShippingInfoVO> wxPayUploadShippingInfoVOList;
}
