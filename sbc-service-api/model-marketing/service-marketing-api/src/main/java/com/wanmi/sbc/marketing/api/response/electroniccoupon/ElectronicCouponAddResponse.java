package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>电子卡券表新增结果</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的电子卡券表信息
     */
    @Schema(description = "已新增的电子卡券表信息")
    private ElectronicCouponVO electronicCouponVO;
}
