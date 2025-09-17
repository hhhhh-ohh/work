package com.wanmi.sbc.elastic.provider.impl.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsDistributorGoodsListQueryRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.*;
import com.wanmi.sbc.elastic.api.response.goods.EsBaseInfoByParamsResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsResponse;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.goods.service.EsGoodsInfoElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: songhanlin
 * @Date: Created In 18:19 2020/11/30
 * @Description: TODO
 */
@RestController
@Validated
public class EsGoodsInfoElasticQueryController implements EsGoodsInfoElasticQueryProvider {

    @Autowired
    private EsGoodsInfoElasticService esGoodsInfoElasticService;

    @Override
    public BaseResponse<EsGoodsInfoResponse> page(@RequestBody @Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.page(request));
    }

    @Override
    public BaseResponse<EsGoodsResponse> pageByGoods(@RequestBody @Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.pageByGoods(request));
    }

    @Override
    public BaseResponse<EsGoodsInfoResponse> distributorGoodsListByCustomerId(@RequestBody @Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.distributorGoodsListByCustomerId(request));
    }

    @Override
    public BaseResponse<EsGoodsInfoResponse> distributorGoodsList(@RequestBody @Valid EsDistributorGoodsListQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.distributorGoodsList(request.getRequest(),
                request.getGoodsIdList()));
    }

    @Override
    public BaseResponse<EsGoodsSelectOptionsResponse> selectOptions(@Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.selectOptions(request));
    }

    @Override
    public BaseResponse<EsGoodsSimpleResponse> spuPage(@Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.spuPage(request));
    }

    @Override
    public BaseResponse<EsGoodsInfoSimpleResponse> skuPage(@Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.skuPage(request));
    }

    @Override
    public BaseResponse<EsGoodsInfoSimpleResponse> skuPageByAllParams(@Valid EsGoodsInfoQueryRequest request) {
        return BaseResponse.success(esGoodsInfoElasticService.skuPageByAllParams(request));
    }

    @Override
    public BaseResponse<EsBaseInfoByParamsResponse> getEsBaseInfoByParams(@RequestBody @Valid EsGoodsInfoQueryRequest request) {
        EsSearchResponse esSearchResponse = esGoodsInfoElasticService.getEsBaseInfoByParams(request);
        List<EsGoodsInfoVO> esGoodsInfoVOList = Objects.isNull(esSearchResponse) ? new ArrayList<>() :
                esSearchResponse.getData();
        return BaseResponse.success(EsBaseInfoByParamsResponse.builder().esGoodsInfoVOList(esGoodsInfoVOList).build());
    }


}
