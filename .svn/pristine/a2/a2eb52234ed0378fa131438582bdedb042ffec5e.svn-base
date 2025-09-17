package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.enums.CommissionPriorityType;
import com.wanmi.sbc.customer.bean.enums.CommissionUnhookType;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:49 2019/7/5
 * @Description:
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerListForOrderCommitRequest extends BaseRequest {

    /**
     * 店主id
     */
    @Schema(description = "店主id")
    private String InviteeId;

    /**
     * 下单用户id
     */
    @Schema(description = "下单用户id")
    private String buyerId;

    /**
     * 下单用户是否分销员
     */
    @Schema(description = "下单用户是否分销员")
    private DefaultFlag isDistributor = DefaultFlag.NO;

    /**
     * 佣金返利优先级
     */
    @Schema(description = "佣金返利优先级")
    private CommissionPriorityType commissionPriorityType;

    /**
     * 佣金提成脱钩
     */
    @Schema(description = "佣金提成脱钩")
    private CommissionUnhookType commissionUnhookType;

    /**
     * 分销员等级列表
     */
    @Schema(description = "分类员等级列表")
    private List<DistributorLevelVO> distributorLevels = new ArrayList<>();

}
