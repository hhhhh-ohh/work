package com.wanmi.sbc.empower.bean.vo.sellplatform.apply;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCheckAccessInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 上传商品并审核成功，0:未成功，1:成功
     */
    @Schema(description = "上传商品并审核成功，0:未成功，1:成功")
    private Integer spu_audit_success;
    /**
     * 发起一笔订单并支付成功，0:未成功，1:成功
     */
    @Schema(description = "发起一笔订单并支付成功，0:未成功，1:成功")
    private Integer send_delivery_success;
    /**
     * 物流接口调用成功，0:未成功，1:成功
     */
    @Schema(description = "物流接口调用成功，0:未成功，1:成功")
    private Integer ec_order_success;
    /**
     * 售后接口调用成功，0:未成功，1:成功
     */
    @Schema(description = "售后接口调用成功，0:未成功，1:成功")
    private Integer ec_after_sale_success;
    /**
     * 商品接口调试完成，0:未完成，1:已完成
     */
    @Schema(description = "商品接口调试完成，0:未完成，1:已完成")
    private Integer spu_audit_finished;
    /**
     * 订单接口调试完成，0:未完成，1:已完成
     */
    @Schema(description = "订单接口调试完成，0:未完成，1:已完成")
    private Integer send_delivery_finished;
    /**
     * 物流接口调试完成，0:未完成，1:已完成
     */
    @Schema(description = "物流接口调试完成，0:未完成，1:已完成")
    private Integer ec_order_finished;
    /**
     * 售后接口调试完成，0:未完成，1:已完成
     */
    @Schema(description = "售后接口调试完成，0:未完成，1:已完成")
    private Integer ec_after_sale_finished;
    /**
     * 测试完成，0:未完成，1:已完成
     */
    @Schema(description = "测试完成，0:未完成，1:已完成")
    private Integer test_api_finished;
    /**
     * 发版完成，0:未完成，1:已完成
     */
    @Schema(description = "发版完成，0:未完成，1:已完成")
    private Integer deploy_wxa_finished;
    /**
     * 完成自定义组件全部任务 0:未完成 1:已完成
     */
    @Schema(description = "完成自定义组件全部任务 0:未完成 1:已完成")
    private Integer open_product_task_finished;
}