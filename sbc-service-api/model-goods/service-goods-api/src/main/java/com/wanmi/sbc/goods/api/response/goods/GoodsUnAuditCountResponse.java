package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsUnAuditCountResponse
 * 待审核商品统计响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午11:22
 */
@Schema
@Data
public class GoodsUnAuditCountResponse extends BasicResponse {

    private static final long serialVersionUID = 764121465486757109L;

    @Schema(description = "待审核商品统计数量")
    private Long unAuditCount;
}
