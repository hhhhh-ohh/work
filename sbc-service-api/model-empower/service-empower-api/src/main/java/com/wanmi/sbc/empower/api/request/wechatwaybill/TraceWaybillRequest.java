package com.wanmi.sbc.empower.api.request.wechatwaybill;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 微信物流传运单接口请求实体request
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraceWaybillRequest implements Serializable {


    /**
     * 用户openid
     */
    @Schema(description = "用户openid")
    private String openid;
    /**
     * 运单号
     */
    @Schema(description = "运单号")
    private String waybill_id;
    /**
     * 寄件人手机号
     */
    @Schema(description = "寄件人手机号")
    private String sender_phone;
    /**
     * 收件人手机号
     */
    @Schema(description = "收件人手机号")
    private String receiver_phone;
    /**
     * 快递公司ID
     */
    @Schema(description = "快递公司ID")
    private String delivery_id;
    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private GoodsInfo goods_info;
    /**
     * 交易单号（微信支付生成的交易单号，一般以420开头
     */
    @Schema(description = "交易单号（微信支付生成的交易单号，一般以420开头")
    private String trans_id;

    // Getters and Setters
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWaybill_id() {
        return waybill_id;
    }

    public void setWaybill_id(String waybill_id) {
        this.waybill_id = waybill_id;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public GoodsInfo getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfo goods_info) {
        this.goods_info = goods_info;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    /**
     * 商品信息实体
     */
    public static class GoodsInfo {
        /**
         * 商品详情列表
         */
        @Schema(description = "商品详情列表")
        private List<GoodsDetail> detail_list;  // 商品详情列表

        // Getters and Setters
        public List<GoodsDetail> getDetail_list() {
            return detail_list;
        }

        public void setDetail_list(List<GoodsDetail> detail_list) {
            this.detail_list = detail_list;
        }
    }

    /**
     * 商品详情实体
     */
    public static class GoodsDetail {
        /**
         * 商品名称
         */
        @Schema(description = "商品名称")
        private String goods_name;
        /**
         * 商品图片URL
         */
        @Schema(description = "商品图片URL")
        private String goods_img_url;

        // Getters and Setters
        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img_url() {
            return goods_img_url;
        }

        public void setGoods_img_url(String goods_img_url) {
            this.goods_img_url = goods_img_url;
        }
    }
}
