package com.wanmi.sbc.goods.api.request.goodslabel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>标签排序请求参数</p>
 * @author dyt
 * @date 2020-09-29 13:57:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLabelSortRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-标签idList
     */
    @Schema(description = "标签id集合")
    private List<Long> labelIdList;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id", hidden = true)
    private Long storeId;

}
