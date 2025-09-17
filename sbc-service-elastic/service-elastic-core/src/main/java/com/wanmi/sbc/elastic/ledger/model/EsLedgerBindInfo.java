package com.wanmi.sbc.elastic.ledger.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfo
 * @description
 * @date 2022/7/13 2:35 PM
 **/
@Data
@Document(indexName = EsConstants.LEDGER_BIND_INFO)
public class EsLedgerBindInfo {

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
     * 接收方编码
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
     * 接收方账号
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
