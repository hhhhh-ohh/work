package com.wanmi.sbc.empower.api.request.sellplatform.order;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformDeliveryDetailVO;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformOrderAddressVO;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformOrderDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  获取小程序AccessToken请求
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformAddOrderRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @NotEmpty
    @Schema(description = "创建时间，yyyy-MM-dd HH:mm:ss，与微信服务器不得超过5秒偏差")
    private String create_time;

    @NotEmpty
    @Schema(description = "商家自定义订单ID(字符集包括大小写字母数字，长度小于128个字符）")
    private String out_order_id;

    @NotEmpty
    @Schema(description = "用户的openid")
    private String openid;

    @Schema(description = "订单详细数据")
    private PlatformOrderDetailVO order_detail;

    @NotNull
    @Schema(description = "配送信息")
    private PlatformDeliveryDetailVO delivery_detail;

    @Schema(description = "订单详情路径")
    private String path;

    @Schema(description = "收货地址信息")
    private PlatformOrderAddressVO address_info;

    @Schema(description = "订单类型：0，普通单，1，二级商户单")
    @NotNull
    private Integer fund_type;

    @Schema(description = "秒级时间戳，订单超时时间，获取支付参数将使用此时间作为prepay_id 过期时间;时间到期之后，微信会流转订单超时取消（status = 181）")
    @NotNull
    private Long expire_time;

    @Schema(description = "确认收货之后多久禁止发起售后，单位：天，需>=5天，default=5天")
    private String aftersale_duration;

}
