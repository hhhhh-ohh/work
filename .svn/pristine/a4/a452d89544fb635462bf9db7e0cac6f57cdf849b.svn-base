package com.wanmi.ares.marketing.suits.dao;

import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:59
 * @Description：
 */
@Repository
public interface SuitsStatisticsMapper {

    /**
     * 保存活动数据
     * @return
     */
    int insertMarketingStatisticsDay(MarketingAnalysisJobRequest jobRequest);

    int insertTradeMarketingSkuDetailDay(MarketingAnalysisJobRequest queryRequest);

    /**
     * 活动效果
     * @param request
     * @return
     */
    List<SuitsReport> pageActivityEffect(EffectPageRequest request);
}
