package com.wanmi.sbc.marketing.buyoutprice.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelListByCustomerLevelNameRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceIdRequest;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceSearchRequest;
import com.wanmi.sbc.marketing.api.response.buyoutprice.MarketingBuyoutPriceMarketingIdResponse;
import com.wanmi.sbc.marketing.bean.dto.MarketingBuyoutPriceLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.buyoutprice.model.entry.MarketingBuyoutPriceLevel;
import com.wanmi.sbc.marketing.buyoutprice.model.request.MarketingBuyoutPriceSaveRequest;
import com.wanmi.sbc.marketing.buyoutprice.repository.MarketingBuyoutPriceLevelRepository;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.service.MarketingScopeService;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.common.service.MarketingServiceInterface;
import com.wanmi.sbc.marketing.halfpricesecondpiece.service.MarketingHalfPriceSecondPieceLevelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 营销一口价业务
 */
@Service
public class MarketingBuyoutPriceService {

    @Autowired
    private MarketingBuyoutPriceLevelRepository marketingBuyoutPriceLevelRepository;

    @Autowired
    private MarketingRepository marketingRepository;

    @Autowired
    @Lazy
    private MarketingService marketingService;

    @Autowired
    private MarketingServiceInterface marketingServiceInterface;

    @Autowired
    protected MarketingBuyoutPriceFullBaleLevelService buyoutPriceFullBaleLevelService;

    @Autowired
    protected StoreQueryProvider storeQueryProvider;
    @Autowired
    protected MarketingHalfPriceSecondPieceLevelService marketingHalfPriceSecondPieceLevelService;
    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;
    @Autowired
    @Lazy
    private MarketingScopeService marketingScopeService;

    /**
     * 新增一口价
     */
    @Transactional(rollbackFor = Exception.class)
    public Marketing addMarketingBuyoutPrice(MarketingBuyoutPriceSaveRequest request) throws SbcRuntimeException {
        request.setPluginType(Nutils.defaultVal(request.getPluginType(), PluginType.NORMAL));
        // 检查营销活动时间和规则冲突
        checkMarketingTimeAndRuleConflict(request);
        Marketing marketing = marketingServiceInterface.addMarketing(request);
        // 保存多级优惠信息
        this.saveLevelList(request.generateBuyoutPriceLevelList(marketing.getMarketingId()));

        return marketing;
    }

