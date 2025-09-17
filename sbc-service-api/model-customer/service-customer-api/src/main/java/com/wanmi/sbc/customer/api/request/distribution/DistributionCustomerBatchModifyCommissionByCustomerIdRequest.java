package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributorDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>批量更新分销员佣金提成</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerBatchModifyCommissionByCustomerIdRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员标识UUID
     */
    @NotNull
    private List<DistributorDTO> list;
}