package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerSimVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:09 2019/7/5
 * @Description:
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerListForOrderCommitResponse extends BasicResponse {

    /**
     * 分销员列表
     */
    @Schema(description = "分销员列表")
    private List<DistributionCustomerSimVO> distributorList;

}