    /**
     * 修改一口价
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyMarketingBuyoutPrice(MarketingBuyoutPriceSaveRequest request) throws SbcRuntimeException {
        request.setPluginType(Nutils.defaultVal(request.getPluginType(), PluginType.NORMAL));
        // 检查营销活动时间和规则冲突
        checkMarketingTimeAndRuleConflict(request);
        // 修改营销信息
        marketingServiceInterface.modifyMarketing(request);
        // 先删除已有的多级优惠信息，然后再保存
        marketingBuyoutPriceLevelRepository.deleteByMarketingId(request.getMarketingId());
        //保存多节营销活动
        this.saveLevelList(request.generateBuyoutPriceLevelList(request.getMarketingId()));
    }


    /**
     * 保存多级优惠信息
     */
    private void saveLevelList(List<MarketingBuyoutPriceLevel> buyoutPriceLevelList) {
        if (CollectionUtils.isNotEmpty(buyoutPriceLevelList)) {
            marketingBuyoutPriceLevelRepository.saveAll(buyoutPriceLevelList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 查询一口价营销活动的详情
     *
     * @param request
     * @return
     */
    public MarketingBuyoutPriceMarketingIdResponse details(MarketingBuyoutPriceIdRequest request) {
        Marketing marketing = marketingService.queryById(request.getMarketingId());
        MarketingBuyoutPriceMarketingIdResponse marketingBuyoutPriceMarketing = KsBeanUtil.convert(marketing,
                MarketingBuyoutPriceMarketingIdResponse.class);
        //获取营销规则
        List<MarketingBuyoutPriceLevel> buyoutPriceLevel =
                marketingBuyoutPriceLevelRepository.findByMarketingId(request.getMarketingId());
        marketingBuyoutPriceMarketing.setMarketingBuyoutPriceLevelVO(KsBeanUtil.copyListProperties(buyoutPriceLevel,
                MarketingBuyoutPriceLevelDTO.class));
        return marketingBuyoutPriceMarketing;
    }


    /**
     * 根据字段模糊查询
     *
     * @param request
     * @return
     */
    public MicroServicePage<MarketingPageVO> search(MarketingBuyoutPriceSearchRequest request) {
        List<Long> storeIds = null;
        //获取店铺信息
        if (StringUtils.isNotBlank(request.getShopName())) {
            ListStoreByNameRequest stores = new ListStoreByNameRequest();
            stores.setStoreName(request.getShopName());
            storeIds = storeQueryProvider.listByName(stores).getContext().getStoreVOList().stream()
                    .map(StoreVO::getStoreId).collect(Collectors.toList());
            // 根据店铺名称没有匹配到店铺，则返回空数据
            if (CollectionUtils.isEmpty(storeIds)){
                return new MicroServicePage<>(
                        Collections.emptyList(), request.getPageable(), NumberUtils.LONG_ZERO);
            }
        }
        Page<Marketing> page = marketingService.page(request, storeIds, request.getStoreId());
        MicroServicePage<MarketingPageVO> marketingPage = KsBeanUtil.convertPage(page, MarketingPageVO.class);
        if (request.getMarketingSubType() == MarketingSubType.BUYOUT_PRICE) {
            //设置一口价营销规则
            buyoutPriceFullBaleLevelService.listFullBaleLevel(marketingPage.getContent());
        }

        if (request.getMarketingSubType() == MarketingSubType.HALF_PRICE_SECOND_PIECE) {
            //设置第二件半价营销规则
            marketingHalfPriceSecondPieceLevelService.halfPriceSecondPieceLevel(marketingPage.getContent());
        }
        //填充目标客户+客户名称
        this.setCustomerLevelAndStoreName(marketingPage.getContent());

        return marketingPage;
    }

    /**
     * 查询关联的店铺信息
     *
     * @param marketingList
     */
    public void getStoreName(List<MarketingPageVO> marketingList) {
        for (MarketingPageVO marketingId : marketingList) {
            StoreByIdRequest storeByIdRequest = new StoreByIdRequest();
            if (marketingId.getStoreId() != null) {
                storeByIdRequest.setStoreId(marketingId.getStoreId());
                String storeName = getOneStoreName(storeByIdRequest,marketingId);
                if (storeName != null) {
                    marketingId.setStoreName(storeName);
                }
            }

        }
    }

    /**
     * 查询目标客户
     *
     * @param marketingList
     */
    protected void setCustomerLevelAndStoreName(List<MarketingPageVO> marketingList) {
        if (CollectionUtils.isEmpty(marketingList)) {
            return;
        }
        List<MarketingCustomerLevelDTO> customerLevelDTOList = marketingList.stream().map(m -> {
            MarketingCustomerLevelDTO dto = new MarketingCustomerLevelDTO();
            dto.setMarketingId(m.getMarketingId());
            dto.setStoreId(m.getStoreId());
            dto.setJoinLevel(m.getJoinLevel());
            return dto;
        }).collect(Collectors.toList());
        List<MarketingCustomerLevelVO> marketingCustomerLevelVOList = customerLevelQueryProvider.
                listByCustomerLevelName(new CustomerLevelListByCustomerLevelNameRequest(customerLevelDTOList)).
                getContext().getCustomerLevelVOList();
        Map<Long, MarketingCustomerLevelVO> levelVOMap = marketingCustomerLevelVOList.stream().
                collect(Collectors.toMap(MarketingCustomerLevelVO::getMarketingId, Function.identity()));
        marketingList.forEach(marketingPageVO -> {
            MarketingCustomerLevelVO levelVO = levelVOMap.get(marketingPageVO.getMarketingId());
            marketingPageVO.setLevelName(levelVO.getLevelName());
            marketingPageVO.setStoreName(levelVO.getStoreName());
        });
    }

    /**
     * @description 检查营销活动时间和规则冲突
     * @author malianfeng
     * @date 2021/7/16 17:43
     * @param request 营销活动添加/编辑请求
     * @return void
     */
    public void checkMarketingTimeAndRuleConflict(MarketingBuyoutPriceSaveRequest request) {
        // 打包一口价设置重合时间内，有不同设置规则且都为全部商品，不允许设置
        if (request.getSubType() == MarketingSubType.BUYOUT_PRICE) {
            // 获取时间冲突的营销活动
            List<Long> existsMarketingList = marketingRepository.getExistsMarketingByStoreIdAndScopeType(request.getStoreId(),
                    request.getScopeType().toValue(), request.getBeginTime(), request.getEndTime(),
                    request.getMarketingId(), request.getPluginType().toValue());

            //只有自定义商品校验商品互斥
            if (request.getScopeType() == MarketingScopeType.SCOPE_TYPE_CUSTOM) {
                // 验证ScopeId是否有重复
                List<MarketingScope> marketingScopeList = marketingScopeService.findByMarketingIdList(existsMarketingList);
                if (CollectionUtils.isEmpty(marketingScopeList)) {
                    return;
                }
                List<String> scopeIdList = marketingScopeList.stream().map(MarketingScope :: getScopeId).collect(Collectors.toList());
                scopeIdList.retainAll(request.getScopeIds());
                if (CollectionUtils.isNotEmpty(scopeIdList)) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080022);
                }
            }
        }
    }

    protected String getOneStoreName(StoreByIdRequest storeByIdRequest,MarketingPageVO marketingId){
        return storeQueryProvider.getById(storeByIdRequest).getContext().getStoreVO().getStoreName();
    }
}
