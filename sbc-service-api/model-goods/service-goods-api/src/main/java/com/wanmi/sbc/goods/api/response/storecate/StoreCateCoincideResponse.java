package com.wanmi.sbc.goods.api.response.storecate;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 列出二个店铺类型是否存在重合id响应结果
 * @author: dyt
 * @createDate: 2018/12/5 11:42
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateCoincideResponse implements Serializable {
    private static final long serialVersionUID = -7623829946908926729L;

    @Schema(description = "重合的分类id")
    private List<Long> storeCateIds;
}
