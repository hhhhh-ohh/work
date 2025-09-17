package com.wanmi.sbc.elastic.api.response.distributionmatter;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.DistributionGoodsMatterPageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema
@Data
public class EsDistributionGoodsMatterPageResponse extends BasicResponse {

    @Schema(description = "分页分销商品素材信息")
    private MicroServicePage<DistributionGoodsMatterPageVO> distributionGoodsMatterPage;

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