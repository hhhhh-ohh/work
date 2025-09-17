package com.wanmi.sbc.marketing.api.request.fullreturn;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author 黄昭
 * @className FullReturnPageRequest
 * @description TODO
 * @date 2022/4/7 10:14
 **/
@Data
@Schema
public class FullReturnPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -9190946746237146052L;
    /**
     * 营销名称
     */
    @Schema(description = "营销名称")
    private String marketingName;

    /**
     * 参与店铺是：0全部，1指定店铺
     */
    @Schema(description = "参与店铺是：0全部，1指定店铺")
    private Integer storeType;

    /**
     * 店铺名称(模糊查询)
     */
    @Schema(description = "铺名称(模糊查询)")
    private String storeName;

    /**
     * supplier端查询客户等级
     */
    @Schema(description = "supplier端查询客户等级")
    private Long targetLevelId;

    /**
     * boss端查询客户等级
     */
    @Schema(description = "boss端查询客户等级")
    private Long bossJoinLevel;

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
     * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
     */
    @Schema(description = "查询类型")
    private MarketingStatus queryTab;

    /**
     * 是否是平台，1：boss，0：商家
     */
    @Schema(description = "是否是平台，1：boss，0：商家")
    private BoolFlag isBoss;
}