package com.wanmi.sbc.marketing.gift.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelMapGetResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftReduceStockRequest;
import com.wanmi.sbc.marketing.bean.dto.FullGiftDetailDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullGiftLevelDTO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftLevelVO;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.gift.model.root.MarketingFullGiftDetail;
import com.wanmi.sbc.marketing.gift.model.root.MarketingFullGiftLevel;
import com.wanmi.sbc.marketing.gift.repository.MarketingFullGiftDetailRepository;
import com.wanmi.sbc.marketing.gift.repository.MarketingFullGiftLevelRepository;
import com.wanmi.sbc.marketing.gift.request.MarketingFullGiftSaveRequest;
import com.wanmi.sbc.marketing.gift.response.MarketingFullGiftLevelResponse;
import com.wanmi.sbc.marketing.plugin.impl.CustomerLevelPlugin;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import io.seata.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 营销满赠业务
 */
@Service
public class MarketingFullGiftService implements MarketingFullGiftServiceInterface{

    @Autowired
    private MarketingFullGiftLevelRepository marketingFullGiftLevelRepository;

    @Autowired
    private MarketingFullGiftDetailRepository marketingFullGiftDetailRepository;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    protected GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    protected CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    protected CustomerLevelPlugin customerLevelPlugin;

    @Autowired
    protected GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    public MarketingService getMarketingService() {
        return marketingService;
    }

