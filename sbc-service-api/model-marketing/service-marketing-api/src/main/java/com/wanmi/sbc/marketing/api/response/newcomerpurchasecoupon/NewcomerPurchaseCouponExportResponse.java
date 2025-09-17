package com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon;

import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseCouponPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>新人购优惠券列表结果</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 新人购优惠券列表结果
     */
    @Schema(description = "新人购优惠券列表结果")
    private List<NewcomerPurchaseCouponPageVO> newcomerPurchaseCouponList;
}
