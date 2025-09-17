package com.wanmi.sbc.marketing.api.request.market;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
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

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingAddRequest extends BaseRequest {

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

}
