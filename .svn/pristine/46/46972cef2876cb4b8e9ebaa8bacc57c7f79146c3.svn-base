package com.wanmi.sbc.marketing.common.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnStore;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Convert;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MarketingResponse extends BasicResponse {

    /**
     * 促销Id
     */
    private Long marketingId;

    /**
     * 促销名称
     */
    private String marketingName;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     */
    private MarketingType marketingType;

    /**
     * 促销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠
     */
    private MarketingSubType subType;

    /**
     * 开始时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 参加促销类型 0：全部货品 1：货品 2：品牌 3：分类
     */
    private MarketingScopeType scopeType;

    /**
     * 参加会员 0:全部等级 -1:全部用户 other:其他等级
     */
    private String joinLevel;

    /**
     * 是否是商家，0：商家，1：boss
     */
    private BoolFlag isBoss;

    /**
     * 商家Id  0：boss,  other:其他商家
     */
    private Long storeId;

    /**
     * 删除标记  0：正常，1：删除
     */
    private DeleteFlag delFlag;

    /**
     * 是否暂停 0：正常，1：暂停
     */
    private BoolFlag isPause;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 修改时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 删除时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    private String deletePerson;

    /**
     * 关联商品
     */
    private List<MarketingScopeVO> marketingScopeList;

    /**
     * 满减等级
     */
    private List<MarketingFullReductionLevelVO> fullReductionLevelList;

    /**
     * 满折等级
     */
    private List<MarketingFullDiscountLevelVO> fullDiscountLevelList;

    /**
     * 满赠等级
     */
    private List<MarketingFullGiftLevelVO> fullGiftLevelList;

    /**
     * 加价购等级
     */
    private List<MarketingPreferentialLevelVO> preferentialLevelList;

    /**
     * 一口价等级
     */

    private List<MarketingBuyoutPriceLevelVO> buyoutPriceLevelList;

    /**
     * 第二件半价
     */
    private List<MarketingHalfPriceSecondPieceLevelVO> halfPriceSecondPieceLevel;

    /**
     * 满返
     */
    private List<MarketingFullReturnLevel> fullReturnLevelList;

    /**
     * 赠券列表
     */
    private List<CouponInfoVO> returnList = new ArrayList<>();

    /**
     * 营销关联商品
     */
    private GoodsInfoResponseVO goodsList;

    /**
     * 关联的分类、品牌名称
     */
    private List<String> scopeNames;

    /**
     * 商家名称
     */
    private String storeName;

    /**
     * 参与门店类型
     */
    private ParticipateType participateType;

    /**
     * 参与门店数量
     */
    private Long storeNum;

    /**
     * 营销类型
     */
    private PluginType pluginType;

    /**
     * 营销审核状态
     */
    private AuditStatus auditStatus;

    /**
     * 审核失败原因
     */
    private String refuseReason;

    /**
     * 店铺类型
     */
    private MarketingStoreType storeType;

    /**
     * 参与门店列表
     */
    private List<StoreVO> participateStoreList;

    /**
     * 满返店铺信息
     */
    private List<MarketingFullReturnStore> marketingFullReturnStoreList;

    public MarketingJoinLevel getMarketingJoinLevel() {
        if(joinLevel.equals(Constants.STR_0)){
            return MarketingJoinLevel.ALL_LEVEL;
        }else if(joinLevel.equals(Constants.STR_MINUS_1)){
            return MarketingJoinLevel.ALL_CUSTOMER;
        }else{
            return MarketingJoinLevel.LEVEL_LIST;
        }
    }

    public List<Long> getJoinLevelList() {
        return Arrays.stream(joinLevel.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

}
