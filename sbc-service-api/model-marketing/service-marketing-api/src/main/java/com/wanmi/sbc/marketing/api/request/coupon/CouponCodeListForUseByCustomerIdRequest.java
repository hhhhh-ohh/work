package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeItemInfoDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据客户id查询使用优惠券列表
 * @Author: daiyitian
 * @Date: Created In 下午5:58 2018/11/23
 * @Description: 使用优惠券列表请求对象
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCodeListForUseByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 4172643191705493478L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * 用户终端token
     */
    @Schema(description = "客户id")
    @NotBlank
    private String terminalToken;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private List<CouponMarketingType> couponMarketingTypes;

    /**
     * 确认订单中的商品列表
     */
    @Schema(description = "确认订单中的商品列表")
    @NotEmpty
    private List<TradeItemInfoDTO> tradeItems = new ArrayList<>();

    @Schema(description = "店铺Id")
    private Long storeId;

    @Schema(description = "订单金额/")
    private BigDecimal price;

    /**
     * 已选优惠券 codeIds 列表
     * 因为系统按照 店铺券 -> 平台券 -> 店铺券 的顺序判断使用门槛和算价，前者的时候可能会导致后者不满足门槛，
     * 这里通过传入已选券 codeIds，二次算价判断门槛，对不可用的后者，进行过滤
     */
    @Schema(description = "已选优惠券 codeIds 列表")
    private List<String> selectedCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    /**
     * 优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
     */
    @Schema(description = "优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券")
    private QueryCouponType queryCouponType;
}
