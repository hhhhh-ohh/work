package com.wanmi.sbc.empower.api.request.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 * @author
 * @date
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WxPayUploadShippingInfoRequest implements Serializable {

    private static final long serialVersionUID = -7235036131607693930L;
    /**
     * 订单，需要上传物流信息的订单
     * 必填
     */
    private UploadShippingInfoOrderKeyRequest order_key;

    /**
     * 物流模式，发货方式枚举值：1、实体物流配送采用快递公司进行实体物流配送形式 2、同城配送 3、虚拟商品，虚拟商品，例如话费充值，点卡等，无实体配送形式 4、用户自提
     */
    private Integer logistics_type;

    /**
     * 发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货） 示例值: UNIFIED_DELIVERY
     */
    private Integer delivery_mode;

    /**
     * 分拆发货模式时必填，用于标识分拆发货模式下是否已全部发货完成，只有全部发货完成的情况下才会向用户推送发货完成通知。示例值: true/false
     */
    private boolean is_all_delivered;

    /**
     * 物流信息列表，发货物流单列表，支持统一发货（单个物流单）和分拆发货（多个物流单）两种模式，多重性: [1, 10]
     */
    private List<ShippingListRequest> shipping_list;

    /**
     * 上传时间，用于标识请求的先后顺序 示例值: `2022-12-15T13:29:35.120+08:00`
     */
    private String upload_time;

    /**
     * 支付者，支付者信息
     */
    private UploadShippingInfoPayerRequest payer;

}
