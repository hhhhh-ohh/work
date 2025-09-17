package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>分销员分页结果</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员分页结果
     */
    @Schema(description = "分销员分页结果")
    private MicroServicePage<DistributionCustomerVO> distributionCustomerVOPage;
}
