package com.wanmi.sbc.order.bean.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.vo.OfflineAccountVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收款单
 * Created by zhangjin on 2017/3/20.
 */
@Data
@Schema
public class ReceivableVO extends BasicResponse {


    @Schema(description = "收款单id")
    private String receivableId;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String receivableNo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 线上账户
     */
    @Schema(description = "线上账户id")
    private Long onlineAccountId;

    /**
     * 线下账户
     */
    @Schema(description = "线下账户id")
    private Long offlineAccountId;

    /**
     * 收款账号
     */
    @Schema(description = "收款账号")
    private String receivableAccount;

    /**
     * 评论
     */
    @Schema(description = "评论")
    private String comment;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    @JsonBackReference
    @JSONField(serialize = false)
    @Schema(description = "付款单")
    private PayOrderVO payOrder;

    /**
     * 支付单外键
     */
    @Schema(description = "支付单外键")
    private String payOrderId;

    /**
     * 收款在线渠道
     */
    @Schema(description = "收款在线渠道")
    private String payChannel;

    @Schema(description = "收款在线渠道id")
    private Long payChannelId;

    /**
     * 附件
     */
    @Schema(description = "附件")
    private String encloses;

    /**
     * 离线账户
     */
    @Schema(description = "离线账户")
    private OfflineAccountVO offlineAccount;
}

