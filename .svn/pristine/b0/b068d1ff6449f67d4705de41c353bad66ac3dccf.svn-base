package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by of2975 on 2019/4/30.
 */

/**
 * 分销员佣金导出查询参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class DistributionCommissionExportRequest extends BaseQueryRequest {

    /**
     * jwt token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 分销员IDList
     */
    @Schema(description = "批量查询-分销员List")
    private List<String> distributionIdList;

    /**
     *分销员ID
     */
    @Schema(description = "分销员ID")
    private String  distributionId;

    /**
     * 加入起始日期
     */
    @Schema(description = "加入起始日期")
    private String createStartTime;

    /**
     * 加入结束日期
     */
    @Schema(description = "加入结束日期")
    private String createEndTime;

    /**
     * 加入日期
     */
    @Schema(description = "加入日期")
    private String createTime;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

    @Schema(description = "分销员ID")
    private String distributorLevelId;
}
