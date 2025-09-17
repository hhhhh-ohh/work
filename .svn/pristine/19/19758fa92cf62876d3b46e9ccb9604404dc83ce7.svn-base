package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * 电子卡券表批量新增结果
 * @className ElectronicCouponAddBatchResponse
 * @author zhengyang
 * @date 2022/4/28 10:59 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponAddBatchResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的电子卡券表信息
     */
    @Schema(description = "已新增的电子卡券表信息")
    private List<ElectronicCouponVO> electronicCouponVO;
}
