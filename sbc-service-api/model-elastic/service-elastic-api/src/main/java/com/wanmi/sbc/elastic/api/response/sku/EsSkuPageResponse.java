package com.wanmi.sbc.elastic.api.response.sku;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品SKU视图分页响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class EsSkuPageResponse extends EsSkuPageBaseResponse{

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private MicroServicePage<GoodsInfoVO> goodsInfoPage;
}