    /**
     * 新增满赠
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Marketing addMarketingFullGift(MarketingFullGiftSaveRequest request) throws SbcRuntimeException {

        Marketing marketing = getMarketingService().addMarketing(request);

        // 保存多级优惠信息
        List<MarketingFullGiftLevelDTO> levelList = request.generateFullGiftLevelList(marketing.getMarketingId());
        this.saveLevelList(levelList);
        request.setFullGiftLevelList(levelList);
        this.saveLevelGiftDetailList(request.generateFullGiftDetailList(request.getFullGiftLevelList()));

        return marketing;
    }

    /**
     * 修改满赠
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyMarketingFullGift(MarketingFullGiftSaveRequest request) throws SbcRuntimeException {
        marketingService.modifyMarketing(request);

        // 先删除已有的多级优惠信息，然后再保存
        marketingFullGiftLevelRepository.deleteByMarketingId(request.getMarketingId());
        List<MarketingFullGiftLevelDTO> levelList = request.generateFullGiftLevelList(request.getMarketingId());
        this.saveLevelList(levelList);
        request.setFullGiftLevelList(levelList);

        // 先删除已有的赠品信息，然后再保存
        marketingFullGiftDetailRepository.deleteByMarketingId(request.getMarketingId());
        this.saveLevelGiftDetailList(request.generateFullGiftDetailList(request.getFullGiftLevelList()));
    }

    @Override
    public void reduceStock(FullGiftReduceStockRequest request) {
        for (FullGiftDetailDTO fullGiftDetailDTO : request.getFullGiftDetailList()) {
            marketingFullGiftDetailRepository.decreaseStock(request.getMarketingId(),
                    fullGiftDetailDTO.getProductId(), fullGiftDetailDTO.getProductStock());
        }
    }

    /**
     * 保存多级优惠信息
     */
    private void saveLevelList(List<MarketingFullGiftLevelDTO> fullGiftLevelList) {
        if(CollectionUtils.isNotEmpty(fullGiftLevelList)) {
            List<MarketingFullGiftLevel> levels = KsBeanUtil.convertList(fullGiftLevelList, MarketingFullGiftLevel.class);
            marketingFullGiftLevelRepository.saveAll(levels);
            for(int i=0;i< fullGiftLevelList.size();i++){
                fullGiftLevelList.get(i).setGiftLevelId(levels.get(i).getGiftLevelId());
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 保存多级优惠赠品信息
     */
    private void saveLevelGiftDetailList(List<MarketingFullGiftDetail> giftDetailList) {
        if(CollectionUtils.isNotEmpty(giftDetailList)) {
            marketingFullGiftDetailRepository.saveAll(giftDetailList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 获取营销等级信息，包含等级下的detail
     *
     * @param marketingId
     * @return
     */
    public List<MarketingFullGiftLevelVO> getLevelsByMarketingId(Long marketingId){
        List<MarketingFullGiftLevelVO> levelList = KsBeanUtil.convertList(marketingFullGiftLevelRepository.findByMarketingIdOrderByFullAmountAscFullCountAsc(marketingId), MarketingFullGiftLevelVO.class);
        if(CollectionUtils.isNotEmpty(levelList)){
            List<MarketingFullGiftDetailVO> detailList = KsBeanUtil.convertList(marketingFullGiftDetailRepository.findByMarketingId(marketingId), MarketingFullGiftDetailVO.class);
            if(CollectionUtils.isNotEmpty(detailList)){
                Map<Long, List<MarketingFullGiftDetailVO>> map = detailList.stream().collect(Collectors.groupingBy(MarketingFullGiftDetailVO::getGiftLevelId));
                levelList.forEach(level -> level.setFullGiftDetailList(map.get(level.getGiftLevelId())));
            }
        }
        return levelList;
    }

    /**
     * 获取营销等级信息，包含等级下的detail
     *
     * @param marketingIds
     * @return
     */
    public List<MarketingFullGiftLevelVO> getLevelsByMarketingIds(List<Long> marketingIds){
        List<MarketingFullGiftLevelVO> levelList = KsBeanUtil.convertList(marketingFullGiftLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(marketingIds)), MarketingFullGiftLevelVO.class);
        if(CollectionUtils.isNotEmpty(levelList)){
            List<MarketingFullGiftDetailVO> detailList = KsBeanUtil.convertList(marketingFullGiftDetailRepository.findAll((root, cq, cb) -> root.get("marketingId").in(marketingIds)), MarketingFullGiftDetailVO.class);
            if(CollectionUtils.isNotEmpty(detailList)){
                Map<Long, List<MarketingFullGiftDetailVO>> map = detailList.stream().collect(Collectors.groupingBy(MarketingFullGiftDetailVO::getGiftLevelId));
                levelList.forEach(level -> level.setFullGiftDetailList(map.get(level.getGiftLevelId())));
            }
        }
        return levelList;
    }


    /**
     * 根据活动获取赠品列表
     * @param marketingId
     * @param customerId
     * @return
     */
    public MarketingFullGiftLevelResponse getGiftList(Long marketingId, String customerId, Boolean isMarketing){
        MarketingFullGiftLevelResponse response = new MarketingFullGiftLevelResponse();
        List<MarketingFullGiftLevelVO> levelList = getLevelsByMarketingId(marketingId);
        if (CollectionUtils.isEmpty(levelList)) {
            return response;
        }
        List<String> goodsInfoIds = levelList.stream()
                .map(MarketingFullGiftLevelVO::getFullGiftDetailList)
                .flatMap(Collection::stream)
                .map(MarketingFullGiftDetailVO::getProductId)
                .collect(Collectors.toList());
        //获取SKU信息

        List<GoodsInfoVO> giftList = goodsInfoQueryProvider.listViewByIds(
                GoodsInfoViewByIdsRequest.builder().goodsInfoIds(goodsInfoIds).isHavSpecText(Constants.yes).isMarketing(isMarketing).build()
        ).getContext().getGoodsInfos();
        List<String> providerGoodsInfoIdList = giftList.stream().filter(g -> StringUtils.isNotBlank(g.getProviderGoodsInfoId())).map(GoodsInfoVO::getProviderGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIdList)) {
            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerGoodsInfoIdList).build()).getContext().getGoodsInfos();
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                for (GoodsInfoVO goodsInfoVo : giftList) {
                    for (GoodsInfoVO pGoodsInfo : goodsInfos) {
                        if (pGoodsInfo.getGoodsInfoId().equals(goodsInfoVo.getProviderGoodsInfoId())) {
                            goodsInfoVo.setStock(pGoodsInfo.getStock());
                        }
                    }
                }
            }
        }

        response.setGiftList(giftList);
        //获取客户的等级
        if(StringUtils.isNotBlank(customerId)) {
            List<Long> storeIds = response.getGiftList().stream()
                    .map(GoodsInfoVO::getStoreId)
                    .filter(Objects::nonNull)
                    .distinct().collect(Collectors.toList());
            Map<Long, CommonLevelVO> levelMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(storeIds)) {
                CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapByCustomerIdAndStoreIdsRequest = new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
                customerLevelMapByCustomerIdAndStoreIdsRequest.setCustomerId(customerId);
                customerLevelMapByCustomerIdAndStoreIdsRequest.setStoreIds(storeIds);
                BaseResponse<CustomerLevelMapGetResponse> customerLevelMapGetResponseBaseResponse =
                        customerLevelQueryProvider.listCustomerLevelMapByCustomerIdAndIds(customerLevelMapByCustomerIdAndStoreIdsRequest);
                Map<Long, CommonLevelVO> tmpLevelMap = customerLevelMapGetResponseBaseResponse.getContext()
                        .getCommonLevelVOMap();
                levelMap.putAll(tmpLevelMap);
            }
            //计算会员价
            customerLevelPlugin.goodsListFilter(response.getGiftList(), MarketingPluginRequest.builder().customerId(customerId).levelMap(levelMap).build());
        }

        List<GoodsInfoVO> goodsInfoVOList = response.getGiftList();
        List<GoodsInfoDTO> goodsInfoDTOList = KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class);
        GoodsIntervalPriceByCustomerIdRequest request = new GoodsIntervalPriceByCustomerIdRequest();
        if(StringUtils.isNotBlank(customerId)) {
            request.setCustomerId(customerId);
        }
        request.setGoodsInfoDTOList(goodsInfoDTOList);
        //计算区间价
        GoodsIntervalPriceByCustomerIdResponse priceResponse = goodsIntervalPriceProvider.putByCustomerId(request).getContext();

        response.setGiftList(KsBeanUtil.copyListProperties(priceResponse.getGoodsInfoVOList(), GoodsInfoVO.class));
        response.setLevelList(levelList);
        return response;
    }

    /**
     * 根据活动和阶梯信息获取对应赠品列表
     * @param marketingId
     * @param giftLevelId
     * @return
     */
    public List<MarketingFullGiftDetail> getGiftList(Long marketingId,Long giftLevelId){
        return marketingFullGiftDetailRepository.findByMarketingIdAndGiftLevelId(marketingId,giftLevelId);
    }

    /**
     * 根据活动和阶梯信息批量获取对应赠品列表
     *
     * @param marketingIds 批量营销id
     * @param giftLevelIds 批量赠品等级id
     * @return
     */
    public List<MarketingFullGiftDetail> getGiftList(List<Long> marketingIds,List<Long> giftLevelIds){
        return marketingFullGiftDetailRepository.findByMarketingIdInAndGiftLevelIdIn(marketingIds,giftLevelIds);
    }

    public List<MarketingFullGiftDetail>  getGiftListByMarketingId(Long marketingId) {
        return marketingFullGiftDetailRepository.findByMarketingId(marketingId);
    }
}
