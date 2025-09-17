package com.wanmi.sbc.account.api.response.funds;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerFundsForEsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员资金列表
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFundsListResponse extends BasicResponse {

    private static final long serialVersionUID = 6749202970111334943L;
    /**
     * 会员资金列表
     */
    @Schema(description = "会员资金列表")
    private List<CustomerFundsForEsVO> lists;
}
