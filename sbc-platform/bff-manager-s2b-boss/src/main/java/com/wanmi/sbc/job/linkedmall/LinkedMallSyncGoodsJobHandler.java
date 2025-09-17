package com.wanmi.sbc.job.linkedmall;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.provider.channel.goods.ChannelGoodsSyncProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncParameterRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsPageByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.ThirdPlatformGoodsDelRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsPageByConditionResponse;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreIdResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务Handler
 * 全量同步linkedmall商品
 *
 * @author dyt
 */
@Component
@Slf4j
public class LinkedMallSyncGoodsJobHandler {

    @Autowired private GoodsQueryProvider goodsQueryProvider;
    @Autowired private GoodsProvider goodsProvider;
    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;
    @Autowired private EsStandardProvider esStandardProvider;
    @Autowired private ChannelSyncGoodsProvider channelSyncGoodsProvider;
    @Autowired private StoreQueryProvider storeQueryProvider;
    @Autowired private StoreCateQueryProvider storeCateQueryProvider;
    @Autowired private ChannelGoodsSyncProvider channelGoodsSyncProvider;
    @Autowired private CommonUtil commonUtil;

    @Value("${linkedmall.goods.sync.pageSzie:5}")
    private Integer syncPageSize;
    @Value("${linkedmall.goods.sync.retryMax:5}")
    private Integer retryMax;

