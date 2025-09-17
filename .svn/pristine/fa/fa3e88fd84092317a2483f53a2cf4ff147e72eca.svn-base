package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponActivitySource;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>优惠券活动表</p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponActivityVO extends BasicResponse {

    private static final long serialVersionUID = -823786821658036989L;

    /**
     * 优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    private String activityId;

    /**
     * 优惠券活动名称
     */
    @Schema(description = "优惠券活动名称")
    private String activityName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 优惠券活动类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券， 4权益赠券
     */
    @Schema(description = "优惠券活动类型")
    private CouponActivityType couponActivityType;

    /**
     * 是否限制领取优惠券次数，0 每人限领次数不限，1 每人限领N次
     */
    @Schema(description = "是否限制领取优惠券次数")
    private DefaultFlag receiveType;

    /**
     * 是否暂停 ，1 暂停
     */
    @Schema(description = "是否暂停")
    private DefaultFlag pauseFlag;

    /**
     * 优惠券被使用后可再次领取的次数，每次仅限领取1张
     */
    @Schema(description = "优惠券被使用后可再次领取的次数，每次仅限领取1张")
    private Integer receiveCount;

    /**
     * 生效终端，逗号分隔 0全部,1.PC,2.移动端,3.APP
     */
    @Schema(description = "生效终端,以逗号分隔", contentSchema = com.wanmi.sbc.marketing.bean.enums.TerminalType.class)
    private String terminals;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 是否平台 0店铺 1平台
     */
    @Schema(description = "是否平台")
    private DefaultFlag platformFlag;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 关联的客户等级   -1:全部客户 0:全部等级 other:其他等级 ,
     */
    @Schema(description = "关联的客户等级", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private String joinLevel;

    /**
     * 是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）
     */
    @Schema(description = "是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）")
    private DefaultFlag joinLevelType;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String delPerson;


    /**
     * 剩余优惠券组数
     */
    @Schema(description = "剩余优惠券组数")
    private Integer leftGroupNum;

    /**
     * 参与成功通知标题
     */
    @Schema(description = "参与成功通知标题")
    private String activityTitle;

    /**
     * 参与成功通知描述
     */
    @Schema(description = "参与成功通知描述")
    private String activityDesc;

    /**
     * 活动所属平台 (0 商家  1 平台  2 门店)
     */
    @Schema(description = "活动所属平台")
    private PluginType pluginType;

    /**
     *  是否审核
     */
    @Schema(description = "是否审核")
    private AuditState auditState;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String refuseReason;

    /**
     * 精准发券 1:已扫描 0或空:未扫描 2:执行失败
     */
    @Schema(description = "精准发券 1:已扫描 0或空:未扫描 2:执行失败")
    private Integer scanType;

    /**
     * 任务版本
     */
    @Schema(description = "任务版本")
    private String scanVersion;

    /**
     * 业务来源 0:sbc系统产生,1:对外接入产生
     */
    @Schema(description = "业务来源 0:sbc系统产生,1:对外接入产生")
    private CouponActivitySource businessSource;

    /**
     * 关联抽奖活动Id，activity_type为8时有值
     */
    @Schema(description = "关联抽奖活动Id，activity_id为8时有值")
    private Long drawActivityId;
}
