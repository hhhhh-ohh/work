package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.enums.ParticipateType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>营销满折规则</p>
 * author: sunkun
 * Date: 2018-11-20
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingSaveDTO implements Serializable {

    private static final long serialVersionUID = 3245414890077391250L;

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
     * 营销类型 0：满减 1:满折 2:满赠 3:一口价营销活动,4:第二件半价营销活动
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
     * 参加营销类型 0：全部货品 1：货品 2：品牌 3：分类
     */
    @Schema(description = "参加营销类型范围")
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
     * 商家Id  0：boss,  other:其他商家
     */
    @Schema(description = "商家Id，0：boss, other:其他商家")
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
     * 营销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠 6：一口价 7:第二件半价营销活动
     */
    @Schema(description = "营销子类型")
    @NotNull
    private MarketingSubType subType;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private PluginType pluginType;

    @Schema(description = "参与门店")
    private ParticipateType participateType;

    @Schema(description = "参与门店列表")
    private List<Long> participateStoreList;

    /**
     * 是否暂停（1：暂停，0：正常）
     */
    @Schema(description = "是否暂停（1：暂停，0：正常）")
    private DefaultFlag isPause;

    /**
     * 营销范围Id
     */
    @Schema(description = "营销范围Id集合")
    private List<String> scopeIds;

    public MarketingDTO generateMarketing() {
        MarketingDTO marketing = new MarketingDTO();

        marketing.setMarketingName(marketingName);
        marketing.setMarketingType(marketingType);
        marketing.setSubType(subType);
        marketing.setBeginTime(beginTime);
        marketing.setEndTime(endTime);
        marketing.setScopeType(scopeType);
        marketing.setJoinLevel(joinLevel);
        marketing.setIsBoss(isBoss);
        marketing.setStoreId(storeId);
        marketing.setCreatePerson(createPerson);
        marketing.setUpdatePerson(updatePerson);
        marketing.setDeletePerson(deletePerson);

        return marketing;
    }

    public List<MarketingScopeDTO> generateMarketingScopeList(Long marketingId) {
        if(scopeIds == null) {
            return new ArrayList<>();
        }
        return scopeIds.stream().map((scopeId) -> {
            MarketingScopeDTO scope = new MarketingScopeDTO();
            scope.setMarketingId(marketingId);
            scope.setScopeId(scopeId);

            return scope;
        }).collect(Collectors.toList());
    }
}
