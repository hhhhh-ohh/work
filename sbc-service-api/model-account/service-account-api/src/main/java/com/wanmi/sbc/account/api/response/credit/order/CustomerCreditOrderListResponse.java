package com.wanmi.sbc.account.api.response.credit.order;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>授信订单信息列表结果</p>
 * @author chenli
 * @date 2021-03-15 17:23:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditOrderListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 授信订单信息列表结果
     */
    @Schema(description = "授信订单信息列表结果")
    private List<CustomerCreditOrderVO> customerCreditOrderVOList;
}
