package com.wanmi.sbc.freight.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsBaseInfoByParamsResponse;
import com.wanmi.sbc.freight.response.FreightPurchaseInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.freight.CollectPageInfoRequest;
import com.wanmi.sbc.goods.api.request.freight.GetFreightInGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.FreightGoodsInfoVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.order.api.request.purchase.PurchaseQueryRequest;
import com.wanmi.sbc.order.api.response.purchase.PurchaseQueryResponse;
import com.wanmi.sbc.order.bean.vo.PurchaseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className FreightBaseService
 * @description 运费处理
 * @date 2022/7/6 9:22
 **/
@Slf4j
@Service
public class FreightBaseService {


    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private FreightProvider freightProvider;

    @Autowired
    private PurchaseQueryProvider purchaseQueryProvider;

    @Autowired
    EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    /**
     * @description  处理商品详情 运费信息
     * @author  wur
     * @date: 2022/7/6 19:27
     * @param request
     * @return
     **/
    public BaseResponse<GetFreightInGoodsInfoResponse> inGoodsInfoFreight(GetFreightInGoodsInfoRequest request){
        //处理秒杀
        if (Objects.nonNull(request.getFlashSaleGoodsId()) && this.flashSalePostage(request.getFlashSaleGoodsId())) {
            return BaseResponse.success(GetFreightInGoodsInfoResponse.builder().freightDescribe("免运费").collectFlag(Boolean.FALSE).build());
        }
        return freightProvider.inGoodsInfo(request);
    }

    /**
     * @description   验证秒杀商品是否包邮
     * @author  wur
     * @date: 2022/7/6 19:28
     * @param flashSaleGoodsId
     * @return
     **/
    private boolean flashSalePostage(Long flashSaleGoodsId) {
        FlashSaleGoodsVO flashSaleGoodsVO =
                flashSaleGoodsQueryProvider.getById(FlashSaleGoodsByIdRequest.builder().id(flashSaleGoodsId).build())
                        .getContext()
                        .getFlashSaleGoodsVO();
        if (Objects.isNull(flashSaleGoodsVO)) {
            return  false;
        }
        return flashSaleGoodsVO.getPostage().equals(1);
    }

