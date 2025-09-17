package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）电子卡券表信息response</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 电子卡券表信息
     */
    @Schema(description = "电子卡券表信息")
    private ElectronicCouponVO electronicCouponVO;
}
