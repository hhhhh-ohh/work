package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className LedgerAccountModifyRequest
 * @description
 * @date 2022/7/7 4:55 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountModifyRequest extends BaseRequest {
    private static final long serialVersionUID = -8199158811883823764L;

    /**
     * 业务id 商户id或customerId
     */
    @Schema(description = "业务id")
    @NotBlank
    private String businessId;

    /**
     * 开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败
     *
     * @see com.wanmi.sbc.customer.bean.enums.LedgerAccountState
     */
    @Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败")
    private Integer accountState;

    @Schema(description = "审核通过时间")
    private LocalDateTime passDateTime;

    /**
     * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
     *
     * @see com.wanmi.sbc.customer.bean.enums.LedgerState
     */
    @Schema(description = "分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败")
    private Integer ledgerState;

    /**
     * 开户驳回原因
     */
    @Schema(description = "开户驳回原因")
    private String accountRejectReason;

    /**
     * 分账驳回原因
     */
    @Schema(description = "分账驳回原因")
    private String ledgerRejectReason;

    /**
     * 电子合同文件id
     */
    @Schema(description = "电子合同文件id")
    private String ecContent;


    /**
     * 进件id
     */
    @Schema(description = "进件id")
    private String contractId;

    /**
     * 外部商户标识id
     */
    @Schema(description = "外部商户标识id")
    private String thirdMemNo;

    /**
     * 电子合同受理号
     */
    @Schema(description = "外部商户标识id")
    private String ecApplyId;


    /**
     * 终端号
     */
    @Schema(description = "终端号")
    private String termNo;

    /**
     * 银联商户号
     */
    @Schema(description = "银联商户号")
    private String merCupNo;

    /**
     * 商户分账申请编号
     */
    @Schema(description = "商户分账申请编号")
    private String ledgerApplyId;


    /**
     * 电子合同号
     */
    @Schema(description = "电子合同号")
    private String ecNo;

    /**
     * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
     */
    @Schema(description = "B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败")
    private Integer b2bAddState;

    @Schema(description = "B2b网银新增id")
    private String b2bAddApplyId;

    /**
     * 银行卡终端号
     */
    @Schema(description = "银行卡终端号")
    private String bankTermNo;

    /**
     * 网银终端号
     */
    @Schema(description = "网银终端号")
    private String unionTermNo;

    /**
     * 快捷终端号
     */
    @Schema(description = "快捷终端号")
    private String quickTermNo;

}
