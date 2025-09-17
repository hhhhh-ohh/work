package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/4/26 13:51
 * @description <p> 拖拽排序 </p>
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsGoodsPropCateSortRequest extends BaseRequest {

    private static final long serialVersionUID = -7570905176121732444L;

    /**
     * 商品类目与属性关联列表结果
     */
    @Schema(description = "类目属性拖拽排序")
    private List<GoodsPropertyVO> goodsPropCateVOList;
}
