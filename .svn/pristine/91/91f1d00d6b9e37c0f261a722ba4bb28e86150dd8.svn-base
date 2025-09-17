package com.wanmi.sbc.marketing.halfpricesecondpiece.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.common.service.MarketingServiceInterface;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.entry.MarketingHalfPriceSecondPieceLevel;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.request.HalfPriceSecondPieceSaveRequest;
import com.wanmi.sbc.marketing.halfpricesecondpiece.repository.HalfPriceSecondPieceLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 第二件半价业务
 */
@Service
public class HalfPriceSecondPieceService implements HalfPriceSecondPieceServiceInterface{

    @Autowired
    private HalfPriceSecondPieceLevelRepository halfPriceSecondPieceLevelRepository;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private MarketingServiceInterface marketingServiceInterface;

    public MarketingService getMarketingService() {
        return marketingService;
    }

    /**
     * 新增第二件半价
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Marketing addMarketingHalfPriceSecondPiece(HalfPriceSecondPieceSaveRequest request) throws SbcRuntimeException {
        Marketing marketing = marketingServiceInterface.addMarketing(request);

        // 保存多级优惠信息
        this.saveLevel(KsBeanUtil.convert(request.generateHalfPriceSecondPieceList(marketing.getMarketingId()), MarketingHalfPriceSecondPieceLevel.class));

        return marketing;
    }

    /**
     * 修改第二件半价
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyHalfPriceSecondPiece(HalfPriceSecondPieceSaveRequest request) throws SbcRuntimeException {
        //修改营销信息
        marketingServiceInterface.modifyMarketing(request);
        // 先删除已有的多级优惠信息，然后再保存
        halfPriceSecondPieceLevelRepository.deleteByMarketingId(request.getMarketingId());
        //保存多节营销活动
        this.saveLevel(KsBeanUtil.convert(request.generateHalfPriceSecondPieceList(request.getMarketingId()), MarketingHalfPriceSecondPieceLevel.class));
    }


    /**
     * 保存多级优惠信息
     */
    private void saveLevel(MarketingHalfPriceSecondPieceLevel halfPriceSecondPieceLevel) {
        if (Objects.nonNull(halfPriceSecondPieceLevel)) {
            halfPriceSecondPieceLevelRepository.save(halfPriceSecondPieceLevel);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
