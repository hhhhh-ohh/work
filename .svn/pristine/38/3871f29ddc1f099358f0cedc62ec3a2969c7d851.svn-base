package com.wanmi.sbc.elastic.distributioninvitenew.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.enums.FailReasonFlag;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.RewardFlag;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * 邀新记录实体
 */
@Data
@Document(indexName = EsConstants.INVITE_NEW_RECORD)
public class EsInviteNewRecord  {


    @Id
    private String recordId;
    /**
     * 受邀人ID
     */
    private String invitedNewCustomerId;

    /**
     * 邀请人ID
     */
    private String requestCustomerId;


    /**
     * 是否有效邀新 0：否 1：是
     */
    @Enumerated
    private InvalidFlag availableDistribution = InvalidFlag.NO;

    /**
     * 是否是分销员 0: 否  1：是
     */
    private Integer distributor = 0;

    /**
     * 注册时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime registerTime;

    /**
     * 首次下单时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime firstOrderTime;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单完成时间
     */

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderFinishTime;

    /**
     * 奖励是否入账 0:否 1:是
     */
    @Enumerated
    private InvalidFlag rewardRecorded = InvalidFlag.NO;

    /**
     * 奖励入账时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime rewardRecordedTime;

    /**
     * 奖励金额
     */
    private BigDecimal rewardCash = BigDecimal.ZERO;

    /**
     * 后台配置的奖励优惠券id，多个以逗号分隔
     */
    private String settingCoupons;

    /**
     * 后台配置的奖励金额
     */
    private BigDecimal settingAmount = BigDecimal.ZERO;

    /**
     * 未入账原因(0:非有效邀新，1：奖励达到上限，2：奖励未开启)
     */
    @Enumerated
    private FailReasonFlag failReasonFlag;

    /**
     * 入账类型（0：现金 1：优惠券）
     */
    @Enumerated
    private RewardFlag rewardFlag;

    /**
     * 入账优惠券
     */
    private String rewardCoupon;

    /**
     * 优惠券组集合信息（id：count）
     */
    private String settingCouponIdsCounts;
}
