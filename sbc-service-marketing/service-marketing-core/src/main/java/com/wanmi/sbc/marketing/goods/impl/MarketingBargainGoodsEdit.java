package com.wanmi.sbc.marketing.goods.impl;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdsRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateGoodsStatusRequest;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bargaingoods.service.BargainGoodsService;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.marketing.goods.MarketingGoodsEdit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description
 * @author wur
 * @date: 2022/8/29 10:57
 */
@Slf4j
@Service
public class MarketingBargainGoodsEdit implements MarketingGoodsEdit {

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private BargainGoodsService bargainGoodsService;

    @Override
    public Boolean goodsEdit(GoodsEditSynRequest request) {
        List<String> goodsInfoIds = request.getGoodsInfoIds();
        DeleteFlag goodsStatus = DeleteFlag.NO;
        if (Objects.equals(GoodsEditFlag.UP, request.getFlag())) {
            goodsStatus = DeleteFlag.YES;
        }
        //处理商品基本信息变更
        if (Objects.equals(GoodsEditFlag.INFO, request.getFlag())) {
            // 查询目标商品
            List<GoodsInfoVO> goodsInfos =
                    goodsInfoQueryProvider
                            .getOriginalByIds(
                                    GoodsInfoByIdsRequest.builder()
                                            .goodsIds(request.getGoodsIds())
                                            .goodsInfoIds(request.getGoodsInfoIds())
                                            .isProvide(request.getIsProvider())
                                            .build())
                            .getContext()
                            .getGoodsInfos();
            if (CollectionUtils.isEmpty(goodsInfos)) {
                return Boolean.FALSE;
            }
            bargainGoodsService.updateGoodsInfo(goodsInfos);
            return Boolean.TRUE;
        }
        // 查询目标商品
        if ((Objects.nonNull(request.getIsProvider()) && request.getIsProvider())
                || CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            // 查询目标商品
            List<GoodsInfoVO> goodsInfos =
                    goodsInfoQueryProvider
                            .getOriginalByIds(
                                    GoodsInfoByIdsRequest.builder()
                                            .goodsIds(request.getGoodsIds())
                                            .goodsInfoIds(request.getGoodsInfoIds())
                                            .isProvide(request.getIsProvider())
                                            .build())
                            .getContext()
                            .getGoodsInfos();
            if (CollectionUtils.isEmpty(goodsInfos)) {
                return Boolean.FALSE;
            }
            // 如果是上传操作则需要过滤不可售商品
            if (Objects.equals(GoodsEditFlag.UP, request.getFlag())) {
                List<GoodsInfoVO> filterGoodsInfos =
                        goodsInfos.stream()
                                .filter(
                                        goodsInfoVO ->
                                                Objects.equals(
                                                                DeleteFlag.NO,
                                                                goodsInfoVO.getDelFlag())
                                                        && Objects.equals(
                                                                CheckStatus.CHECKED,
                                                                goodsInfoVO.getAuditStatus())
                                                        && Objects.equals(
                                                                AddedFlag.YES.toValue(),
                                                                goodsInfoVO.getAddedFlag())
                                                        && (StringUtils.isEmpty(
                                                                                goodsInfoVO
                                                                                        .getProviderGoodsInfoId())
                                                                        || request.getIsProvider()
                                                                ? true
                                                                : Objects.equals(
                                                                        Constants.yes,
                                                                        goodsInfoVO.getVendibility(
                                                                                true))))
                                .collect(Collectors.toList());
                if (CollectionUtils.isEmpty(filterGoodsInfos)) {
                    return Boolean.FALSE;
                }
                goodsInfoIds =
                        filterGoodsInfos.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            } else {
                goodsInfoIds =
                        goodsInfos.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            }
        }
        bargainGoodsService.updateGoodsStatus(
                UpdateGoodsStatusRequest.builder()
                        .goodsInfoIds(goodsInfoIds)
                        .goodsStatus(goodsStatus)
                        .build());
        return Boolean.TRUE;
    }
}
