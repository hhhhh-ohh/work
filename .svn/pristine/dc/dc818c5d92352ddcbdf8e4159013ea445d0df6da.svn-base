package com.wanmi.sbc.marketing.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponActivitySource;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Schema
@Data
public class CouponActivityModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1748367079674585029L;

    @Schema(description = "优惠券活动id")
    @NotBlank
    private String activityId;

    /**
     * 优惠券活动名称
     */
    @Schema(description = "优惠券活动名称")
    @NotBlank
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
    @NotNull
    private CouponActivityType couponActivityType;

    /**
     * 是否暂停 ，1 暂停
     */
    private DefaultFlag pauseFlag;

    /**
     * 是否限制领取优惠券次数，0 每人限领次数不限，1 每人限领N次
     */
    @Schema(description = "是否限制领取优惠券次数")
    @NotNull
    private DefaultFlag receiveType;

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
    @Schema(description = "是否是平台")
    private DefaultFlag platformFlag;

    /**
     * 关联的客户等级   -1:全部客户 0:全部等级 other:其他等级 ,
     */
    @Schema(description = "关联的客户等级", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    @NotBlank
    private String joinLevel;

    @Schema(description = "修改人")
    private String updatePerson;

    @Schema(description = "优惠券活动配置信息")
    private List<CouponActivityConfigSaveRequest> couponActivityConfigs;

    /**
     * 促销目标客户范围Ids
     */
    @Schema(description = "促销目标客户范围Id列表")
    @Size(max = 1000)
    private List<String> customerScopeIds;


    /**
     * 剩余优惠券组数
     */
    @Schema(description = "剩余优惠券组数")
    private Integer leftGroupNum;

    /**
     * 增加优惠券组数
     */
    @Schema(description = "增加优惠券组数")
    private Integer increaseGroupNum;

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
     * 活动所属平台（目前只有门店用到）
     */
    @Schema(description = "活动所属平台")
    private PluginType pluginType;

    /**
     * 任务扫描间隔,单位分钟
     */
    @Schema(description = "任务扫描间隔,单位分钟")
    private int withinTime;

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

    @Override
    public void checkParam() {
        if (this.receiveType == DefaultFlag.YES && (this.receiveCount == null || this.receiveCount == 0)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //指定赠券
        if (Objects.equals(CouponActivityType.SPECIFY_COUPON, couponActivityType)
                ) {
            //促销目标客户范围Ids
            if (Objects.equals(String.valueOf(MarketingJoinLevel.SPECIFY_CUSTOMER.toValue()), joinLevel)) {
                if (CollectionUtils.isEmpty(customerScopeIds) || customerScopeIds.size() > Constants.NUM_1000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            } else {
                if (CollectionUtils.isNotEmpty(customerScopeIds)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }

        }

        if(this.couponActivityType == CouponActivityType.REGISTERED_COUPON || this.couponActivityType == CouponActivityType.STORE_COUPONS){
            if(StringUtils.isBlank(this.activityTitle) || StringUtils.isBlank(this.activityDesc)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        //开始时间不能晚于结束时间
        if(!Objects.equals(CouponActivityType.DISTRIBUTE_COUPON, couponActivityType) &&
                !Objects.equals(CouponActivityType.RIGHTS_COUPON, couponActivityType)) {
            if (Objects.isNull(startTime) || Objects.isNull(endTime) || startTime.isAfter(endTime)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }


    }


}
