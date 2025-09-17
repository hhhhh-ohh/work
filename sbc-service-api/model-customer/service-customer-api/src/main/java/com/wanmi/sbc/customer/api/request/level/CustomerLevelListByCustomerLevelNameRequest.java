package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;

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
public class CustomerLevelListByCustomerLevelNameRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 营销会员等级对象
     */
    @Schema(description = "营销会员等级对象")
    @NotNull
    private List<MarketingCustomerLevelDTO> customerLevelDTOList;

}
