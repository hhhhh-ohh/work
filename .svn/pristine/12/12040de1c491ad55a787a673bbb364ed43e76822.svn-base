package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.ESStoreInfoInitRequest;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoModifyByStoreIdAndCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoPageByCustomerIdRequest;
import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className LakalaEditStoreStateService
 * @description 拉卡拉开店或者关店
 * @date 2021/8/16 10:49 上午
 */
@Slf4j
@Service
public class LakalaEditStoreStateService{

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Bean
    public Consumer<Message<String>> mqLakalaEditStoreStateService() {
        return this::extracted;
    }

    /**
     * 消费数据
     *
     * @param message
     */
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        StoreState storeState;
        if (IsOpen.YES.equals(IsOpen.valueOf(json))) {
            storeProvider.closeStoreForLaKaLa();
            storeState = StoreState.CLOSED;
            //拉卡拉开关打开后，分销员商品判断是否与商家绑定分账关系，如果没有绑定，分销员商品改为审核中
            executeChangeStatusForDistributorGoodsInfo();
        } else {
            storeProvider.openStoreForLaKaLa();
            storeState = StoreState.OPENING;
            distributorGoodsInfoProvider.updateStatusForLaKaLa(new DistributorGoodsInfoModifyByStoreIdAndCustomerIdRequest());
        }
        esStoreInformationProvider.initStoreInformationList(new ESStoreInfoInitRequest());
        //找出正在开店或者关店的店铺
        List<StoreVO> storeVOList = storeQueryProvider.listStore(ListStoreRequest.builder()
                .storeState(storeState)
                .delFlag(DeleteFlag.NO)
                .build()).getContext().getStoreVOList();
        changeStoreStateForGoodsInfo(storeVOList);
        //代销商品下架
        if (IsOpen.YES.equals(IsOpen.valueOf(json))) {
            goodsProvider.changeAddedFlagByLedgerBindState();
        }
    }


    private void changeStoreStateForGoodsInfo(List<StoreVO> storeVOList) {
        // 2.1 创建线程池，暂时是每50个店铺分配一个线程
        ExecutorService executor = Executors.newFixedThreadPool(50);
        // 2.2 遍历店铺列表
        storeVOList.forEach(storeVO ->
            executor.submit(() ->
                esGoodsInfoElasticProvider.modifyStore(EsGoodsStoreInfoModifyRequest.builder()
                        .storeId(storeVO.getStoreId()).storeState(storeVO.getStoreState()).build())
            )
        );
        executor.shutdown();
        //等待线程池结束
        try {
            if (!executor.awaitTermination(Constants.NUM_24, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void executeChangeStatusForDistributorGoodsInfo() {
        // 2.1 创建线程池，暂时是每50个店铺分配一个线程
        ExecutorService executor = Executors.newFixedThreadPool(50);
        changeStatusForDistributorGoodsInfo(Constants.ZERO,executor);
        executor.shutdown();
        //等待线程池结束
        try {
            if (!executor.awaitTermination(Constants.NUM_24, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void changeStatusForDistributorGoodsInfo(Integer pageNum, ExecutorService executor) {
        DistributorGoodsInfoPageByCustomerIdRequest request = new DistributorGoodsInfoPageByCustomerIdRequest();
        request.setPageNum(pageNum);
        request.setPageSize(Constants.NUM_1000);
        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList = distributorGoodsInfoQueryProvider.findGroupByCustomerAndStoreId(request)
                .getContext().getDistributorGoodsInfoVOList();
        if (CollectionUtils.isNotEmpty(distributorGoodsInfoVOList)) {
            distributorGoodsInfoVOList.forEach(distributorGoodsInfoVO -> {
                executor.submit(() -> {
                    // 获取商户id
                    Long companyInfoId = storeQueryProvider.getById(StoreByIdRequest.builder()
                            .storeId(distributorGoodsInfoVO.getStoreId())
                            .build()).getContext().getStoreVO().getCompanyInfoId();
                    //根据商户id跟接收方id查询绑定关系
                    List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                            .supplierId(companyInfoId)
                            .receiverId(distributorGoodsInfoVO.getCustomerId())
                            .build()).getContext().getLedgerReceiverRelVOList();
                    //如果不存在或者不是已经绑定的状态，需要刷新分销员商品的状态为审核中
                    if (CollectionUtils.isEmpty(ledgerReceiverRelVOList) || ledgerReceiverRelVOList.get(0).getBindState() != LedgerBindState.BINDING.toValue()) {
                        distributorGoodsInfoProvider
                                .updateStatusForLaKaLa(new DistributorGoodsInfoModifyByStoreIdAndCustomerIdRequest(distributorGoodsInfoVO.getStoreId(),distributorGoodsInfoVO.getCustomerId()));
                    }
                });
            });
            if (distributorGoodsInfoVOList.size() == Constants.NUM_1000) {
                changeStatusForDistributorGoodsInfo(pageNum + 1,executor);
            }
        }
    }
}
