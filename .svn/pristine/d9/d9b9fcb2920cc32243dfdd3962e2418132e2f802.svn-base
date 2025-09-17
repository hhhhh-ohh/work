package com.wanmi.sbc.goods.api.response.goodstemplate;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huangzhao
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class goodsTemplateJoinResponse {

    @Schema(description = "模版Id")
    private Long templateId;

    @Schema(description = "关联商品Id")
    private List<String> goodsIdList;
}
