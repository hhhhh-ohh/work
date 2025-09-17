package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品SPU属性查询请求
 * Created by bail on 2018/3/23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsPropQueryRequest extends BaseQueryRequest {

    // 搜索属性
    @Schema(description = "搜索属性")
    private Long propId;
    // 搜索属性
    @Schema(description = "搜索属性名称")
    private String propName;
    // 搜索属性值
    @Schema(description = "搜索属性值")
    private List<Long> detailIds = new ArrayList<>();
}
