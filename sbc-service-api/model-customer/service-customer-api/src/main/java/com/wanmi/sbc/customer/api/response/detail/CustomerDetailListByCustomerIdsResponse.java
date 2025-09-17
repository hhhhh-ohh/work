package com.wanmi.sbc.customer.api.response.detail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailBaseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据会员id集合查询会员详情基础数据集合
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByCustomerIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 会员详情基础数据集合
     */
    @Schema(description = "会员详情基础数据集合")
    private List<CustomerDetailBaseVO> list;


}
