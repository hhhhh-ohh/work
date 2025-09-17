package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 根据客户和券码id查询不可用的平台券以及优惠券实际优惠总额的请求结构
 * @Author: gaomuwei
 * @Date: Created In 上午9:27 2018/9/29
 */
@Schema
@Data
public class CouponCheckoutRequest extends BaseRequest {

    private static final long serialVersionUID = -3231863703528051505L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * 用户终端token
     */
    @NotBlank
    private String terminalToken;

    /**
     * 已勾选的优惠券码id
     */
    @Schema(description = "已勾选的优惠券码id列表")
    @NotNull
    private List<String> couponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    @Schema(description = "店铺Id")
    private Long storeId;

    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 限定为达到使用门槛的券类型列表
     */
    private List<QueryCouponType> unreachedTypes;
}
