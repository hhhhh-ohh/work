package com.wanmi.sbc.goods.api.request.spec;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 根据goodsIds批量查询请求结构
 *
 * @author daiyitian
 * @dateTime 2018/11/13 上午9:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSpecListByGoodsIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 7485086248180516195L;

    @Schema(description = "spu Id")
    @NotEmpty
    private List<String> goodsIds;
}
