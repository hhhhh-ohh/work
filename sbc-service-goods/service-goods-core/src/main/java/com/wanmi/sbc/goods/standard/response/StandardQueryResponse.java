package com.wanmi.sbc.goods.standard.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品库查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardQueryResponse extends BasicResponse {

    /**
     * 商品分页数据
     */
    private Page<StandardGoodsVO> standardGoodsPage ;

    /**
     * 商品SKU全部数据
     */
    private List<StandardSkuVO> standardSkuList ;

    /**
     * 商品SKU的规格值全部数据
     */
    private List<StandardSkuSpecDetailRelVO> standardSkuSpecDetails ;

    /**
     * 商品品牌所有数据
     */
    private List<GoodsBrandVO> goodsBrandList ;

    /**
     * 商品分类所有数据
     */
    private List<GoodsCateVO> goodsCateList ;

    /**
     * 已被导入的商品库ID
     */
    private List<String> usedStandard ;
}
