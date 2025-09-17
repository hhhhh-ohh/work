package com.wanmi.ares.marketing.marketingtaskrecord.dao;

import com.wanmi.ares.marketing.marketingtaskrecord.model.MarketingTaskRecord;
import com.wanmi.ares.request.marketing.MarketingQueryEarliestDateRequest;
import com.wanmi.ares.request.marketing.MarketingTaskRecordQueryRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:59
 * @Description：
 */
@Repository
public interface MarketingTaskRecordMapper {

    long insert(MarketingTaskRecord marketingTaskRecord);

    /**
     * 查询列表
     * @return
     */
    List<MarketingTaskRecord> list(MarketingTaskRecordQueryRequest request);

    /**
     * 查询开始最早时间
     * @param request
     * @return
     */
    LocalDate queryEarliestTime(MarketingTaskRecordQueryRequest request);

    /**
     * 查询数量
     * @return
     */
    Long count(MarketingTaskRecordQueryRequest request);
}
