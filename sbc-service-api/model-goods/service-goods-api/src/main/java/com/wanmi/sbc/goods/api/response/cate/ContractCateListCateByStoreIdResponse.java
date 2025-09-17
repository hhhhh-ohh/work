package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>查询店铺已签约的类目列表相应类</p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateListCateByStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = -3155642478698447309L;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private List<GoodsCateVO> goodsCateList;
}
