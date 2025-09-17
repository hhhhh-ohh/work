package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description   订单详情
 * @author  wur
 * @date: 2022/4/13 11:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformOrderVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 创建时间，yyyy-MM-dd HH:mm:ss，与微信服务器不得超过5秒
     */
    private String create_time;
    /**
     * 微信侧订单号
     */
    private Long order_id;
    /**
     * 商家自定义订单ID(字符集包括大小写字幕数字，长度小于128个字符）
     */
    private String out_order_id;
    /**
     * 用户的openid
     */
    private String openid;
    /**
     * 下单小程序场景值
     */
    private Integer scene;
    /**
     * 订单详细数据
     */
    private SellPlatformOrderDetailVO order_detail;
    /**
     * 商品状态
     * 10	待付款
     * 11	收银台支付完成（自动流转，对商家来说和10同等对待即可）
     * 20	待发货(即支付完成)
     * 21	部分发货
     * 30	待收货
     * 100	完成
     * 181	超时未支付取消
     * 200	全部商品售后之后取消
     * 250	用户取消
     */
    private Integer status;
    /**
     * 售后单信息
     */
    private SellPlatformRelatedAftersaleInfoVO related_aftersale_info;
    /**
     * 订单类型：0，普通单，1，二级商户单
     */
    private Integer fund_type;
    /**
     * 推广员、分享员信息
     */
    private SellPlatformPromotionInfoVO promotion_info;
}
