package com.wanmi.ares.report.paymember.dao;

import com.wanmi.ares.report.paymember.model.request.PayMemberAreaDistributeRequest;
import com.wanmi.ares.report.paymember.model.root.PayMemberAreaDistributeReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 付费会员区域持久化Mapper
 */
@Repository
public interface PayMemberAreaDistributeReportMapper {

    /**
     * 生成平台区域数据
     * @param areaRequest
     * @return
     */
    int generateBossPayMemberAreaDistribute(PayMemberAreaDistributeRequest areaRequest);

    /**
     * 报表查询
     */
    List<PayMemberAreaDistributeReport> query(@Param("date") LocalDate date);

    /**
     * 删除指定日期的数据
     */
    int deleteReport(String dateStr);
}
