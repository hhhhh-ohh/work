package com.wanmi.sbc.crm.api.response.customerplanconversion;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.CustomerPlanConversionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划转化效果分页结果</p>
 * @author zhangwenchang
 * @date 2020-02-12 00:16:50
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanConversionPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 运营计划转化效果分页结果
     */
    @Schema(description = "运营计划转化效果分页结果")
    private MicroServicePage<CustomerPlanConversionVO> customerPlanConversionVOPage;
}
