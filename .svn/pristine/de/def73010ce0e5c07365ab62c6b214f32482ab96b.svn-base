package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分销商品SKU视图分页响应
 * Created by chenli on 2019/2/20.
 */
@Schema
@Data
public class DistributionGoodsInfoPageResponse extends BasicResponse {

    private static final long serialVersionUID = 7297127214741393930L;

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private MicroServicePage<GoodsInfoVO> goodsInfoPage;

    /**
     * 品牌列表
     */
    @Schema(description = "品牌列表")
    private List<GoodsBrandVO> brands = new ArrayList<>();

    /**
     * 分类列表
     */
    @Schema(description = "分类列表")
    private List<GoodsCateVO> cates = new ArrayList<>();

    /**
     * 商品SKU的规格值全部数据
     */
    @Schema(description = "商品SKU的规格值全部数据")
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetails;

    /**
     * 商家信息数据
     */
    @Schema(description = "商家信息数据")
    private List<CompanyInfoVO> companyInfoList = new ArrayList<>();
}
