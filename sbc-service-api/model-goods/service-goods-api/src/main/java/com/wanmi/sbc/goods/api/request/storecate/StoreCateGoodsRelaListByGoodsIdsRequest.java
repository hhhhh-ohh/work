package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanggang
 * @version 1.0
 * @createDate 2018/11/1 10:00
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateGoodsRelaListByGoodsIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -5068649678829183110L;

    @Schema(description = "商品Id")
    @NotNull
    private List<String> goodsIds;
}
