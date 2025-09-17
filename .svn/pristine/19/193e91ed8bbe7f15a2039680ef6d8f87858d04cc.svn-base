package com.wanmi.sbc.goods.service.list.imp;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkQueryProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 足迹列表查询服务
 * @author malianfeng
 * @date 2022/8/25 11:12
 */
@Slf4j
@Service
public class CustomerGoodsFootmarkListService extends GoodsInfoListService {

    @Autowired private GoodsFootmarkQueryProvider goodsFootmarkQueryProvider;

    @Override
    public EsGoodsInfoQueryRequest setRequest(EsGoodsInfoQueryRequest request) {
        threadRrequest.set(request);
        return request;
    }

    @Override
    public EsGoodsInfoSimpleResponse getEsDataPage(EsGoodsInfoQueryRequest request) {

        String customerId = commonUtil.getOperatorId();
        EsGoodsInfoSimpleResponse response = new EsGoodsInfoSimpleResponse();


        // 1. 查询足迹记录（最近90天，updateTime升序）
        GoodsFootmarkQueryRequest goodsFootmarkQueryRequest = new GoodsFootmarkQueryRequest();
        goodsFootmarkQueryRequest.putSort("updateTime", "desc");
        goodsFootmarkQueryRequest.setUpdateTimeBegin(LocalDateTime.now().minusDays(90));
        goodsFootmarkQueryRequest.setUpdateTimeEnd(LocalDateTime.now());
        goodsFootmarkQueryRequest.setCustomerId(customerId);
        goodsFootmarkQueryRequest.setDelFlag(DeleteFlag.NO);
        goodsFootmarkQueryRequest.setPageNum(request.getPageNum());
        goodsFootmarkQueryRequest.setPageSize(request.getPageSize());
        MicroServicePage<GoodsFootmarkVO> goodsFootmarkPage = goodsFootmarkQueryProvider.page(goodsFootmarkQueryRequest).getContext();


        // 2. 构造ES商品查询条件
        List<String> goodsInfoIdList = new ArrayList<>();
        // 足迹商品升序排序号map，[skuId] => [sortNo]
        Map<String, Long> goodsFootmarkSortNoMap = new HashMap<>(request.getPageSize());
        // 足迹商品map，[skuId] => [GoodsFootmarkVO]
        Map<String, GoodsFootmarkVO> goodsFootmarkMap = new HashMap<>(request.getPageSize());
        List<EsGoodsInfoVO> esGoodsInfoVOList = new ArrayList<>();
        if (Objects.nonNull(goodsFootmarkPage) && CollectionUtils.isNotEmpty(goodsFootmarkPage.getContent())) {
            for (int i = 0; i < goodsFootmarkPage.getContent().size(); i++) {
                GoodsFootmarkVO goodsFootmarkVO = goodsFootmarkPage.getContent().get(i);
                // 收集skuIds，用于查询
                goodsInfoIdList.add(goodsFootmarkVO.getGoodsInfoId());
                // 构造足迹商品map，便于填充扩展字段
                goodsFootmarkMap.put(goodsFootmarkVO.getGoodsInfoId(), goodsFootmarkVO);
                // 构造足迹商品排序号map，便于排序
                goodsFootmarkSortNoMap.put(goodsFootmarkVO.getGoodsInfoId(), (long) i);
            }
            // 填充商品查询条件
            request.setPageNum(0);
            request.setGoodsInfoIds(goodsInfoIdList);


            // 3. 查询ES商品
            response = esGoodsInfoElasticQueryProvider.skuPageByAllParams(request).getContext();
            esGoodsInfoVOList = Optional.ofNullable(response.getEsGoodsInfoPage())
                    .map(MicroServicePage::getContent)
                    .map(ArrayList::new)
                    .orElse(new ArrayList<>());

            // 3.1 es商品数量小于足迹数量，说明部分足迹删除已被删除，需要从数据库把已删除的再查一遍
            if (esGoodsInfoVOList.size() < goodsInfoIdList.size()) {
                // 找出被删除的 skuIds
                Set<String> existEsSkuIds = esGoodsInfoVOList.stream().map(EsGoodsInfoVO::getId).collect(Collectors.toSet());
                List<String> deletedSkuIds = goodsInfoIdList.stream().filter(item -> !existEsSkuIds.contains(item)).collect(Collectors.toList());

                List<GoodsInfoVO> deletedGoodsInfoList = Collections.emptyList();
                try {
                    GoodsInfoViewByIdsResponse goodsInfoViewByIdsResponse = goodsInfoQueryProvider.listViewByIds(GoodsInfoViewByIdsRequest.builder()
                            .goodsInfoIds(deletedSkuIds).build()).getContext();
                    deletedGoodsInfoList = Optional.ofNullable(goodsInfoViewByIdsResponse.getGoodsInfos()).orElse(new ArrayList<>());
                } catch (SbcRuntimeException e) {
                    // listViewByIds方法有概率会抛出异常，导致整个列表返回失败，所以这里捕获一下
                    log.info("CustomerGoodsFootmark search deletedSkuIds:{}, error:{}", deletedSkuIds, e.getMessage());
                }
                for (GoodsInfoVO goodsInfoVO : deletedGoodsInfoList) {
                    if (Objects.nonNull(goodsInfoVO)) {
                        // 构造并手动添加至查询列表
                        GoodsInfoNestVO goodsInfoNestVO = KsBeanUtil.copyPropertiesThird(goodsInfoVO, GoodsInfoNestVO.class);
                        EsGoodsInfoVO esGoodsInfoVO = new EsGoodsInfoVO();
                        esGoodsInfoVO.setId(goodsInfoVO.getGoodsInfoId());
                        esGoodsInfoVO.setGoodsId(goodsInfoVO.getGoodsId());
                        esGoodsInfoVO.setGoodsInfo(goodsInfoNestVO);
                        esGoodsInfoVO.setGoodsSubtitle(goodsInfoVO.getGoodsSubtitle());
                        esGoodsInfoVOList.add(esGoodsInfoVO);
                    }
                }
            }

            // 3.2 为商品信息填充足迹扩展字段
            esGoodsInfoVOList.forEach(item -> {
                GoodsFootmarkVO goodsFootmarkVO = goodsFootmarkMap.get(item.getId());
                if (Objects.nonNull(goodsFootmarkVO)) {
                    item.setSortNo(goodsFootmarkSortNoMap.get(item.getId()));
                    if (Objects.nonNull(item.getGoodsInfo())) {
                        HashMap<String, String> extendMap = new HashMap<>();
                        extendMap.put("goodsFootmarkVO", JSON.toJSONString(goodsFootmarkVO));
                        item.getGoodsInfo().setExtendMap(extendMap);
                    }
                }
            });

            // 3.3 填充足迹的排序号，对商品重新排序
            esGoodsInfoVOList.forEach(item -> item.setSortNo(goodsFootmarkSortNoMap.get(item.getId())));
            esGoodsInfoVOList = esGoodsInfoVOList.stream().sorted(Comparator.comparing(EsGoodsInfoVO :: getSortNo)).collect(Collectors.toList());
        }

        // 4. 设置分页信息为 goodsFootmarkPage 的信息
        response.getEsGoodsInfoPage().setContent(esGoodsInfoVOList);
        response.getEsGoodsInfoPage().setTotal(Objects.nonNull(goodsFootmarkPage) ? goodsFootmarkPage.getTotal() : 0L);
        response.getEsGoodsInfoPage().setNumber(request.getPageNum());
        response.getEsGoodsInfoPage().setSize(request.getPageSize());

        return response;
    }

    @Override
    public EsGoodsInfoSimpleResponse filter(EsGoodsInfoSimpleResponse esGoodsInfoSimpleResponse) {
        esGoodsInfoSimpleResponse
                .getEsGoodsInfoPage()
                .forEach(
                        esGoodsInfoVO -> {
                            GoodsInfoNestVO goodsInfo = esGoodsInfoVO.getGoodsInfo();
                            if (!CheckStatus.CHECKED.equals(goodsInfo.getAuditStatus())
                                    || AddedFlag.NO.equals(goodsInfo.getAddedFlag())
                                    || DeleteFlag.YES.equals(goodsInfo.getDelFlag())
                                    || StoreState.CLOSED.equals(esGoodsInfoVO.getStoreState())) {
                                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                            }
                            if (goodsInfo.getVendibilityStatus() != null
                                    && goodsInfo.getVendibilityStatus() == 0) {
                                goodsInfo.setGoodsStatus(GoodsStatus.NO_SALE);
                            }
                        });
        return super.filter(esGoodsInfoSimpleResponse);
    }
}