    /**
     * @description   查询凑单信息  goodsInfoIds 优先级高于 freightGoodsInfoVOList
     *               如果goodsInfoIds 有参数则从用户采购单中匹配目标商品
     *
     * @author  wur
     * @date: 2022/7/6 19:40
     * @return
     **/
    public BaseResponse<CollectPageInfoResponse> collectPageInfo(CollectPageInfoRequest request) {
        // 1. 获取目标商品信息
        List<String> skuIds = new ArrayList<>();
        List<FreightGoodsInfoVO> freightGoodsInfoVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            // 查询用户+storeId采购单信息
            PurchaseQueryResponse purchaseResponse = purchaseQueryProvider.query(PurchaseQueryRequest.builder().customerId(request.getCustomer().getCustomerId()).goodsInfoIds(request.getGoodsInfoIds()).isO2O(Boolean.FALSE).build()).getContext();
            if (Objects.isNull(purchaseResponse) || CollectionUtils.isEmpty(purchaseResponse.getPurchaseList())) {
                return BaseResponse.success(CollectPageInfoResponse.builder().build());
            }
            //根据店铺Id过滤
            List<PurchaseVO> purchaseList = purchaseResponse.getPurchaseList().stream().filter(purchaseVO -> purchaseVO.getStoreId().equals(request.getStoreId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(purchaseList)) {
                return BaseResponse.success(CollectPageInfoResponse.builder().build());
            }
            skuIds = purchaseList.stream().map(PurchaseVO :: getGoodsInfoId).collect(Collectors.toList());
            for (PurchaseVO purchaseVO : purchaseList) {
                freightGoodsInfoVOList.add(FreightGoodsInfoVO.builder().goodsInfoId(purchaseVO.getGoodsInfoId()).num(purchaseVO.getGoodsNum()).build());
            }
        } else {
            if (CollectionUtils.isNotEmpty(request.getFreightGoodsInfoVOList())) {
                skuIds = request.getFreightGoodsInfoVOList().stream().map(FreightGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                freightGoodsInfoVOList = request.getFreightGoodsInfoVOList();
            }
        }
        if (CollectionUtils.isEmpty(freightGoodsInfoVOList)) {
            return BaseResponse.success(CollectPageInfoResponse.builder().build());
        }

        // 2. 如果是单品运费则需要根据运费模板去匹配
        EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        goodsInfoQueryRequest.setGoodsInfoIds(skuIds);
        goodsInfoQueryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        goodsInfoQueryRequest.setPageSize(skuIds.size());
        if (DefaultFlag.YES.equals(request.getFreightTemplateType())) {
            goodsInfoQueryRequest.setFreightTemplateId(request.getFreightTemplateId());
        } else {
            //如果是供应商
            if (Objects.nonNull(request.getProviderStoreId())){
                goodsInfoQueryRequest.setProviderStoreId(request.getProviderStoreId());
            } else {
                //排除代销商品
                goodsInfoQueryRequest.setNotShowProvideFlag(Boolean.TRUE);
            }
        }

        EsBaseInfoByParamsResponse esResponse = esGoodsInfoElasticQueryProvider.getEsBaseInfoByParams(goodsInfoQueryRequest).getContext();
        if(Objects.isNull(esResponse) || CollectionUtils.isEmpty(esResponse.getEsGoodsInfoVOList())) {
            return BaseResponse.success(CollectPageInfoResponse.builder().build());
        }
        // 3. 过滤商品最终目标商品信息
        List<String> finalSkuIds = new ArrayList<>();
        esResponse.getEsGoodsInfoVOList().stream().forEach(esGoodsInfoVO -> {
            finalSkuIds.add(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId()); });
        freightGoodsInfoVOList = freightGoodsInfoVOList.stream().filter(goodsInfoVO ->finalSkuIds.contains(goodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(freightGoodsInfoVOList)) {
            return BaseResponse.success(CollectPageInfoResponse.builder().build());
        }

        //封装返回数据
        request.setFreightGoodsInfoVOList(freightGoodsInfoVOList);
        return freightProvider.collectPageInfo(request);
    }

    /**
     * @description   查询采购单中匹配指定运费模板的商品信息
     * @author  wur
     * @date: 2022/7/14 9:12
     * @param request
     * @return
     **/
    public BaseResponse<FreightPurchaseInfoResponse> purchaseInfo(CollectPageInfoRequest request) {
        //1. 查询采购单中商家的商品
        PurchaseQueryResponse purchaseResponse = purchaseQueryProvider.listByCustomerId(PurchaseQueryRequest.builder().customerId(request.getCustomer().getCustomerId()).isO2O(Boolean.FALSE).build()).getContext();
        if (Objects.isNull(purchaseResponse) || CollectionUtils.isEmpty(purchaseResponse.getPurchaseList())) {
            return BaseResponse.success(FreightPurchaseInfoResponse.builder().build());
        }

        //根据店铺Id过滤
        List<PurchaseVO> purchaseList = purchaseResponse.getPurchaseList().stream().filter(purchaseVO -> Objects.equals(request.getStoreId(),purchaseVO.getStoreId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(purchaseList)) {
            return BaseResponse.success(FreightPurchaseInfoResponse.builder().build());
        }

        List<String> skuIds = purchaseList.stream().map(PurchaseVO :: getGoodsInfoId).collect(Collectors.toList());

        //如果是单品运费则需要根据运费模板去匹配
        EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        goodsInfoQueryRequest.setGoodsInfoIds(skuIds);
        goodsInfoQueryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        goodsInfoQueryRequest.setPageSize(skuIds.size());
        if (DefaultFlag.YES.equals(request.getFreightTemplateType())) {
            goodsInfoQueryRequest.setFreightTemplateId(request.getFreightTemplateId());
        } else {
            //如果是供应商
            if (Objects.nonNull(request.getProviderStoreId())){
                goodsInfoQueryRequest.setProviderStoreId(request.getProviderStoreId());
            } else {
                //排除代销商品
                goodsInfoQueryRequest.setNotShowProvideFlag(Boolean.TRUE);
            }
        }

        EsBaseInfoByParamsResponse esResponse = esGoodsInfoElasticQueryProvider.getEsBaseInfoByParams(goodsInfoQueryRequest).getContext();
        if(Objects.isNull(esResponse) || CollectionUtils.isEmpty(esResponse.getEsGoodsInfoVOList())) {
            return BaseResponse.success(FreightPurchaseInfoResponse.builder().build());
        }
        List<String> finalSkuIds = new ArrayList<>();
        esResponse.getEsGoodsInfoVOList().stream().forEach(esGoodsInfoVO -> {
            finalSkuIds.add(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId()); });
        return BaseResponse.success(FreightPurchaseInfoResponse.builder().skuIdList(finalSkuIds).build());
    }
}