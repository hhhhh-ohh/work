package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>优惠券活动表</p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponActivityBaseVO extends BasicResponse {

    private static final long serialVersionUID = -7611369184914438883L;

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
     * 关联的客户等级   -1:全部客户 0:全部等级 other:其他等级 ,
     */
    @Schema(description = "关联的客户等级", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private List<String> joinLevels;

    public List<String> getJoinLevels() {
        return StringUtils.isNotEmpty(joinLevel) ? Splitter.on(",").trimResults().splitToList(joinLevel) : Lists.newArrayList();
    }
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
}
