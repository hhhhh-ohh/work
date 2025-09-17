package com.wanmi.sbc.marketing.api.response.pointscoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.PointsCouponVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>积分兑换券表新增结果</p>
 * @author yang
 * @date 2019-06-11 10:07:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的积分兑换券表信息
     */
    @Schema(description = "已新增的积分兑换券表信息")
    private PointsCouponVO pointsCouponVO;
}
