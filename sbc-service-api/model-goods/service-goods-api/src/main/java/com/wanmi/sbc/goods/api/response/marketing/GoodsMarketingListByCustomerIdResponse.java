package com.wanmi.sbc.goods.api.response.marketing;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品营销</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMarketingListByCustomerIdResponse extends BasicResponse {

    private static final long serialVersionUID = -8165878580713909484L;

    @Schema(description = "商品营销")
    private List<GoodsMarketingVO> goodsMarketings;

}
