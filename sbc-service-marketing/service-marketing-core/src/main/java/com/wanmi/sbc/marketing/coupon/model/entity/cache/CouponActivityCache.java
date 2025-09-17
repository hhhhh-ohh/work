package com.wanmi.sbc.marketing.coupon.model.entity.cache;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.ParticipateType;
import com.wanmi.sbc.marketing.common.BaseBean;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: hht
 * @Date: Created In 11:15 AM 2018/9/12
 * @Description: 优惠券活动缓存
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponActivityCache extends BaseBean{

    private static final long serialVersionUID = 238292065824415989L;

    /**
     * 优惠券活动id
     */
    private String activityId;

    /**
     * 优惠券活动名称
     */
    private String activityName;

    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 优惠券类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券， 4权益赠券
     */
    private CouponActivityType couponActivityType;

    /**
     * 领取类型，0 每人限领次数不限，1 每人限领N次
     */
    private DefaultFlag receiveType;

    /**
     * 是否暂停 ，1 暂停
     */
    private DefaultFlag pauseFlag;

    /**
     * 参与等级
     */
    private String[] joinLevel;

    /**
     * 优惠券被使用后可再次领取的次数，每次仅限领取1张
     */
    private Integer receiveCount;

    /**
     * 生效终端，逗号分隔 0全部,1.PC,2.移动端,3.APP
     */
    private String terminals;

    /**
     * 商户id
     */
    private Long storeId;

    /**
     * 是否平台 0平台 1店铺
     */
    private DefaultFlag platformFlag;

    /**
     * 是否删除标志 0：否，1：是
     */
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     * 优惠券类型 0 普通优惠券  2 O2O运费券
     */
    private PluginType pluginType;

    /**
     *  是否审核
     */
    private AuditState auditState;


    /**
     * 是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）
     */
    private DefaultFlag joinLevelType;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    private ParticipateType participateType;
}
