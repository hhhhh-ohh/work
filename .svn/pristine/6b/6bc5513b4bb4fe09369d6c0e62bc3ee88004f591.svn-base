package com.wanmi.sbc.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.giftcard.response.GiftCardGoodsPageResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardGoodsPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardInfoRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className GiftCardController
 * @description 礼品卡适用商品列表查询
 * @date 2022/12/22 5:57 下午
 **/
@Slf4j
@Tag(name =  "礼品卡适用商品业务API", description =  "GiftCardController")
@RestController
@Validated
@RequestMapping(value = "/giftCard")
public class GiftCardController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private GiftCardQueryProvider giftCardQueryProvider;

    /**
     * 礼品卡凑单页
     *
     * @return
     */
    @Operation(summary = "礼品卡凑单页")
    @RequestMapping(value = "/gift-card-goods", method = RequestMethod.POST)
    public BaseResponse<GiftCardGoodsPageResponse> listGoodsByGiftCardId(@RequestBody GiftCardGoodsPageRequest request) {
        if(Objects.isNull(request.getGiftCardId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GiftCardGoodsPageResponse giftCardGoodsPageResponse = new GiftCardGoodsPageResponse();
        EsGoodsInfoResponse esGoodsInfoResponse = new EsGoodsInfoResponse();
        GiftCardInfoResponse giftCardInfoResponse = giftCardQueryProvider.queryDetail(GiftCardInfoRequest.builder()
                .giftCardId(request.getGiftCardId()).build()).getContext();
        if(Objects.isNull(giftCardInfoResponse)){
            return BaseResponse.success(giftCardGoodsPageResponse);
        }
        GiftCardVO giftCardVO = giftCardInfoResponse.getGiftCardVO();
        if(Objects.isNull(giftCardVO)){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        giftCardGoodsPageResponse.setGiftCardVO(giftCardVO);//礼品卡信息

        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        esGoodsInfoQueryRequest.setStoreState(StoreState.OPENING.toValue());
        esGoodsInfoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
        esGoodsInfoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        if(StringUtils.isBlank(request.getSortType())){
            esGoodsInfoQueryRequest.setSortFlag(0);
        }
        esGoodsInfoQueryRequest.setPageNum(request.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(request.getPageSize());
        esGoodsInfoQueryRequest.setCateAggFlag(true);
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        esGoodsInfoQueryRequest.setContractStartDate(now);
        esGoodsInfoQueryRequest.setContractEndDate(now);
        esGoodsInfoQueryRequest.setVendibility(Constants.yes);
        esGoodsInfoQueryRequest.setKeywords(request.getKeywords());
        if (commonUtil.getTerminal() == TerminalSource.PC) {
            esGoodsInfoQueryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        }
        if (CollectionUtils.isNotEmpty(request.getCateIds())) {
            esGoodsInfoQueryRequest.setCateIds(request.getCateIds());
        }
        if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
            esGoodsInfoQueryRequest.setBrandIds(request.getBrandIds());
        }
        esGoodsInfoQueryRequest.setSortFlag(request.getSortFlag());

        List<Long> brandIds = new ArrayList<>();
        List<Long> cateIds = new ArrayList<>();
        List<Long> storeIds = new ArrayList<>();
        List<String> goodsInfoIds = new ArrayList<>();
        if(giftCardVO.getScopeType() != GiftCardScopeType.ALL){
            giftCardVO.getScopeIdList().forEach(giftCardScopeId -> {
                if(giftCardVO.getScopeType() == GiftCardScopeType.BRAND){
                    brandIds.add(Long.valueOf(giftCardScopeId));
                }
                if(giftCardVO.getScopeType() == GiftCardScopeType.CATE){
                    cateIds.add(Long.valueOf(giftCardScopeId));
                }
                if(giftCardVO.getScopeType() == GiftCardScopeType.STORE){
                    storeIds.add(Long.valueOf(giftCardScopeId));
                }
                if(giftCardVO.getScopeType() == GiftCardScopeType.GOODS
                        || GiftCardType.PICKUP_CARD.equals(giftCardVO.getGiftCardType())){
                    goodsInfoIds.add(giftCardScopeId);
                }
            });
        }
        if(CollectionUtils.isNotEmpty(brandIds)){
            esGoodsInfoQueryRequest.setBrandIds(brandIds);
        }
        if(CollectionUtils.isNotEmpty(cateIds)){
            esGoodsInfoQueryRequest.setCateIds(cateIds);
        }
        if(CollectionUtils.isNotEmpty(storeIds)){
            esGoodsInfoQueryRequest.setStoreIds(storeIds);
        }
        if(CollectionUtils.isNotEmpty(goodsInfoIds)){
            esGoodsInfoQueryRequest.setGoodsInfoIds(goodsInfoIds);
        }
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            esGoodsInfoQueryRequest.setIsBuyCycle(Constants.no);
        }
        esGoodsInfoResponse = esGoodsInfoElasticQueryProvider.page(esGoodsInfoQueryRequest).getContext();
        List<EsGoodsInfoVO> goodsInfoVOs = esGoodsInfoResponse.getEsGoodsInfoPage().getContent();

        if (CollectionUtils.isNotEmpty(goodsInfoVOs)) {
            //未开启商品抵扣时，清零buyPoint
            systemPointsConfigService.clearBuyPoinsForEsSku(goodsInfoVOs);

            if (!GiftCardType.PICKUP_CARD.equals(giftCardVO.getGiftCardType())){
                //获取会员和等级
                String customerId = commonUtil.getOperatorId();

                //组装优惠券标签
                List<GoodsInfoVO> goodsInfoList = goodsInfoVOs.stream().filter(e -> Objects.nonNull(e.getGoodsInfo()))
                        .map(e -> KsBeanUtil.convert(e.getGoodsInfo(), GoodsInfoVO.class))
                        .collect(Collectors.toList());

                // 构建[skuId => 起售数量]Map，防止原始list被后续代码覆盖，丢失字段
                Map<String, Long> skuStartSaleNumMap = goodsInfoList.stream().collect(
                        Collectors.toMap(GoodsInfoVO::getGoodsInfoId, goodsInfoVO -> goodsInfoVO.getStartSaleNum()));

                //计算营销
                MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
                filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class));
                filterRequest.setCustomerId(customerId);
                goodsInfoList = marketingPluginProvider.goodsListFilter(filterRequest).getContext().getGoodsInfoVOList();

                //计算区间价
                GoodsIntervalPriceByCustomerIdResponse priceResponse =
                        goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoList, customerId);
                esGoodsInfoResponse.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
                goodsInfoList = priceResponse.getGoodsInfoVOList();

                //重新赋值于Page内部对象
                Map<String, GoodsInfoVO> voMap = goodsInfoList.stream()
                        .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, g -> g));
                esGoodsInfoResponse
                        .getEsGoodsInfoPage()
                        .getContent()
                        .forEach(
                                esGoodsInfo -> {
                                    GoodsInfoVO vo =
                                            voMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                                    // 填充起售数量
                                    vo.setStartSaleNum(
                                            skuStartSaleNumMap.getOrDefault(vo.getGoodsInfoId(), 1L));
                                    if (Objects.nonNull(vo)) {
                                        GoodsInfoNestVO goodsInfoNestVO =
                                                KsBeanUtil.convert(vo, GoodsInfoNestVO.class);
                                        //分销商品 将付费会员价 改为null
                                        if (Objects.equals(DistributionGoodsAudit.CHECKED, goodsInfoNestVO.getDistributionGoodsAudit())) {
                                            goodsInfoNestVO.setPayMemberPrice(null);
                                        }
                                        esGoodsInfo.setGoodsInfo(goodsInfoNestVO);
                                    }
                                });
            }
            giftCardGoodsPageResponse.setEsGoodsInfoResponse(esGoodsInfoResponse);
            return BaseResponse.success(giftCardGoodsPageResponse);
        } else {
            giftCardGoodsPageResponse.setEsGoodsInfoResponse(esGoodsInfoResponse);
            return BaseResponse.success(giftCardGoodsPageResponse);
        }
    }
}
