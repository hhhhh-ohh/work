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
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsSpecQueryRequest extends BaseQueryRequest {

    // 搜索规格项
    @Schema(description = "搜索规格项")
    private String name;

    // 搜索规格值
    @Schema(description = "搜索规格值")
    private List<String> values = new ArrayList<>();
}
