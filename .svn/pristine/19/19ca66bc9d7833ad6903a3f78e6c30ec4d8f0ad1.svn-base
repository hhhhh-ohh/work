package com.wanmi.sbc.marketing.api.request.bargaingoods;

import com.wanmi.sbc.common.enums.AuditStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 砍价审核请求对象
 * @author  lipeixian
 * @date 2022/5/25 2:36 下午
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BargainCheckRequest implements Serializable {

    /**
     * 砍价活动ID
     */
    @Schema(description = "砍价活动ID")
    @NotEmpty
    private List<Long> bargainGoodsIds;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败")
    @NotNull
    private AuditStatus auditStatus;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String reasonForRejection;


}
