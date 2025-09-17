package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>获取已使用的标品库响应</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsGetUsedStandardResponse extends BasicResponse {

    private static final long serialVersionUID = -7565364291728643394L;

    @Schema(description = "商品库Id")
    private List<String> standardIds;
}
