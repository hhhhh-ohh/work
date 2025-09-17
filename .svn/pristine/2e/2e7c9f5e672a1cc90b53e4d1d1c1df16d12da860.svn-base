package com.wanmi.sbc.customer.bean.vo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentAuditLogBaseVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 审核记录主键ID（UUID）
     */
    @Schema(description = "审核记录主键ID（UUID）")
    private String logId;

    /**
     * 代理商ID（agent.agent_id）
     */
    @Schema(description = "代理商ID（agent.agent_id）")
    private String agentId;

    /**
     * 审核状态 0已创建 1待审核 2通过 3驳回
     */
    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    private Integer auditStatus;

    /**
     * 审核意见
     */
    @Schema(description = "审核意见")
    private String auditOpinion;

    /**
     * 驳回原因（audit_status=3时填写）
     */
    @Schema(description = "驳回原因（audit_status=3时填写）")
    private String rejectReason;

    /**
     * 审核人用户ID
     */
    @Schema(description = "审核人用户ID")
    private String auditorId;

    /**
     * 审核人姓名
     */
    @Schema(description = "审核人姓名")
    private String auditorName;

    /**
     * 审核时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    /**
     * 删除标志 0正常 1已删除
     */
    @Schema(description = "删除标志 0正常 1已删除")
    private DeleteFlag delFlag;


    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String contactPhone;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
