package com.wanmi.ares.report.flow.service;

import com.wanmi.ares.report.flow.dao.ReplayMarketingSkuPvMapper;
import com.wanmi.ares.report.flow.dao.ReplayMarketingSkuUvMapper;
import com.wanmi.ares.request.mq.MarketingSkuSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName FollowMarketingReportService
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/7 11:40
 * @Version 1.0
 **/
@Service
public class FollowMarketingReportService {
    @Autowired
    private ReplayMarketingSkuPvMapper pvMapper;
    @Autowired
    private ReplayMarketingSkuUvMapper uvMapper;

    @Transactional
    public void save(List<MarketingSkuSource> list){
        pvMapper.insertByList(list);
        for(MarketingSkuSource marketingSkuSource : list) {
            if (marketingSkuSource.getCustomerIds() !=null) {
                uvMapper.insertByList(marketingSkuSource);
            }
        }
    }
}
