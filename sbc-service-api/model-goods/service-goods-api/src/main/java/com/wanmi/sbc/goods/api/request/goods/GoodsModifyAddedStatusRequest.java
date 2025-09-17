package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyAddedStatusRequest
 * 修改商品上下架状态请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:51
 */
@Schema
@Data
public class GoodsModifyAddedStatusRequest extends BaseRequest {

    private static final long serialVersionUID = 5761978562132902527L;

    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    @Schema(description = "商品Id")
    private List<String> goodsIds;

    @Schema(description = "商品Id")
    private List<String> goodsInfoIds;

    @Schema(description = "是否定时上架 true:是,false:否")
    private Boolean addedTimingFlag;

    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    @Schema(description = "是否定时下架 true:是,false:否")
    private Boolean takedownTimeFlag;

    @Schema(description = "定时下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime takedownTime;

    @Schema(description = "商品编码")
    private String goodsNo;
    // 是否是定时任务
    BoolFlag jobIn = BoolFlag.NO;
}
