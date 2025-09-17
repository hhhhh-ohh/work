package com.wanmi.sbc.marketing.reduction.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.common.service.MarketingServiceInterface;
import com.wanmi.sbc.marketing.reduction.model.entity.MarketingFullReductionLevel;
import com.wanmi.sbc.marketing.reduction.model.request.MarketingFullReductionSaveRequest;
import com.wanmi.sbc.marketing.reduction.repository.MarketingFullReductionLevelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 营销满减业务
 */
@Service
public class MarketingFullReductionService implements MarketingFullReductionServiceInterface {

    @Autowired
    private MarketingFullReductionLevelRepository marketingFullReductionLevelRepository;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private MarketingServiceInterface marketingServiceInterface;

    public MarketingService getMarketingService() {
        return marketingService;
    }

    /**
     * 新增满减
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Marketing addMarketingFullReduction(MarketingFullReductionSaveRequest request) throws SbcRuntimeException {
        Marketing marketing = getMarketingService().addMarketing(request);
        // 保存多级优惠信息
        this.saveLevelList(request.generateFullReductionLevelList(marketing.getMarketingId()));
        return marketing;
    }

    /**
     * 修改满减
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyMarketingFullReduction(MarketingFullReductionSaveRequest request) throws SbcRuntimeException {
        marketingServiceInterface.modifyMarketing(request);
        // 先删除已有的多级优惠信息，然后再保存
        marketingFullReductionLevelRepository.deleteByMarketingId(request.getMarketingId());
        this.saveLevelList(request.generateFullReductionLevelList(request.getMarketingId()));
    }

    /**
     * 根据营销编号查询营销等级集合
     *
     * @param marketingId
     * @return
     */
    public List<MarketingFullReductionLevel> findByMarketingId(Long marketingId){
        return marketingFullReductionLevelRepository.findByMarketingId(marketingId);
    }

    /**
     * 保存多级优惠信息
     */
    private void saveLevelList(List<MarketingFullReductionLevel> fullReductionLevelList) {
        if(CollectionUtils.isNotEmpty(fullReductionLevelList)) {
            marketingFullReductionLevelRepository.saveAll(fullReductionLevelList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
