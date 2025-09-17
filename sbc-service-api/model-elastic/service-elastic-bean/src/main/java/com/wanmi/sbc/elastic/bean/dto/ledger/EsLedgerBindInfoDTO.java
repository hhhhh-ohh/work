package com.wanmi.sbc.elastic.bean.dto.ledger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoDTO
 * @description
 * @date 2022/7/14 4:51 PM
 **/
@Data
public class EsLedgerBindInfoDTO {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 商户分账数据id
     */
    private String ledgerSupplierId;

    /**
     * 商户id
     */
    private Long supplierId;

    /**
     * 接收方id
     */
    private String receiverId;

    /**
     * 接收方名称
     */
    private String receiverName;

    /**
     * 接收方编码(供应商编码或分销员账号)
     */
    private String receiverCode;

    /**
     * 接收方类型 0、平台 1、供应商 2、分销员
     */
    private Integer receiverType;

    /**
     * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
     */
    private Integer accountState;

    /**
     * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bindTime;

    /**
     * 接收方账户
     */
    private String receiverAccount;

    /**
     * 审核状态 0、待审核 1、已审核 2、已驳回
     */
    private Integer checkState;

    /**
     * 驳回原因
     */
    private String rejectReason;
}
