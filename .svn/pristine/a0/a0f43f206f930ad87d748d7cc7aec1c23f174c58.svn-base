package com.wanmi.ares.report.paymember.dao;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.report.paymember.model.root.PayMemberGrowReport;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 付费会员增长统计Mapper
 */
@Repository
public interface PayMemberGrowReportMapper {

    /**
     * 生成付费会员增长数据
     *
     * @param payMemberGrowReport
     * @return rows
     */
    int savePayMemberGrowthReport(@Param("report") PayMemberGrowReport payMemberGrowReport);

    /**
     * 根据时间清理那天的数据
     * @param deleteDate
     */
    void deletePayMemberGrowthReportByDate(@Param("deleteDate") String deleteDate);

    /**
     * 查询数量
     * @param request
     * @return
     */
    int queryGrowthTotal(@Param("request") PayMemberGrowthRequest request);

    /**
     * 查询平台所有的增长数量
     * @param request
     * @param pageRequest
     * @return
     */
    List<PayMemberGrowReport> findAllGrowReport(@Param("request") PayMemberGrowthRequest request, @Param("pageRequest") PageRequest pageRequest);

    /**
     * 查询会员增长趋势-按天
     * @param startDate
     * @param endDate
     * @return
     */
    List<PayMemberGrowReport> findAllGrowReportByDate(@Param("startDate") String startDate, @Param("endDate")String endDate);

    /**
     * 查询会员增长趋势-按周
     * @param startDate
     * @param endDate
     * @return
     */
    PayMemberGrowReport findAllGrowReportByWeek(@Param("startDate") String startDate, @Param("endDate")String endDate);

}
