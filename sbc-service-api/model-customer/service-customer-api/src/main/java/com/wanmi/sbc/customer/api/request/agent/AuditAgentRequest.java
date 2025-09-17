package com.wanmi.sbc.customer.api.request.agent;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @program: sbc-micro-service
 * @description: 审核代理商信息请求参数
 * @create: 2020-04-01 15:01
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AuditAgentRequest extends BaseRequest {

    @Schema(description = "代理商ID（agent.agent_id）")
    private String agentId;

    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    private Integer auditStatus;

    @Schema(description = "审核意见")
    private String auditOpinion;

    @Schema(description = "驳回原因（audit_status=3时填写）")
    private String rejectReason;

    @Schema(description = "代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商")
    private Integer type;

    @Schema(description = "营业执照URL")
//    @NotBlank(message = "营业执照URL不能为空")
    private String businessLicense;

    @Schema(description = "店铺负责人")
//    @NotBlank(message = "店铺负责人不能为空")
    private String contactPerson;

    @Schema(description = "学校名称")
//    @NotBlank(message = "学校名称不能为空")
    private String schoolName;

    @Schema(description = "店铺名称")
//    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

}
