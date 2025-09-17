package com.wanmi.sbc.goods.api.response.enterprise;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除企业购商品时返回删除的skuIds
 *
 * @author CHENLI
 * @dateTime 2019/3/26 上午9:33
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseBatchDeleteResponse extends BasicResponse {

    private static final long serialVersionUID = -1652506578599540589L;

    /**
     * 删除企业购商品时返回删除的skuIds
     */
    @Schema(description = "不符合条件的skuIds")
    private List<String> goodsInfoIds = new ArrayList<>();
}
