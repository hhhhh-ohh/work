package com.wanmi.sbc.goods.api.request.goodstemplate;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangzhao
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateJoinRequest implements Serializable {

    private static final long serialVersionUID = -2076866866907016149L;
    @Schema(description = "商品模版Id")
    @NotNull
    private Long templateId;

    @Schema(description = "关联商品Id")
    private List<String> joinGoodsIdList;

    @Schema(description = "关联商品名称")
    private List<String> joinGoodsNameList;

    @Schema(description = "操作人")
    private String userId;
}
