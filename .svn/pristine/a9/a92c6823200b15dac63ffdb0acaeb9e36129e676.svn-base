package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.enums.ParticipateType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 11:08
 */
@Schema
@Data
public class MarketingQueryBaseDTO extends BaseQueryRequest {

    /**
     * 活动名称
     */
    @Schema(description = "营销活动名称")
    private String marketingName;

    /**
     * 活动类型
     */
    @Schema(description = "营销活动子类型")
    private MarketingSubType marketingSubType;

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
     * 目标客户
     */
    @Schema(description = "目标客户")
    private Long targetLevelId;

    /**
     * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
     */
    @Schema(description = "查询类型")
    private MarketingStatus queryTab;

    /**
     * 删除标记  0：正常，1：删除
     */
    @Schema(description = "是否已删除")
    public DeleteFlag delFlag;

    /**
     * 是否显示店铺名称
     */
    @Schema(description = "是否显示店铺名称")
    private Boolean showStoreNameFlag;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     */
    @Schema(description = "促销类型")
    private MarketingType marketingType;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     */
    @Schema(description = "促销类型")
    private List<MarketingType> marketingTypeList;

    /**
     * 多个店铺名称
     */
    @Schema(description = "多个店铺id")
    private List<Long> storeIds;

    @Schema(description = "参与门店")
    private ParticipateType participateType;

}
