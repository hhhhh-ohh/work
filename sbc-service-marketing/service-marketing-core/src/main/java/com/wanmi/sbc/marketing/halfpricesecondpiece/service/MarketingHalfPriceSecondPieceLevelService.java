package com.wanmi.sbc.marketing.halfpricesecondpiece.service;

import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.entry.MarketingHalfPriceSecondPieceLevel;
import com.wanmi.sbc.marketing.halfpricesecondpiece.repository.HalfPriceSecondPieceLevelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 一口价营销规则业务
 */
@Service
public class MarketingHalfPriceSecondPieceLevelService {

    @Autowired
    private HalfPriceSecondPieceLevelRepository halfPriceSecondPieceLevelRepository;

    /**
     * 凭借营销规则信息
     */
    public void halfPriceSecondPieceLevel(List<MarketingPageVO> marketingList) {
        List<Long> marketingIds =
                marketingList.stream().map(MarketingPageVO::getMarketingId).collect(Collectors.toList());
        List<MarketingHalfPriceSecondPieceLevel> levelList =
                halfPriceSecondPieceLevelRepository.findByMarketingIdIn(marketingIds);
        Map<Long, MarketingHalfPriceSecondPieceLevel> levelMap =
                levelList.stream().collect(Collectors.toMap(MarketingHalfPriceSecondPieceLevel::getMarketingId,
                        Function.identity()));
        for (MarketingPageVO marketingId : marketingList) {
            MarketingHalfPriceSecondPieceLevel levels = levelMap.get(marketingId.getMarketingId());
            List<String> levelsList = new ArrayList<>();
            if (Objects.nonNull(levels)) {
                //第二件半价，当时0折的时候规则描述成买一送一
                if (levels.getDiscount().compareTo(BigDecimal.ZERO) != 0) {
                    String builder = "第" + levels.getNumber() + "件" + levels.getDiscount() + "折";
                    levelsList.add(builder);
                } else {
                    levelsList.add("买" + (levels.getNumber() - 1) + "送1");
                }
            }
            if(CollectionUtils.isNotEmpty(levelsList)) {
                marketingId.setRulesList(levelsList);
            }
        }
    }
}
