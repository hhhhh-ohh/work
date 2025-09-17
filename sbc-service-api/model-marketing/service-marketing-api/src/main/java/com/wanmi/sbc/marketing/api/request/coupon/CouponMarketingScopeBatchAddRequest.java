package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponMarketingScopeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 批量新增优惠券商品作用范围请求结构
 * @Author: daiyitian
 * @Date: Created In 下午7:47 2018/11/24
 * @Description:
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponMarketingScopeBatchAddRequest extends BaseRequest {

    private static final long serialVersionUID = 355423344910423227L;

    /**
     * 优惠券商品作用范围内容 {@link CouponMarketingScopeDTO}
     */
    @Schema(description = "优惠券商品作用范围列表")
    @NotEmpty
    private List<CouponMarketingScopeDTO> scopeDTOList;

}
