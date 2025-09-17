package com.wanmi.sbc.goods.api.response.common;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 批量导入商品信息响应对象
 *
 * @author daiyitian
 * @dateTime 2018/11/2 上午9:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommonBatchAddResponse extends BasicResponse {

    private static final long serialVersionUID = 8925167226133296629L;

    /**
     * 批量新增商品的id
     */
    @Schema(description = "批量新增商品的id")
    private List<String> skuNoList;
}
