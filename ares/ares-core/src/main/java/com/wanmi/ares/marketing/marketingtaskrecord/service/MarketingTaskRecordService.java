package com.wanmi.ares.marketing.marketingtaskrecord.service;


import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.marketing.marketingtaskrecord.dao.MarketingTaskRecordMapper;
import com.wanmi.ares.marketing.marketingtaskrecord.model.MarketingTaskRecord;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.request.marketing.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:56
 * @Description：
 */
@Service
public class MarketingTaskRecordService {

    @Autowired
    private MarketingTaskRecordMapper marketingTaskRecordMapper;

    /**
     * 插入
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public long insert(MarketingAnalysisJobRequest jobRequest){
        MarketingTaskRecord marketingTaskRecord = new MarketingTaskRecord();
        marketingTaskRecord.setMarketingType(MarketingType.valueOf(jobRequest.getMarketingType()).toValue());
        marketingTaskRecord.setCreateTime(LocalDate.now().minusDays(1L));
        if(StringUtils.isNotBlank(jobRequest.getInitDate())){
            marketingTaskRecord.setCreateTime(LocalDate.parse(jobRequest.getInitDate()));
        }
        return marketingTaskRecordMapper.insert(marketingTaskRecord);
    }


}
