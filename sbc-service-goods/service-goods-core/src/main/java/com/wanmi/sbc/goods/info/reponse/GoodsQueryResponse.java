package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class GoodsQueryResponse extends BasicResponse {

    /**
     * 商品分页数据
     */
    private Page<GoodsSaveVO> goodsPage = new PageImpl<>(new ArrayList<>());

    /**
     * 商品SKU全部数据
     */
    private List<GoodsInfoSaveVO> goodsInfoList = new ArrayList<>();

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

    /**
     * 存放导入商品库的商品
     */
    private List<String> importStandard = new ArrayList<>();
}
