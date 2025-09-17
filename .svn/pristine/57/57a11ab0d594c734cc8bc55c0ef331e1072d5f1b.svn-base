package com.wanmi.sbc.thirdplatform.linkedmall.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.signature.LinkedMallSignatureProvider;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallSignatureVerifyRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.ThirdPlatformGoodsDelRequest;
import com.wanmi.sbc.goods.api.request.linkedmall.SyncItemRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreIdResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.linkedmall.signature.ModificationItemRequest;
import com.wanmi.sbc.vas.api.request.linkedmall.signature.SyncItemDeletionRequest;
import com.wanmi.sbc.vas.bean.enums.VasErrorCodeEnum;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * linkedmall回调接口
 */
@Tag(name =  "linkedmall回调接口", description =  "BossLinkedMallController")
@RestController
@Validated
@RequestMapping("/linkedmall")
@Slf4j
public class BossLinkedMallController {

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private LinkedMallSignatureProvider signatureProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;
    @Autowired
    private StoreQueryProvider storeQueryProvider;
    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    /**
     * 添加商品后信息推送接口
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GlobalTransactional
    @Operation(summary = "添加商品后信息推送接口")
    @PostMapping("/syncItemIncrement")
    public BaseResponse syncItemIncrement(@RequestBody SyncItemRequest request) throws IOException {
        boolean verify = signatureProvider.verifySignature(new LinkedMallSignatureVerifyRequest(originalString(request), request.getSignature())).getContext();
        if (!verify) {
            throw new SbcRuntimeException(VasErrorCodeEnum.K120002);
        }

        List<Long> itemIdList = request.getItemListEntity().stream().map(SyncItemRequest.LinkedMallItem::getItemId).collect(Collectors.toList());

        //linkedmall供应商店铺
        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.LINKED_MALL)).getContext();
        //店铺默认分类
        StoreCateListByStoreIdResponse storeCateListResponse = storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeVO.getStoreId())).getContext();
        List<Long> storeCates = storeCateListResponse.getStoreCateResponseVOList().stream()
                .filter(v -> DefaultFlag.YES == v.getIsDefault())
                .map(StoreCateResponseVO::getStoreCateId)
                .collect(Collectors.toList());
        ChannelGoodsSyncBySpuVasRequest channelGoodsSyncBySpuVasRequest = ChannelGoodsSyncBySpuVasRequest.builder()
                .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                .spuIds(itemIdList)
                .storeId(storeVO.getStoreId())
                .storeName(storeVO.getStoreName())
                .companyInfoId(storeVO.getCompanyInfoId())
                .companyName(storeVO.getCompanyInfo().getCompanyName())
                .storeCateIds(storeCates)
                .build();
        channelSyncGoodsProvider.syncSpuList(channelGoodsSyncBySpuVasRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * linkedmall修改商品信息后，同步商品修改信息到客户侧
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GlobalTransactional
    @Operation(summary = "linkedmall修改商品信息后，同步商品修改信息")
    @PostMapping("/syncItemModification")
    public BaseResponse syncItemModification(@RequestBody ModificationItemRequest request) throws IOException {
        boolean verify = signatureProvider.verifySignature(new LinkedMallSignatureVerifyRequest(originalString(request), request.getSignature())).getContext();
        if (!verify) {
            throw new SbcRuntimeException(VasErrorCodeEnum.K120002);
        }
        List<Long> itemIdList = request.getItemListEntity().stream().map(ModificationItemRequest.LinkedMallItemModification::getItemId).collect(Collectors.toList());

        //linkedmall供应商店铺
        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.LINKED_MALL)).getContext();
        //店铺默认分类
        StoreCateListByStoreIdResponse storeCateListResponse = storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeVO.getStoreId())).getContext();
        List<Long> storeCates = storeCateListResponse.getStoreCateResponseVOList().stream()
                .filter(v -> DefaultFlag.YES == v.getIsDefault())
                .map(StoreCateResponseVO::getStoreCateId)
                .collect(Collectors.toList());
        ChannelGoodsSyncBySpuVasRequest channelGoodsSyncBySpuVasRequest = ChannelGoodsSyncBySpuVasRequest.builder()
                .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                .spuIds(itemIdList)
                .storeId(storeVO.getStoreId())
                .storeName(storeVO.getStoreName())
                .companyInfoId(storeVO.getCompanyInfoId())
                .companyName(storeVO.getCompanyInfo().getCompanyName())
                .storeCateIds(storeCates)
                .build();
        channelSyncGoodsProvider.syncSpuList(channelGoodsSyncBySpuVasRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除商品后信息推送接口
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GlobalTransactional
    @Operation(summary = "删除商品后信息推送接口")
    @PostMapping("/syncItemDeletion")
    public BaseResponse syncItemDeletion(@RequestBody SyncItemDeletionRequest request) throws IOException {
        boolean verify = signatureProvider.verifySignature(new LinkedMallSignatureVerifyRequest(originalString(request), request.getSignature())).getContext();
        if (!verify) {
            throw new SbcRuntimeException(VasErrorCodeEnum.K120002);
        }
        List<SyncItemDeletionRequest.LinkedMallDeletionItem> itemIdListEntity = request.getItemIdListEntity();
        ThirdPlatformGoodsDelResponse response = goodsProvider.deleteThirdPlatformGoods(ThirdPlatformGoodsDelRequest.builder()
                .thirdPlatformGoodsDelDTOS(KsBeanUtil.copyListProperties(itemIdListEntity, ThirdPlatformGoodsDelDTO.class))
                .deleteAllSku(false)
                .build()).getContext();
        List<String> esGoodsInfoIds = response.getGoodsInfoIds();
        if (esGoodsInfoIds.size() > 0) {
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(esGoodsInfoIds).build());
        }
        //初始化商品库ES
        if (CollectionUtils.isNotEmpty(response.getStandardIds())) {
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(response.getStandardIds()).build());
        }
        //删除商品库ES
        if (CollectionUtils.isNotEmpty(response.getDelStandardIds())) {
            esStandardProvider.deleteByIds(EsStandardDeleteByIdsRequest.builder().goodsIds(response.getDelStandardIds()).build());
        }
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 拼装linkedmall签名时用的字符串
     *
     * @param object
     * @return
     */
    private String originalString(Object object) {
        Class<?> aClass = object.getClass();
        Map<String, Object> data = new HashMap<>();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Object value = null;
            ReflectionUtils.makeAccessible(declaredField);
//            以下不参与验签
            if (!"signature".equals(name) && !"signatureMethod".equals(name) && !"serialVersionUID".equals(name)) {
                try {
                    value = declaredField.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (value != null) {
                data.put(name, value);
            }
        }
        StringBuilder dataToBeSigned = new StringBuilder();
        List arrayList = new ArrayList(data.entrySet());
        Collections.sort(arrayList, new Comparator() {
            public int compare(Object arg1, Object arg2) {
                Map.Entry obj1 = (Map.Entry) arg1;
                Map.Entry obj2 = (Map.Entry) arg2;
                return (obj1.getKey()).toString().compareTo(obj2.getKey().toString());
            }
        });
        for (Iterator iter = arrayList.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            dataToBeSigned.append("".equals(dataToBeSigned.toString()) ? "" : '&')
                    .append(entry.getKey()).append('=').append(entry.getValue());
        }
        return dataToBeSigned.toString();
    }

}
