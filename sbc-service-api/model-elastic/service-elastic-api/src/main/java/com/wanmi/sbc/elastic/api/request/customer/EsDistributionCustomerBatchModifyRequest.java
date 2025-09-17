package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/9 17:52
 * @description <p> 根据id查询分销员信息 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsDistributionCustomerBatchModifyRequest extends BaseRequest {

    /**
     * 分销员ID
     */
    @Schema(description = "分销员ID列表")
    private List<String> distributionIds;
}
