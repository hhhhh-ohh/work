package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 14:13
 */
@Schema
@Data
public class MarketingVO extends BasicResponse {

    private static final long serialVersionUID = -2300174769405855185L;
    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 营销名称
     */
    @Schema(description = "营销名称")
    private String marketingName;

    /**
     * 营销类型 0：满减 1:满折 2:满赠 3.组合套餐
     */
    @Schema(description = "营销活动类型")
    private MarketingType marketingType;

    /**
     * 营销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠 6.组合活动
     */
    @Schema(description = "营销子类型")
    private MarketingSubType subType;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 参加营销类型 0：全部货品 1：货品 2：品牌 3：分类
     */
    @Schema(description = "参加营销类型")
    private MarketingScopeType scopeType;

    /**
     * 参加会员 0:全部等级  other:其他等级
     */
    @Schema(description = "参加会员", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private String joinLevel;

    /**
     * 是否是商家，0：商家，1：boss
     */
    @Schema(description = "是否是商家")
    private BoolFlag isBoss;

    /**
     * 商家Id  0：boss,  other:其他商家
     */
    @Schema(description = "商家Id，0：boss, other:其他商家")
    private Long storeId;

    /**
     * 删除标记  0：正常，1：删除
     */
    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 是否暂停 0：正常，1：暂停
     */
    @Schema(description = "是否暂停")
    private BoolFlag isPause;

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
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

    /**
     * 审核失败原因
     */
    @Schema(description = "审核失败原因")
    private String refuseReason;

    /**
     * 关联商品
     */
    @Schema(description = "营销关联商品列表")
    private List<MarketingScopeVO> marketingScopeList;

    /**
     * joinLevel的衍射属性，获取枚举
     */
    @Schema(description = "关联客户等级")
    private MarketingJoinLevel marketingJoinLevel;

    /**
     * joinLevel的衍射属性，marketingJoinLevel == LEVEL_LIST 时，可以获取对应的等级集合
     */
    @Schema(description = "关联其他等级的等级id集合")
    private List<Long> joinLevelList;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String storeName;

    /**
     * 等级名称
     */
    @Schema(description = "等级名称")
    private String joinLevelName;

    /**
     * 关联的分类、品牌名称
     */
    @Schema(description = "关联的分类、品牌名称")
    private List<String> scopeNames;

    /**
     * 参与门店类型
     */
    @Schema(description = "参与门店类型")
    private ParticipateType participateType;

    /**
     * 营销审核状态
     */
    @Schema(description = "营销审核状态")
    private AuditStatus auditStatus;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private PluginType pluginType;
}
