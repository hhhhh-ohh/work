package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:05 2019/3/12
 * @Description:
 */
@Schema
@Data
public class DistributionCustomerUpgradeRequest extends BaseRequest {

    /**
     * 待升级的用户id
     */
    @Schema(description = "待升级的用户id")
    @NotNull
    private String customerId;

    /**
     * 分销员等级设置信息
     */
    @Schema(description = "分销员等级设置信息")
    private List<DistributorLevelVO> distributorLevelVOList;

    /**
     * 基础邀新奖励限制
     */
    @Schema(description = "基础邀新奖励限制")
    private DefaultFlag baseLimitType;

    /**
     * 邀新人数
     */
    @Schema(description = "邀新人数")
    private Integer inviteNum = NumberUtils.INTEGER_ZERO;

    /**
     * 销售额(元)
     */
    @Schema(description = "销售额(元) ")
    private BigDecimal sales = BigDecimal.ZERO;

    /**
     * 分销佣金(元)
     */
    @Schema(description = "分销佣金(元) ")
    private BigDecimal commission = BigDecimal.ZERO;

}
