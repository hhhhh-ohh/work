package com.wanmi.sbc.goods.api.request.goods;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品查询请求
 *
 * @author daiyitian
 * @date 2017/3/24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodColsByConditionRequest extends GoodsByConditionRequest {

    @NotEmpty
    @Schema(description = "指定字段")
    private List<String> cols;

}
