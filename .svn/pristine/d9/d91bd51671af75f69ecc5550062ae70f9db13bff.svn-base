package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>获取叶子分类列表响应结构</p>
 * author: daiyitian
 * Date: 2018-11-05
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCateLeafResponse extends BasicResponse {

    private static final long serialVersionUID = 3202511085839617470L;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private List<GoodsCateVO> goodsCateList;
}
