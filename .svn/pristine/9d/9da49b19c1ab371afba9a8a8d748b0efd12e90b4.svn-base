package com.wanmi.sbc.goods.provider.impl.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsFreightTemplateRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsBaseInfoByParamsResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsProvider;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsCopyByIdAndStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsDeleteByIdAndStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsInitByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsSaveRequest;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.service.FreightTemplateGoodsServiceInterface;
import com.wanmi.sbc.goods.info.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>对单品运费模板操作接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@RestController
@Validated
public class FreightTemplateGoodsController implements FreightTemplateGoodsProvider {

    @Autowired
    private FreightTemplateGoodsServiceInterface freightTemplateGoodsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    /**
     * 新增/更新单品运费模板
     *
     * @param request 保存单品运费模板数据结构 {@link FreightTemplateGoodsSaveRequest}
     * @return {@link BaseResponse}
     */
    @Override

    public BaseResponse save(@RequestBody @Valid FreightTemplateGoodsSaveRequest request){
        freightTemplateGoodsService.renewalFreightTemplateGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据单品运费模板id和店铺id删除单品运费模板
     *
     * @param request 删除单品运费模板数据结构 {@link FreightTemplateGoodsDeleteByIdAndStoreIdRequest}
     * @return {@link BaseResponse}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteByIdAndStoreId(@RequestBody @Valid FreightTemplateGoodsDeleteByIdAndStoreIdRequest
                                                     request) {
        freightTemplateGoodsService.delById(request.getFreightTempId(), request.getStoreId(), request.getPluginType());
        //查询默认单品模板
        FreightTemplateGoods goodsFreight = freightTemplateGoodsService.queryByDefaultByStoreId(request.getStoreId());
        if (Objects.isNull(goodsFreight)) {
            return BaseResponse.SUCCESSFUL();
        }
        //更新商品ES
        esGoodsInfoElasticProvider.modifyFreightTemplateId(
                EsFreightTemplateRequest.builder().freightTemplateId(goodsFreight.getFreightTempId()).oldFreightTemplateId(request.getFreightTempId()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据单品运费模板id和店铺id复制单品运费模板
     *
     * @param request 复制单品运费模板数据结构 {@link FreightTemplateGoodsCopyByIdAndStoreIdRequest}
     * @return {@link BaseResponse}
     */
    @Override

    public BaseResponse copyByIdAndStoreId(@RequestBody @Valid FreightTemplateGoodsCopyByIdAndStoreIdRequest request){
        freightTemplateGoodsService.copyFreightTemplateGoods(request.getFreightTempId(), request.getStoreId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始单品运费模板
     *
     * @param request 初始单品运费模板数据结构 {@link FreightTemplateGoodsInitByStoreIdRequest}
     * @return {@link BaseResponse}
     */
    @Override

    public BaseResponse initByStoreId(@RequestBody @Valid FreightTemplateGoodsInitByStoreIdRequest request){
        freightTemplateGoodsService.initFreightTemplate(request.getStoreId());
        return BaseResponse.SUCCESSFUL();
    }

}
