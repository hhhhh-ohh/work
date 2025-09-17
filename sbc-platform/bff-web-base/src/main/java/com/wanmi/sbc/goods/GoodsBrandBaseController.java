package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandCenterVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * 商品品牌controller
 * Created by sunkun on 2017/8/4.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsBrandBaseController", description = "S2B web公用-商品品牌信息API")
public class GoodsBrandBaseController {

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    /**
     * 条件查询商品品牌列表
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "条件查询商品品牌列表")
    @RequestMapping(value = {"/allGoodsBrands"}, method = RequestMethod.GET)
    public BaseResponse<List<GoodsBrandVO>> list(GoodsBrandListRequest queryRequest) {
        return BaseResponse.success(goodsBrandQueryProvider.list(queryRequest).getContext().getGoodsBrandVOList());
    }

    /**
     * 品牌中心
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "品牌中心品牌列表")
    @RequestMapping(value = {"/brandCenter"}, method = RequestMethod.GET)
    public BaseResponse<Map<String,List<GoodsBrandCenterVO>>> centerList(GoodsBrandListRequest queryRequest) {
        return BaseResponse.success(goodsBrandQueryProvider.center(queryRequest).getContext().getGoodsBrandVOListMap());
    }

    /**
     * 根据商品品牌id查询商品品牌信息
     * @param brandId
     * @return
     */
    @Operation(summary = "根据商品品牌id查询商品品牌信息")
    @Parameter( name = "brandId", description = "商品品牌id", required = true)
    @RequestMapping(value = {"/goodsBrand/{brandId}"}, method = RequestMethod.GET)
    public BaseResponse<GoodsBrandByIdResponse> list(@PathVariable Long brandId) {
        return goodsBrandQueryProvider.getById(GoodsBrandByIdRequest.builder().brandId(brandId).build());
    }
}
