package com.wanmi.sbc.elastic.communityleader.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wc
 * 社区团长实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = EsConstants.COMMUNITY_LEADER)
public class EsCommunityLeader implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 团长id
     */
    @Schema(description = "团长id")
    @Id
    private String leaderId;

    /**
     * 团长账号
     */
    @Schema(description = "团长账号")
    private String leaderAccount;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 团长名称
     */
    @Schema(description = "团长名称")
    private String leaderName;

    /**
     * 团长简介
     */
    @Schema(description = "团长简介")
    private String leaderDescription;

    /**
     * 审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中
     */
    @Schema(description = "审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中")
    private LeaderCheckStatus checkStatus;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String checkReason;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String disableReason;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime checkTime;

    /**
     * 禁用时间
     */
    @Schema(description = "禁用时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime disableTime;

    /**
     * 是否帮卖 0:否 1:是
     */
    @Schema(description = "是否帮卖 0:否 1:是")
    private Integer assistFlag;

    /**
     * 会员头像
     */
    @Schema(description = "会员头像")
    private String headImg;


}
