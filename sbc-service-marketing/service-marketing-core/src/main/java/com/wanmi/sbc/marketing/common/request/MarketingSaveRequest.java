package com.wanmi.sbc.marketing.common.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import com.wanmi.sbc.marketing.common.model.validgroups.NotMarketingId;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnStore;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 营销满折规则
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketingSaveRequest extends BaseRequest {
    /**
     * 促销Id
     */
    @NotNull(groups = {NotMarketingId.class})
    private Long marketingId;

    /**
     * 促销名称
     */
    @NotBlank
    @Length(max = 40)
    private String marketingName;

    /**
     * 促销类型 0：满减 1:满折 2:满赠 3:一口价
     */
    @NotNull
    private MarketingType marketingType;

    /**
     * 开始时间
     */
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 参加促销类型 0：全部货品 1：货品 2：品牌 3：分类
     */
    @NotNull
    private MarketingScopeType scopeType;

    /**
     * 参加会员 0:全部等级  other:其他等级
     */
    @NotBlank
    private String joinLevel;

    /**
     * 是否是商家，0：boss，1：商家
     */
    private BoolFlag isBoss;

    /**
     * 商家Id  0：boss,  other:其他商家
     */
    private Long storeId;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 删除人
     */
    private String deletePerson;

    /**
     * 促销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠，6:一口价
     */
    @NotNull
    private MarketingSubType subType;

    /**
     * 促销范围Id
     */
    private List<String> scopeIds;

    /**
     * 促销店铺Id
     */
    private List<Long> storeIds;

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
     * 审核
     */
    @Schema(description = "审核")
    private AuditStatus auditStatus;

    /**
     * 是否暂停（1：暂停，0：正常）
     */
    private DefaultFlag pauseFlag;

    /**
     * 参与店铺是：0全部，1指定店铺
     */
    private MarketingStoreType storeType;

    public Marketing generateMarketing() {
        Marketing marketing = new Marketing();

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
        marketing.setParticipateType(participateType);
        marketing.setStoreType(storeType);

        return marketing;
    }

    public List<MarketingScope> generateMarketingScopeList(Long marketingId) {
        if(scopeIds == null) {
            return new ArrayList<>();
        }
        return scopeIds.stream().map((scopeId) -> {
            MarketingScope scope = new MarketingScope();
            scope.setMarketingId(marketingId);
            scope.setScopeId(scopeId);

            return scope;
        }).collect(Collectors.toList());
    }

    public List<MarketingFullReturnStore> generateMarketingStoreList(Long marketingId) {
        if(storeIds == null) {
            return new ArrayList<>();
        }
        return storeIds.stream().map((storeId) -> {
            MarketingFullReturnStore marketingFullReturnStore = new MarketingFullReturnStore();
            marketingFullReturnStore.setMarketingId(marketingId);
            marketingFullReturnStore.setStoreId(storeId);

            return marketingFullReturnStore;
        }).collect(Collectors.toList());
    }
}
