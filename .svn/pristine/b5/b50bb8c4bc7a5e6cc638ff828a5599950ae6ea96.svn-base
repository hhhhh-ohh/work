package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * es删除分类平台request
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsCateUpdateNameRequest extends BaseRequest {

    @Schema(description = "商品类目")
    private List<GoodsCateVO> goodsCateListVOList;

}
