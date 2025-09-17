package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Data;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 11:17
 */
@Schema
@Data
public class MarketingPageVO extends BasicResponse {

    private static final long serialVersionUID = 4043264917929737209L;
    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String marketingName;

    /**
     * 活动类型
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
     * 目标客户
     */
    @Schema(description = "参加会员", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private String joinLevel;

    /**
     * 是否暂停
     */
    @Schema(description = "是否暂停")
    private BoolFlag isPause;

    /**
     * 是否是商家，0：商家，1：boss
     */
    @Schema(description = "是否商家")
    private BoolFlag isBoss;

    /**
     *参与门店
     */
    @Schema(description = "参与门店")
    private String participateStore;

    /**
     * 活动状态
     */
    @Schema(description = "活动状态")
    private MarketingStatus marketingStatus;

    /**
     * 关联商品
     */
    @Schema(description = "关联商品列表")
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
     * 获取活动状态
     *
     * @return
     */
    public MarketingStatus getMarketingStatus() {
        if (beginTime != null && endTime != null) {
            if (LocalDateTime.now().isBefore(beginTime)) {
                return MarketingStatus.NOT_START;
            } else if (LocalDateTime.now().isAfter(endTime)) {
                return MarketingStatus.ENDED;
            } else if (isPause == BoolFlag.YES) {
                return MarketingStatus.PAUSED;
            } else {
                return MarketingStatus.STARTED;
            }
        }
        return null;
    }

    /**
     * 营销规则
     */
    @Schema(description = "关联营销规则列表")
    private List<String> rulesList;

    /**
     * 店铺Id
     */
    @Schema(description = "关联店铺Id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "关联店铺名称")
    private String storeName;

    /**
     * 等级名称
     */
    @Schema(description = "等级名称")
    private String levelName;

    /**
     * 营销审核状态
     */
    @Schema(description = "营销审核状态")
    private AuditStatus auditStatus;

    /**
     * 审核失败原因
     */
    @Schema(description = "审核失败原因")
    private String refuseReason;

    /**
     * 门店参与类型
     */
    @Schema(description = "门店参与类型")
    private ParticipateType participateType;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private PluginType pluginType;

    /**
     * 参与门店数量
     */
    @Schema(description = "营销类型")
    private Integer num;


    /**
     * 营销活动类型
     */
    @Schema(description = "营销活动类型")
    private MarketingType marketingType;

    @Schema(description = "营销规则展示")
    private String ruleText;

    /**
     * 参与店铺是：0全部，1指定店铺
     */
    @Schema(description = "参与店铺是：0全部，1指定店铺")
    private MarketingStoreType storeType;

    /**
     * 获取参与门店字段
     * @return
     */
    public String getParticipateStore(){
        String participateStoreStr = null;
        if(isBoss == BoolFlag.YES){
            if(participateType != null){
                switch (participateType){
                    case PART:
                        if(Objects.nonNull(num) && num.equals(1)){
                            participateStoreStr = storeName;
                        }else {
                            participateStoreStr = "部分门店";
                        }
                        break;
                    case ALL:
                        participateStoreStr = "全部门店";
                        break;
                    default:
                        break;
                }
            }
        }else {
            participateStoreStr = storeName;
        }
        return participateStoreStr;
    }
}
