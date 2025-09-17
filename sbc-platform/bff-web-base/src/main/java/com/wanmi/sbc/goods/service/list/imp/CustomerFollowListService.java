package com.wanmi.sbc.goods.service.list.imp;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.order.api.provider.follow.FollowQueryProvider;
import com.wanmi.sbc.order.api.request.follow.FollowListRequest;
import com.wanmi.sbc.order.api.response.follow.GoodsCustomerFollowPageResponse;
import com.wanmi.sbc.order.bean.vo.GoodsCustomerFollowVO;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className CustomerFollowListService
 * @description TODO
 * @date 2021/8/11 5:14 下午
 */
@Service
public class CustomerFollowListService extends GoodsInfoListService {

    @Autowired CommonUtil commonUtil;

    @Autowired private FollowQueryProvider followQueryProvider;

    @Autowired EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;


    @Override
    public EsGoodsInfoQueryRequest setRequest(EsGoodsInfoQueryRequest request) {
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            request.setIsBuyCycle(Constants.no);
        }
        threadRrequest.set(request);
        return request;
    }

    @Override
    public EsGoodsInfoSimpleResponse getEsDataPage(EsGoodsInfoQueryRequest request) {
        String customerId = commonUtil.getOperatorId();
        EsGoodsInfoSimpleResponse response = new EsGoodsInfoSimpleResponse();
        FollowListRequest followListRequest =
                FollowListRequest.builder().customerId(customerId).build();
        // 按创建时间倒序
        followListRequest.putSort("followTime", SortType.DESC.toValue());
        followListRequest.putSort("followId", SortType.DESC.toValue());
        followListRequest.setPageNum(request.getPageNum());
        followListRequest.setPageSize(request.getPageSize());
        followListRequest.setIsBuyCycle(request.getIsBuyCycle());
        GoodsCustomerFollowPageResponse followPageResponse =
                followQueryProvider.page(followListRequest).getContext();
        Long total = followPageResponse.getGoodsCustomerFollowVOS().getTotal();
        Integer pageSize = request.getPageSize(),pageNum=request.getPageNum();
        List<String> goodsInfoIdList = new ArrayList<>();
        if (followPageResponse != null && followPageResponse.getGoodsCustomerFollowVOS() != null) {
            request.setGoodsInfoIds(
                    followPageResponse.getGoodsCustomerFollowVOS().getContent().stream()
                            .map(GoodsCustomerFollowVO::getGoodsInfoId)
                            .collect(Collectors.toList()));
            goodsInfoIdList = followPageResponse.getGoodsCustomerFollowVOS().getContent().stream()
                    .map(GoodsCustomerFollowVO::getGoodsInfoId)
                    .collect(Collectors.toList());
            // 将页面重新设置为0；
            request.setPageNum(0);
        }
        if (request != null && CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {

            response = esGoodsInfoElasticQueryProvider.skuPageByAllParams(request).getContext();
            List<String> finalGoodsInfoIdList = new ArrayList<>();
            //按照加入时间降序
            List<EsGoodsInfoVO> esGoodsInfoVOList = new ArrayList<>();
            for (int size = 0 ; size < followPageResponse.getGoodsCustomerFollowVOS().getContent().size() ; size ++) {
                int finalSize = size;
                response.getEsGoodsInfoPage().getContent().forEach(esGoodsInfoVO -> {
                    if (followPageResponse.getGoodsCustomerFollowVOS().getContent().get(finalSize).getGoodsInfoId().equals(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId())) {
                        finalGoodsInfoIdList.add(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId());
                        esGoodsInfoVO.setSortNo(Long.valueOf(finalSize));
                        esGoodsInfoVOList.add(esGoodsInfoVO);
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(finalGoodsInfoIdList)) {
                goodsInfoIdList.removeAll(finalGoodsInfoIdList);
            }
            //如果ES没查出数据则 调用goods服务查询
            if (CollectionUtils.isNotEmpty(goodsInfoIdList)) {
                GoodsInfoViewByIdsResponse goodsInfoView = goodsInfoQueryProvider.listViewByIds(GoodsInfoViewByIdsRequest.builder().goodsInfoIds(goodsInfoIdList).build()).getContext();
                List<GoodsInfoVO> goodsInfoVOList = goodsInfoView.getGoodsInfos();
                if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
                    Map<String, GoodsVO> goodsMap = goodsInfoView.getGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, goods -> goods));
                    List<Long> storeIdList = goodsInfoVOList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList());
                    List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIdList).build()).getContext().getStoreVOList();
                    Map<Long, StoreVO> storeVOMap = CollectionUtils.isNotEmpty(storeVOList) ? storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, store -> store)) : new HashMap<>();
                    for (int size = 0 ; size < followPageResponse.getGoodsCustomerFollowVOS().getContent().size() ; size ++) {
                        int finalSize = size;
                        goodsInfoVOList.forEach(goodsInfoVO -> {
                            if (followPageResponse.getGoodsCustomerFollowVOS().getContent().get(finalSize).getGoodsInfoId().equals(goodsInfoVO.getGoodsInfoId())) {
                                GoodsInfoNestVO goodsInfoNestVO = KsBeanUtil.copyPropertiesThird(goodsInfoVO, GoodsInfoNestVO.class);
                                if (goodsMap.containsKey(goodsInfoVO.getGoodsId())) {
                                    goodsInfoNestVO.setGoodsInfoImg(goodsMap.get(goodsInfoVO.getGoodsId()).getGoodsImg());
                                }
                                if (storeVOMap.containsKey(goodsInfoVO.getStoreId())) {
                                    goodsInfoNestVO.setStoreName(storeVOMap.get(goodsInfoVO.getStoreId()).getStoreName());
                                }
                                EsGoodsInfoVO esGoodsInfoVO = new EsGoodsInfoVO();
                                esGoodsInfoVO.setId(goodsInfoVO.getGoodsInfoId());
                                esGoodsInfoVO.setGoodsId(goodsInfoVO.getGoodsId());
                                esGoodsInfoVO.setSortNo(Long.valueOf(finalSize));
                                esGoodsInfoVO.setGoodsInfo(goodsInfoNestVO);
                                esGoodsInfoVO.setGoodsSubtitle(goodsInfoVO.getGoodsSubtitle());

                                esGoodsInfoVOList.add(esGoodsInfoVO);
                            }
                        });
                    }
                }
            }
            esGoodsInfoVOList.sort(Comparator.comparing(EsGoodsInfoVO :: getSortNo));
            // 设置分页信息为GoodsCustomerFollow的信息
            response.getEsGoodsInfoPage().setContent(esGoodsInfoVOList);
            response.getEsGoodsInfoPage().setTotal(total);
            response.getEsGoodsInfoPage().setNumber(pageNum);
            response.getEsGoodsInfoPage().setSize(pageSize);
        }
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
