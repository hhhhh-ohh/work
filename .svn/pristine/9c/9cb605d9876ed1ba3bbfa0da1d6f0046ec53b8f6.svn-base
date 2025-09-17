package com.wanmi.ares.community.dao;

import com.wanmi.ares.community.model.CommunityOverviewReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommunityReportMapper {

    int insertCommunityOverviewDay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int delCommunityOverviewDay(@Param("startDate") LocalDate startDate);

    int insertCommunityOverviewDayBoss(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int insertCommunityGoodsDay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityGoodsDay(@Param("startDate") LocalDate startDate);

    int insertCommunityGoodsSeven(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityGoodsSeven(@Param("startDate") LocalDate startDate);

    int insertCommunityGoodsThirty(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityGoodsThirty(@Param("startDate") LocalDate startDate);

    int insertCommunityGoodsMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityGoodsMonth(@Param("startDate") LocalDate startDate);

    int insertCommunityLeaderDay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                 @Param("isBoss") int isBoss);

    int delCommunityLeaderDay(@Param("startDate") LocalDate startDate);

    int insertCommunityLeaderSeven(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                   @Param("isBoss") int isBoss);
    int delCommunityLeaderSeven(@Param("startDate") LocalDate startDate);

    int insertCommunityLeaderThirty(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                    @Param("isBoss") int isBoss);
    int delCommunityLeaderThirty(@Param("startDate") LocalDate startDate);

    int insertCommunityLeaderMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                   @Param("isBoss") int isBoss);
    int delCommunityLeaderMonth(@Param("startDate") LocalDate startDate);

    int insertCommunityCustomerDay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityCustomerDay(@Param("startDate") LocalDate startDate);

    int insertCommunityCustomerSeven(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityCustomerSeven(@Param("startDate") LocalDate startDate);

    int insertCommunityCustomerThirty(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityCustomerThirty(@Param("startDate") LocalDate startDate);

    int insertCommunityCustomerMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int delCommunityCustomerMonth(@Param("startDate") LocalDate startDate);

    List<CommunityOverviewReport> queryCommunityOverviewDayByYesterday();

    List<CommunityOverviewReport> queryCommunityOverview(List<Long> storeIds);

    Long queryCustomerNum();

    Long queryLeaderNum();

    int insertCommunityOverview(List<CommunityOverviewReport> communityOverviewReports);

    int delCommunityOverview(List<Long> storeIds);

    CommunityOverviewReport queryForSum();

    CommunityOverviewReport queryByBoss();

    int delOverviewForAll();

    int insertOverviewForAll();

}
