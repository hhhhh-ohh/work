package com.wanmi.sbc.marketing.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.marketing.bean.enums.CommissionPriorityType;
import com.wanmi.sbc.marketing.bean.enums.CommissionUnhookType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:16 2019/6/19
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultistageSettingGetResponse extends BasicResponse {


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
