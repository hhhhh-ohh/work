package com.wanmi.sbc.elastic.provider.impl.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.*;
import com.wanmi.sbc.elastic.goods.service.ElasticGoodsElasticService;
import com.wanmi.sbc.elastic.goods.service.EsGoodsElasticServiceInterface;
import com.wanmi.sbc.elastic.goods.service.EsGoodsInfoElasticServiceInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 18:19 2020/11/30
 * @Description: TODO
 */
@RestController
@Validated
public class EsGoodsInfoElasticController implements EsGoodsInfoElasticProvider {

    @Autowired
    private EsGoodsInfoElasticServiceInterface esGoodsInfoElasticServiceInterface;

    @Autowired
    private EsGoodsElasticServiceInterface esGoodsElasticServiceInterface;

    @Autowired
    private ElasticGoodsElasticService elasticGoodsElasticService;

    @Override
    public BaseResponse delBrandIds(@RequestBody @Valid EsBrandDeleteByIdsRequest request) {
        esGoodsInfoElasticServiceInterface.delBrandIds(request.getDeleteIds(), request.getStoreId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initEsGoodsInfo(@RequestBody @Valid EsGoodsInfoRequest request) {
        if(CollectionUtils.isNotEmpty(request.getIdList())){
            request.setGoodsIds(request.getIdList());
            request.setPageNum(0);
            request.setPageSize(request.getIdList().size());
        }
        esGoodsInfoElasticServiceInterface.initEsGoodsInfo(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<Boolean> modifyDistributionGoodsAudit(@RequestBody @Valid EsGoodsInfoModifyDistributionGoodsAuditRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.modifyDistributionGoodsAudit(request));
    }

    @Override
    public BaseResponse<Boolean> modifyDistributionGoodsStatus(@RequestBody @Valid EsGoodsInfoModifyDistributionGoodsStatusRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.modifyDistributionGoodsStatus(request));
    }

    @Override
    public BaseResponse<Boolean> modifyDistributionCommission(@RequestBody @Valid EsGoodsInfoModifyDistributionCommissionRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.modifyDistributionCommission(request));
    }

    @Override
    public BaseResponse<Boolean> modifyDistributionGoodsAuditBySpuId(@RequestBody @Valid EsGoodsInfoModifyDistributionBySpuIdRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.modifyDistributionGoodsAudit(request.getSpuId()));
    }

    @Override
    public BaseResponse<Boolean> modifyEnterpriseAuditStatus(@RequestBody @Valid EsGoodsInfoEnterpriseAuditRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.modifyEnterpriseAuditStatus(request));
    }

    @Override
    public BaseResponse<Boolean> updateEnterpriseGoodsInfo(@RequestBody @Valid EsGoodsInfoEnterpriseBatchAuditRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.updateEnterpriseGoodsInfo(
                request.getBatchEnterPrisePriceDTOS(), request.getEnterpriseAuditState()));
    }

    @Override
    public BaseResponse updateAddedStatus(@RequestBody @Valid EsGoodsInfoModifyAddedStatusRequest request) {
        PluginType pluginType = Nutils.defaultVal(request.getPluginType(), PluginType.NORMAL);
        esGoodsInfoElasticServiceInterface.updateAddedStatus(request.getAddedFlag(), request.getGoodsIds(),
                request.getGoodsInfoIds(), pluginType);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByGoods(@RequestBody @Valid EsGoodsDeleteByIdsRequest request) {
        esGoodsInfoElasticServiceInterface.deleteByGoods(request.getDeleteIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initProviderEsGoodsInfo(@RequestBody @Valid EsGoodsInitProviderGoodsInfoRequest request) {
        esGoodsInfoElasticServiceInterface.initProviderEsGoodsInfo(request.getStoreId(), request.getProviderGoodsIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delete(@RequestBody @Valid EsGoodsDeleteByIdsRequest request) {
        esGoodsInfoElasticServiceInterface.delete(request.getDeleteIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<Long> updateSalesNumBySpuId(@RequestBody @Valid EsGoodsModifySalesNumBySpuIdRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.updateSalesNumBySpuId(request.getSpuId(),
                request.getSalesNum()));
    }

    @Override
    public BaseResponse<Long> updateSortNoBySpuId(@RequestBody @Valid EsGoodsModifySortNoBySpuIdRequest request) {
        return BaseResponse.success(esGoodsInfoElasticServiceInterface.updateSortNoBySpuId(request.getSpuId(),
                request.getSortNo()));
    }

    @Override
    public BaseResponse delStoreCateIds(@RequestBody @Valid EsGoodsDeleteStoreCateRequest request) {
        esGoodsInfoElasticServiceInterface.delStoreCateIds(request.getStoreCateIds(), request.getStoreId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse adjustPrice(@RequestBody @Valid EsGoodsInfoAdjustPriceRequest request) {
        esGoodsInfoElasticServiceInterface.adjustPrice(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyStore(@RequestBody @Valid EsGoodsStoreInfoModifyRequest request){
        esGoodsElasticServiceInterface.updateStoreStateByStoreId(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyGoodsProperty(@RequestBody @Valid EsGoodsPropertyModifyRequest request) {
        elasticGoodsElasticService.modifyGoodsProperty(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyPropIndexFlag(@RequestBody @Valid EsGoodsPropertyIndexRequest request) {
        elasticGoodsElasticService.modifyPropIndexFlag(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteGoodsProperty(@RequestBody @Valid EsGoodsPropertyByIdListRequest request) {
        elasticGoodsElasticService.deletePropIndexFlag(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyPropSort(@RequestBody @Valid EsGoodsPropCateSortRequest request) {
        elasticGoodsElasticService.modifyPropSort(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyFreightTemplateId(@Valid EsFreightTemplateRequest request) {
        elasticGoodsElasticService.modifyFreightTemplateId(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyStoreNameByStoreId(@Valid EsGoodsModifyStoreNameByStoreIdRequest request) {
        esGoodsElasticServiceInterface.updateStoreNameByStoreId(request);
        return BaseResponse.SUCCESSFUL();
    }
}