    @XxlJob(value = "LinkedMallSyncGoodsJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        if (flag) {
            int pageSize = StringUtils.isNotBlank(param) ? Integer.parseInt(param) : syncPageSize;
            XxlJobHelper.log("开始全量同步linkedmall商品");
            log.info("开始同步linkedmall商品");

            //linkedmall供应商店铺
            StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.LINKED_MALL)).getContext();
            //店铺默认分类
            StoreCateListByStoreIdResponse storeCateListResponse = storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeVO.getStoreId())).getContext();
            List<Long> storeCates = storeCateListResponse.getStoreCateResponseVOList().stream()
                    .filter(v -> DefaultFlag.YES == v.getIsDefault())
                    .map(StoreCateResponseVO::getStoreCateId)
                    .collect(Collectors.toList());

            //linkedMall商品SpuId列表
            List<String> linkedMallSpuList = new ArrayList<>();

            //查询一共有多少页
            ChannelGoodsSyncQueryRequest channelGoodsSyncQueryRequest =
                    ChannelGoodsSyncQueryRequest.builder()
                            .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                            .syncParameter(ChannelGoodsSyncParameterRequest.builder()
                                    .storeId(storeVO.getStoreId())
                                    .storeName(storeVO.getStoreName())
                                    .companyInfoId(storeVO.getCompanyInfoId())
                                    .companyName(storeVO.getCompanyInfo().getCompanyName())
                                    .storeCateIds(storeCates)
                                    .build())
                            .build();
            channelGoodsSyncQueryRequest.setPageNum(0);
            channelGoodsSyncQueryRequest.setPageSize(pageSize);
            MicroServicePage<ChannelGoodsSyncQueryResponse> page = channelGoodsSyncProvider.syncQueryPage(channelGoodsSyncQueryRequest).getContext();
            int totalPage = page.getTotalPages();
            long total = page.getTotal();
            int pageNum = 0;
            long successTotal = 0L;
            Map<Integer, Integer> retryMap = new HashMap<>();
            ChannelGoodsSyncBySpuVasRequest syncQueryRequest = ChannelGoodsSyncBySpuVasRequest.builder()
                    .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                    .storeId(storeVO.getStoreId())
                    .storeName(storeVO.getStoreName())
                    .companyInfoId(storeVO.getCompanyInfoId())
                    .companyName(storeVO.getCompanyInfo().getCompanyName())
                    .storeCateIds(storeCates)
                    .build();
            syncQueryRequest.setPageSize(pageSize);
            while (pageNum <= totalPage) {
                syncQueryRequest.setPageNum(pageNum);
                long start = System.currentTimeMillis();
                try {
                    List<String> thirdSpuIdList = channelSyncGoodsProvider.syncSpuList(syncQueryRequest).getContext();
                    log.info("linkedmall商品已同步第{}页，共{}页，同步成功spuId：{}，耗时：{}", pageNum, totalPage
                            , JSON.toJSONString(thirdSpuIdList), System.currentTimeMillis() - start);
                    XxlJobHelper.log("linkedmall商品已同步第{}页，共{}页，同步成功spuId：{}，耗时：{}"
                            , pageNum, totalPage
                            , JSON.toJSONString(thirdSpuIdList), System.currentTimeMillis() - start);
                    if (CollectionUtils.isNotEmpty(thirdSpuIdList)) {
                        successTotal = successTotal + thirdSpuIdList.stream().filter(s -> StringUtils.isNotBlank(s) && !s.contains(":")).count();
                        linkedMallSpuList.addAll(thirdSpuIdList.stream()
                                .map(s -> StringUtils.isNotBlank(s) && !s.contains(":error:")
                                        ? StringUtils.substringBefore(s, ":error:")
                                        : s)
                                .collect(Collectors.toList()));
                    }
                    pageNum++;
                } catch (Exception e) {
                    log.error("linkedmall商品同步报错第{}页，共{}页，耗时：{}", pageNum, totalPage, System.currentTimeMillis() - start, e);
                    XxlJobHelper.log("linkedmall商品同步报错第{}页，共{}页，耗时：{}，报错：{}"
                            , pageNum, totalPage, System.currentTimeMillis() - start, e.getMessage());
                    XxlJobHelper.log(e);
                    //异常重试次数按 retryMax 控制
                    if (retryMax > 0) {
                        Integer retryCount = retryMap.get(pageNum);
                        if (retryCount == null) {
                            retryMap.put(pageNum, 1);
                        } else if (retryCount <= retryMax) {
                            retryMap.put(pageNum, retryCount + 1);
                        } else {
                            log.error("linkedmall商品同步报错重试第{}页，共{}页，重试已达上限：{}", pageNum, totalPage, retryMax);
                            XxlJobHelper.log("linkedmall商品同步报错重试第{}页，共{}页，重试已达上限：{}"
                                    , pageNum, totalPage, retryMax);
                            pageNum++;
                        }
                    }
                }
            }
            log.info("完成全量同步linkedmall商品，共{}个，同步成功{}个", total, successTotal);
            XxlJobHelper.log("完成全量同步linkedmall商品，共{}个，同步成功{}个", total, successTotal);

            XxlJobHelper.log("开始删除已失效的linkedmall商品");
            log.info("开始删除已失效的linkedmall商品");
            GoodsPageByConditionRequest goodsByConditionRequest = new GoodsPageByConditionRequest();
            goodsByConditionRequest.setGoodsSource(GoodsSource.LINKED_MALL.toValue());
            goodsByConditionRequest.setDelFlag(DeleteFlag.NO.toValue());
            goodsByConditionRequest.setPageNum(0);
            goodsByConditionRequest.setPageSize(20);
            int deleteTotal = 0;
            boolean queryNext = true;
            while (queryNext) {
                try {
                    GoodsPageByConditionResponse pageByConditionResponse = goodsQueryProvider.pageByCondition(goodsByConditionRequest).getContext();
                    if (pageByConditionResponse != null && pageByConditionResponse.getGoodsPage() != null
                            && CollectionUtils.isNotEmpty(pageByConditionResponse.getGoodsPage().getContent())) {

                        List<ThirdPlatformGoodsDelDTO> thirdPlatformGoodsDelDTOList = pageByConditionResponse.getGoodsPage().getContent().stream()
                                .filter(goodsVO -> !linkedMallSpuList.contains(goodsVO.getThirdPlatformSpuId()))
                                .map(goodsVO -> ThirdPlatformGoodsDelDTO.builder()
                                        .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                                        .goodsSource(GoodsSource.LINKED_MALL)
                                        .itemId(Long.valueOf(goodsVO.getThirdPlatformSpuId()))
                                        .build())
                                .collect(Collectors.toList());
                        ThirdPlatformGoodsDelResponse response = goodsProvider.deleteThirdPlatformGoods(
                                ThirdPlatformGoodsDelRequest.builder().thirdPlatformGoodsDelDTOS(thirdPlatformGoodsDelDTOList).deleteAllSku(true).build()).getContext();
                        List<String> esGoodsInfoIds = response.getGoodsInfoIds();
                        if (esGoodsInfoIds.size() > 0) {
                            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(esGoodsInfoIds).build());
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
                        List<Long> spuIdDeleteList = thirdPlatformGoodsDelDTOList.stream().map(ThirdPlatformGoodsDelDTO::getItemId).collect(Collectors.toList());
                        log.info("删除已失效的linkedmall商品第{}页，删除成功spuId：{}", goodsByConditionRequest.getPageNum(), JSON.toJSONString(spuIdDeleteList));
                        XxlJobHelper.log("删除已失效的linkedmall商品第{}页，删除成功spuId：{}", goodsByConditionRequest.getPageNum(), JSON.toJSONString(spuIdDeleteList));

                        deleteTotal = deleteTotal + thirdPlatformGoodsDelDTOList.size();
                        goodsByConditionRequest.setPageNum(goodsByConditionRequest.getPageNum() + 1);
                    } else {
                        queryNext = false;
                    }
                } catch (Exception e) {
                    log.info("删除已失效的linkedmall商品第{}页，删除报错", goodsByConditionRequest.getPageNum(), e);
                    XxlJobHelper.log("删除已失效的linkedmall商品第{}页，删除报错", goodsByConditionRequest.getPageNum(), e.getMessage());
                    XxlJobHelper.log(e);
                    goodsByConditionRequest.setPageNum(goodsByConditionRequest.getPageNum() + 1);
                }
            }
            log.info("完成删除已失效的linkedmall商品，删除成功{}个", deleteTotal);
            XxlJobHelper.log("完成删除已失效的linkedmall商品，删除成功{}个", deleteTotal);
        }
    }
}
