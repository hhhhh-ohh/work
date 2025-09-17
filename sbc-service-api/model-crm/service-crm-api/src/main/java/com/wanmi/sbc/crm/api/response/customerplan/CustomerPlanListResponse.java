package com.wanmi.sbc.crm.api.response.customerplan;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.CustomerPlanVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 人群运营计划分页结果</p>
 * @author dyt
 * @date 2020-01-07 17:07:02
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     *  人群运营计划分页结果
     */
    @Schema(description = " 人群运营计划分页结果")
    private List<CustomerPlanVO> customerPlanList;
}
