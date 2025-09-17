package com.wanmi.sbc.elastic.api.provider.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.goods.EsDistributorGoodsListQueryRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES商品查询服务
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsGoodsInfoElasticQueryProvider")
public interface EsGoodsInfoElasticQueryProvider {

    @Deprecated
    @PostMapping("/elastic/${application.elastic.version}/goods/page")
    BaseResponse<EsGoodsInfoResponse> page(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/page/by/goods")
    BaseResponse<EsGoodsResponse> pageByGoods(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/distributor/list/by/customer/id")
    BaseResponse<EsGoodsInfoResponse> distributorGoodsListByCustomerId(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/distributor/list")
    BaseResponse<EsGoodsInfoResponse> distributorGoodsList(@RequestBody @Valid EsDistributorGoodsListQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/select-options")
    BaseResponse<EsGoodsSelectOptionsResponse> selectOptions(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/spu-page")
    BaseResponse<EsGoodsSimpleResponse> spuPage(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/sku-page")
    BaseResponse<EsGoodsInfoSimpleResponse> skuPage(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/sku-page-by-all-params")
    public BaseResponse<EsGoodsInfoSimpleResponse> skuPageByAllParams(@RequestBody @Valid EsGoodsInfoQueryRequest request);

    @PostMapping("/elastic/${application.elastic.version}/goods/info/list")
    BaseResponse<EsBaseInfoByParamsResponse> getEsBaseInfoByParams(@RequestBody @Valid EsGoodsInfoQueryRequest request);
}
