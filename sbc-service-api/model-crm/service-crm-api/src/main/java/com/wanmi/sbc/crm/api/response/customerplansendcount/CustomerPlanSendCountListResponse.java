package com.wanmi.sbc.crm.api.response.customerplansendcount;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerPlanSendCountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>权益礼包优惠券发放统计表列表结果</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 权益礼包优惠券发放统计表列表结果
     */
    @Schema(description = "权益礼包优惠券发放统计表列表结果")
    private List<CustomerPlanSendCountVO> customerPlanSendCountVOList;
}
