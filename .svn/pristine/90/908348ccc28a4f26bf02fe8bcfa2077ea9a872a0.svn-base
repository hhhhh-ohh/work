package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSaveVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品查询请求
 * Created by CHENLI on 2019/2/19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterPriseGoodsQueryResponse extends BasicResponse {

    /**
     * 分销商品分页数据
     */
    private MicroServicePage<GoodsInfoSaveVO> goodsInfoPage = new MicroServicePage<>(new ArrayList<>());

    /**
     * 商品SKU的规格值全部数据
     */
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetails = new ArrayList<>();

    /**
     * 商品品牌所有数据
     */
    private List<GoodsBrandVO> goodsBrandList = new ArrayList<>();

    /**
     * 商品分类所有数据
     */
    private List<GoodsCateVO> goodsCateList = new ArrayList<>();

    /**
     * 商家所有数据
     */
    private List<CompanyInfoVO> companyInfoList = new ArrayList<>();

}
