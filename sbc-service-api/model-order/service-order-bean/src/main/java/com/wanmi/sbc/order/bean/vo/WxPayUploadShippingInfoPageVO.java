package com.wanmi.sbc.order.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>微信小程序支付发货信息VO</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WxPayUploadShippingInfoPageVO extends WxPayUploadShippingInfoVO {

}
