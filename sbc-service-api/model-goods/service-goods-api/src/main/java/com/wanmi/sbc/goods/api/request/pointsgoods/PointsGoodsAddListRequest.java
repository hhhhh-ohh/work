package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yang
 * @since 2019/5/14
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "积分商品信息")
    List<PointsGoodsAddRequest> pointsGoodsAddRequestList;

    /**
     * 兑换开始时间
     */
    @Schema(description = "兑换开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 兑换结束时间
     */
    @Schema(description = "兑换结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;
}
