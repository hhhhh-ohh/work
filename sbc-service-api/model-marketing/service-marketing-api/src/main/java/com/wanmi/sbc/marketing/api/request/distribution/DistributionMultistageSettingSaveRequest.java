package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributorLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.CommissionUnhookType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:41 2019/6/13
 * @Description:
 */
@Schema
@Data
public class DistributionMultistageSettingSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -1345542882010090718L;

    /**
     * 佣金提成脱钩
     */
    @Schema(description = "佣金提成脱钩")
    @NotNull
    private CommissionUnhookType commissionUnhookType;

    /**
     * 分销员等级规则
     */
    @Schema(description = "分销员等级规则")
    @NotNull
    private String distributorLevelDesc;

    /**
     * 分销员等级列表
     */
    @Schema(description = "分销员等级列表")
    @NotEmpty
    private List<DistributorLevelDTO> distributorLevels;


    @Override
    public void checkParam() { }
}