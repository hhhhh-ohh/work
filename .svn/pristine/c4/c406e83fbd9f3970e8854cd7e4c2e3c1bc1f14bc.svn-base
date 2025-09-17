package com.wanmi.sbc.goods.api.response.flashsalegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * program: sbc-micro-service
 *
 * @date 2019-06-17 14:15
 **/

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsInProgressResp implements Serializable {

    private static final long serialVersionUID = 3525259108495609178L;

    @Schema(description = "秒杀活动商品")
    private List<FlashSaleGoodsVO> flashSaleGoodsVOS;

    @Schema(description = "系统时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime serverTime;
}