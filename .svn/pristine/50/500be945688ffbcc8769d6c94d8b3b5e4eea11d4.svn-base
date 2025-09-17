package com.wanmi.sbc.customer.api.request.points;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.api.request.OperationLogAddRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerPointsAdjustDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.util.List;

/**
 * <p>会员积分明细新增参数</p>
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class CustomerPointsBatchAdjustRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;


    private List<CustomerPointsAdjustDTO> pointsAdjustDTOList;

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    private OperationLogAddRequest operationLogAddRequest;

}