package com.wanmi.sbc.crm.api.response.customerplansendcount;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerPlanSendCountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>权益礼包优惠券发放统计表修改结果</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的权益礼包优惠券发放统计表信息
     */
    @Schema(description = "已修改的权益礼包优惠券发放统计表信息")
    private CustomerPlanSendCountVO customerPlanSendCountVO;
}
