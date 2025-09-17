package com.wanmi.sbc.customer.api.request.agent;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * @program: sbc-micro-service
 * @description: 添加代理商请求参数
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAgentRequest extends BaseRequest {

    @Schema(description = "代理商主键ID")
    private String agentId;

    @Schema(description = "系统唯一码")
    private String agentUniqueCode;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    private Integer auditStatus;

    @Schema(description = "区")
    private List<Long> areaIdList;

    @Schema(description = "区id")
    private Long areaId;

    @Schema(description = "oa账号")
    private String oaAccount;

}
