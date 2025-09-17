package com.wanmi.sbc.elastic.api.request.ledger;

import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoPageRequest
 * @description
 * @date 2022/7/13 3:39 PM
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsLedgerBindInfoPageRequest extends EsBaseQueryRequest {
    private static final long serialVersionUID = 3905305110299494086L;

    /**
     * 批量查询-idList
     */
    @Schema(description = "批量查询-idList")
    private List<String> idList;

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 商户分账数据id
     */
    @Schema(description = "商户分账数据id")
    private String ledgerSupplierId;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long supplierId;

    /**
     * 接收方id
     */
    @Schema(description = "接收方id")
    private String receiverId;

    /**
     * 接收方名称
     */
    @Schema(description = "接收方名称")
    private String receiverName;

    /**
     * 接收方编码
     */
    @Schema(description = "接收方编码")
    private String receiverCode;

    /**
     * 接收方类型 0、平台 1、供应商 2、分销员
     */
    @Schema(description = "接收方类型 0、平台 1、供应商 2、分销员")
    private Integer receiverType;

    /**
     * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
     */
    @Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败")
    private Integer accountState;

    /**
     * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    @Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
    private Integer bindState;

    /**
     * 排除指定的绑定状态
     */
    @Schema(description = "排除指定的绑定状态")
    private List<Integer> filterBindStates;

    /**
     * 接收方账户
     */
    @Schema(description = "接收方账户")
    private String receiverAccount;

    /**
     * 审核状态 0、待审核 1、已审核 2、已驳回
     */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、已驳回")
    private Integer checkState;

}
