package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 客户等级查询请求参数
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLevelByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    @NotNull
    private List<Long> customerLevelIds;

}
