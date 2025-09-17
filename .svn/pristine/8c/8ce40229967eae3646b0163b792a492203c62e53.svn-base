package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class CustomerLevelListByCustomerLevelNameResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级信息集合
     */
    @Schema(description = "客户等级信息集合")
    private List<MarketingCustomerLevelVO> customerLevelVOList;

}
