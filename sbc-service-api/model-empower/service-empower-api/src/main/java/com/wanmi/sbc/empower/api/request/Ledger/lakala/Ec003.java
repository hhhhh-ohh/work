package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author edz
 * @className Ec003
 * @description TODO
 * @date 2022/9/2 16:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ec003 {
    /**
     * 授权公司名称
     */
    @JSONField(name = "A1")
    private String A1;

    /**
     * 与合作方签署协议名称
     */
    @JSONField(name = "A2")
    private String A2;

    /**
     * 清分结算方式选择
     */
    @JSONField(name = "A3")
    private String A3;

    /**
     * 支付服务费承担方名称
     */
    @JSONField(name = "A4")
    private String A4;

    /**
     * 商户分得最低分账比例
     */
    @JSONField(name = "A5")
    private String A5;

    /**
     * 商户固定分账比例
     */
    @JSONField(name = "A6")
    private String A6;

    /**
     * 合作方1名称
     */
    @JSONField(name = "A7")
    private String A7;

    /**
     * 合作方1固定分账比例
     */
    @JSONField(name = "A8")
    private String A8;

    /**
     * 合作方2名称
     */
    @JSONField(name = "A9")
    private String A9;

    /**
     * 合作方2固定分账比例
     */
    @JSONField(name = "A10")
    private String A10;

    /**
     * 合作方3名称
     */
    @JSONField(name = "A11")
    private String A11;

    /**
     * 合作方3固定分账比例
     */
    @JSONField(name = "A12")
    private String A12;

    /**
     * 合作公司名称
     */
    @JSONField(name = "A13")
    private String A13;

    /**
     * 商户分得最低分账比例
     */
    @JSONField(name = "A14")
    private String A14;

    /**
     * 结算周期方式选择
     */
    @JSONField(name = "A15")
    private String A15;

    /**
     * 结算周期方式选择
     */
    @JSONField(name = "A16")
    private String A16;

    /**
     * 合作公司名称
     */
    @JSONField(name = "A17")
    private String A17;

    /**
     * 结算周期
     */
    @JSONField(name = "A18")
    private String A18;

    /**
     * 签章日期
     */
    @JSONField(name = "A19")
    private String A19;

    /**
     * 签章日期
     */
    @JSONField(name = "B1")
    private String B1;
}
