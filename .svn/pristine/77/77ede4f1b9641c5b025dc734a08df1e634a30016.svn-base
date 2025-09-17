package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponMarketingScopeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据优惠券范围id查询优惠券商品作用范列表响应结构
 * @Author: daiyitian
 * @Date: Created In 下午5:58 2018/11/23
 * @Description: 使用优惠券列表请求对象
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponMarketingScopeByScopeIdResponse extends BasicResponse {

    private static final long serialVersionUID = 6423440972731726905L;

    /**
     * 优惠券商品作用范围列表
     */
    @Schema(description = "优惠券商品作用范围列表")
    private List<CouponMarketingScopeVO> scopeVOList;
}
