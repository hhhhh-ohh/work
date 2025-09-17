package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.PageUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelBindStateRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StorePageRequest;
import com.wanmi.sbc.customer.api.request.store.StoreQueryRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className GoodsLedgerService
 * @description
 * @date 2022/7/18 6:15 PM
 **/
@Service
public class GoodsLedgerService {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    /**
     * 将未建立绑定关系的代销商品下架
     */
    @Async
    public void changeAddedByLedgerBindState() {
        StorePageRequest storePageRequest = StorePageRequest.builder()
                .delFlag(DeleteFlag.NO).storeType(StoreType.SUPPLIER).build();
        storePageRequest.putSort("applyEnterTime", SortType.ASC.toValue());

        //查询店铺总数
        Long total = storeQueryProvider.count(KsBeanUtil.convert(storePageRequest, StoreQueryRequest.class)).getContext();
        long pageTotal = PageUtil.calPage(total, Constants.NUM_1000);

        storePageRequest.setPageSize(Constants.NUM_1000);
        for (int i = 0; i < pageTotal; i++) {
            storePageRequest.setPageNum(i);
            List<StoreVO> storeVOS = storeQueryProvider.page(storePageRequest).getContext().getStoreVOPage().getContent();
            storeVOS.forEach(storeVO -> {
                List<Long> providerIds = goodsRepository.queryProviderIds(storeVO.getStoreId());
                if (CollectionUtils.isNotEmpty(providerIds)) {
                    LedgerReceiverRelBindStateRequest bindStateRequest = LedgerReceiverRelBindStateRequest.builder()
                            .supplierId(storeVO.getCompanyInfoId()).receiverStoreIds(providerIds).build();
                    List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStores(bindStateRequest).getContext().getUnBindStores();
                    if (CollectionUtils.isNotEmpty(unBindStores)) {
                        GoodsQueryRequest goodsQueryRequest = GoodsQueryRequest.builder()
                                .delFlag(DeleteFlag.NO.toValue())
                                .storeId(storeVO.getStoreId())
                                .providerIds(unBindStores).build();
                        long goodsTotal = goodsService.countByCondition(goodsQueryRequest);
                        long goodsPageTotal = PageUtil.calPage(goodsTotal, Constants.NUM_1000);
                        goodsQueryRequest.setPageSize(Constants.NUM_1000);
                        for(int j = 0; j < goodsPageTotal; j++) {
                            goodsQueryRequest.setPageNum(j);
                            List<Goods> goods = goodsService.pageByCondition(goodsQueryRequest).getContent();
                            List<String> goodsIds  = goods.stream().map(Goods::getGoodsId).collect(Collectors.toList());
                            goodsService.updateAddedState(goodsIds);
                        }

                    }
                }
            });
        }
    }

    /**
     * 拉卡拉开启时，校验商户与供应商是否已绑定
     * @param addedFlag
     * @param addedTimingFlag
     * @param storeId
     * @param providerIds
     */
    public void checkLedgerBindState(Integer addedFlag, Boolean addedTimingFlag, Long storeId, List<Long> providerIds) {
        //拉卡拉开启时，未绑定分账关系的代销商品不支持上架
        if ((addedFlag != null && AddedFlag.YES.toValue() == addedFlag || Boolean.TRUE.equals(addedTimingFlag)) && CollectionUtils.isNotEmpty(providerIds)) {
            StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(storeId).build()).getContext().getStoreVO();
            if (CollectionUtils.isNotEmpty(providerIds)) {
                LedgerReceiverRelBindStateRequest bindStateRequest = LedgerReceiverRelBindStateRequest.builder()
                        .supplierId(storeVO.getCompanyInfoId()).receiverStoreIds(providerIds).build();
                List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStores(bindStateRequest)
                        .getContext().getUnBindStores();
                if (CollectionUtils.isNotEmpty(unBindStores)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030051);
                }
            }
        }
    }
}
