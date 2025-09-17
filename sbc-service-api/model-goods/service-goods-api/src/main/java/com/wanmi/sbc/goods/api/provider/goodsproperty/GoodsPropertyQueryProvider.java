package com.wanmi.sbc.goods.api.provider.goodsproperty;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodsproperty.*;
import com.wanmi.sbc.goods.api.response.goodsproperty.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import java.math.BigDecimal;

/**
 * 商品属性查询服务Provider
 *
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyQueryProvider")
public interface GoodsPropertyQueryProvider {

    /**
     * 分页查询商品属性API
     *
     * @author chenli
     * @param goodsPropertyPageReq 分页请求参数和筛选对象 {@link GoodsPropertyPageRequest}
     * @return 商品属性分页列表信息 {@link GoodsPropertyPageResponse}
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/goods-property-page")
    BaseResponse<GoodsPropertyPageResponse> findGoodsPropertyPage(
            @RequestBody @Valid GoodsPropertyPageRequest goodsPropertyPageReq);

    /**
     * 列表查询商品属性API
     *
     * @author chenli
     * @param goodsPropertyListReq 列表请求参数和筛选对象 {@link GoodsPropertyListRequest}
     * @return 商品属性的列表信息 {@link GoodsPropertyListResponse}
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/list")
    BaseResponse<GoodsPropertyListResponse> list(
            @RequestBody @Valid GoodsPropertyListRequest goodsPropertyListReq);

    /**
     * 单个查询商品属性API
     *
     * @author chenli
     * @param goodsPropertyByIdRequest 单个查询商品属性请求参数 {@link GoodsPropertyByIdRequest}
     * @return 商品属性详情 {@link GoodsPropertyByIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/get-by-id")
    BaseResponse<GoodsPropertyByIdResponse> getGoodsPropertyById(
            @RequestBody @Valid GoodsPropertyByIdRequest goodsPropertyByIdRequest);

    /**
     * 单个查询商品属性API
     *
     * @author chenli
     * @param goodsPropertyByIdRequest 单个查询商品属性请求参数 {@link GoodsPropertyByIdRequest}
     * @return 商品属性详情 {@link GoodsPropertyByIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/get-sort-by-id")
    BaseResponse<GoodsPropertySortResponse> getGoodsPropSortById(
            @RequestBody @Valid GoodsPropertyByIdRequest goodsPropertyByIdRequest);

    /**
     * 根据商品id查询商品属性API
     *
     * @author chenli
     * @param goodsPropertyByGoodsIdRequest 单个查询商品属性请求参数 {@link GoodsPropertyByGoodsIdRequest}
     * @return 商品属性详情 {@link GoodsPropertyListForGoodsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/get-by-spu-id")
    BaseResponse<GoodsPropertyListForGoodsResponse> getGoodsPropertyListForGoods(
            @RequestBody @Valid GoodsPropertyByGoodsIdRequest goodsPropertyByGoodsIdRequest);
    @PostMapping("/goods/${application.goods.version}/goodsproperty/get-by-spu-id-type")
    BaseResponse<BigDecimal> getGoodsPropertyListForGoodsByType(
            @RequestBody @Valid GoodsPropertyByGoodsIdRequest goodsPropertyByGoodsIdRequest);
    /**
     * 入库es数据
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/goodsproperty/get-by-spu-rel")
    BaseResponse<GoodsPropertyByIdsListResponse> getGoodsPropRelNests(@RequestBody @Valid GoodsPropertyByIdRequest request);
}
