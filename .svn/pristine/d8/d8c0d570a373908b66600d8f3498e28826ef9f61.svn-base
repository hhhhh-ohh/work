package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/9 21:09
 * @description <p> </p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerExportResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员导出结果
     */
    @Schema(description = "分销员导出结果")
    private List<DistributionCustomerVO> distributionCustomerVOList;
}
