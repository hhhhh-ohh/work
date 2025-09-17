package com.wanmi.sbc.marketing.api.request.preferential;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author edz
 * @className PreferentialAddRequest
 * @description 加价购活动请求类
 * @date 2022/11/17 14:13
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferentialAddRequest{


    private static final long serialVersionUID = -1073643849079919962L;

    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 营销名称
     */
    @Schema(description = "营销名称")
    @NotBlank
    @Length(max=40)
    private String marketingName;

    /**
     * 营销类型 0：满减 1:满折 2:满赠
     */
    @Schema(description = "营销活动类型")
    @NotNull
    private MarketingType marketingType;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 参加营销范围 0：全部货品 1：货品 2：品牌 3：分类
     */
    @Schema(description = "参加营销范围")
    @NotNull
    private MarketingScopeType scopeType;

    /**
     * 参加会员 0:全部等级  other:其他等级
     */
    @Schema(description = "参加会员", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    @NotBlank
    private String joinLevel;

    /**
     * 是否是商家，0：boss，1：商家
     */
    @Schema(description = "是否是商家")
    private BoolFlag isBoss;

    /**
     * 店铺id  0：boss,  other:其他商家
     */
    @Schema(description = "店铺id，0：boss, other:其他商家")
    private Long storeId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

    /**
     * 营销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠
     */
    @Schema(description = "营销子类型")
    @NotNull
    private MarketingSubType subType;

    /**
     * 营销范围Id
     */
    @Schema(description = "营销范围Id列表")
    private List<String> scopeIds;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private PluginType pluginType;

    /**
     * 是否暂停（1：暂停，0：正常）
     */
    @Schema(description = "是否暂停（1：暂停，0：正常）")
    private DefaultFlag isPause;

    @Schema(description = "参与门店")
    private ParticipateType participateType;

    @Schema(description = "参与门店列表")
    private List<Long> participateStoreList;

    /**
     * 参与店铺是：0全部，1指定店铺
     */
    @Schema(description = "参与店铺是：0全部，1指定店铺")
    private MarketingStoreType storeType;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id列表")
    private List<Long> storeIds;

    public void setBeginTime(LocalDateTime beginTime) {
        beginTime = beginTime.minusSeconds(beginTime.getSecond());
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        endTime = endTime.minusSeconds(endTime.getSecond());
        this.endTime = endTime;
    }

    @Schema(description = "活动阶梯规则")
    @NotNull
    private List<PreferentialLevelRequest> preferentialLevelList;

    public void valid() {
        Set set;

        if (this.getSubType() == MarketingSubType.PREFERENTIAL_FULL_AMOUNT) {
            set = new HashSet<BigDecimal>();
        } else {
            set = new HashSet<Long>();
        }

        if (getBeginTime().equals(getEndTime()) || getBeginTime().isAfter(getEndTime())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //开始时间不能小于现在
        if(getBeginTime().isBefore(LocalDateTime.now())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //等级不能为空  且 等级数量不能大于5
        if(CollectionUtils.isEmpty(preferentialLevelList) || preferentialLevelList.size() > Constants.FIVE){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //换购商品集合
        List<String> goodsInfoIds = new ArrayList<>();

        // 校验参数，满金额时金额不能为空，满数量时数量不能为空
        preferentialLevelList.stream().forEach((level) -> {

            if (this.getSubType() == MarketingSubType.PREFERENTIAL_FULL_AMOUNT) {
                if (level.getFullAmount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN_PREFERENTIAL)) < 0
                        ||  level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX_PREFERENTIAL)) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080029);
                }

                set.add(level.getFullAmount());
            } else if (this.getSubType() == MarketingSubType.PREFERENTIAL_FULL_COUNT) {
                if (level.getFullCount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MIN) < 0
                        || level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MAX) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080006);
                }

                set.add(level.getFullCount());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (level.getPreferentialDetailList() == null || level.getPreferentialDetailList().isEmpty()) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080030);
            } else if (level.getPreferentialDetailList().size() > Constants.MARKETING_Gift_TYPE_MAX) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080031);
            } else {
                level.getPreferentialDetailList().forEach(detail -> {
                    //换购商品不可重复
                    if(CollectionUtils.isEmpty(goodsInfoIds) || !goodsInfoIds.contains(detail.getGoodsInfoId())){
                        goodsInfoIds.add(detail.getGoodsInfoId());
                    }else{
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080032);
                    }

                    if (detail.getPreferentialAmount() == null || detail.getPreferentialAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080028);
                    }
                });
            }
        });

        if (set.size() != preferentialLevelList.size()) {
            if (this.getSubType() == MarketingSubType.PREFERENTIAL_FULL_AMOUNT) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080011);
            } else {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080012);
            }
        }

        if (this.getScopeType() != MarketingScopeType.SCOPE_TYPE_ALL) {
            if (this.getScopeIds() == null || this.getScopeIds().stream().allMatch(scopeId -> "".equals(scopeId.trim()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (MarketingScopeType.SCOPE_TYPE_CUSTOM.equals(this.getScopeType())
                    && this.getScopeIds().size() > Constants.MARKETING_GOODS_SIZE_MAX) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080027,
                        new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
            }
        }
    }

}


