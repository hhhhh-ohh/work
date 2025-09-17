package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GoodsInfoSpecDetailRelSimpleVO
 *
 * @author zhanggaolei
 * @dateTime 2021/08/9 下午2:36
 */
@Schema
@Data
public class GoodsInfoSpecDetailRelSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -6705853990928847703L;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 规格值ID
     */
    @Schema(description = "规格值ID")
    private Long specDetailId;


}
