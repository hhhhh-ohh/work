package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>达达配送记录新增参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识", hidden = true)
    private Long storeId;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "商户号")
    private String sourceId;

    /**
     * 第三方店铺编码
     */
    @Schema(description = "第三方店铺编码")
    private String shopNo;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private Operator operator;


}