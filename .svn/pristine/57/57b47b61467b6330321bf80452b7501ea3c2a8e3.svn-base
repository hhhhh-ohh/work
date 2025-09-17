package com.wanmi.ares.marketing.overview.dao;

import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.request.marketing.MarketingOverviewRequest;
import com.wanmi.ares.request.marketing.MarketingSituationInsertRequest;
import com.wanmi.ares.response.MarketingDataSituation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingSituationMapper {
    /**
     * 秒杀活动
     */
    // boss端
    void saveBossFlashDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFlashSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFlashThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFlashMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierFlashDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFlashSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFlashThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFlashMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 预售活动
     * @return
     */
    // boss端
    void saveBossBookingDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossBookingSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossBookingThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossBookingMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierBookingDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierBookingSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierBookingThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierBookingMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 预约活动
     * @return
     */
    // boss端
    void saveBossAppointmentDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossAppointmentSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossAppointmentThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossAppointmentMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierAppointmentDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierAppointmentSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierAppointmentThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierAppointmentMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 拼团活动
     * @return
     */
    // boss端
    void saveBossGrouponDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossGrouponSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossGrouponThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossGrouponMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierGrouponDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierGrouponSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierGrouponThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierGrouponMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 优惠券活动
     * @return
     */
    // boss端
    void saveBossCouponDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossCouponSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossCouponThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossCouponMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierCouponDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierCouponSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierCouponThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierCouponMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 减折赠活动
     * @return
     */
    // boss端
    void saveBossFullSeriesDay(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFullSeriesSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFullSeriesThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveBossFullSeriesMonth(@Param("request") MarketingSituationInsertRequest request);
    // supplier端
    void saveSupplierFullSeriesDay(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFullSeriesSeven(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFullSeriesThirty(@Param("request") MarketingSituationInsertRequest request);
    void saveSupplierFullSeriesMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 清除数据
     * @param startDate
     */
    void deleteDay(@Param("startDate") String startDate);
    void deleteSeven();
    void deleteThirty();
    void deleteMonth(@Param("startDate") String startDate);


    List<MarketingDataSituation> findByDay(MarketingOverviewRequest request);

    List<MarketingDataSituation> findBySeven(MarketingOverviewRequest request);

    List<MarketingDataSituation> findByThirty(MarketingOverviewRequest request);

    List<MarketingDataSituation> findByMonth(MarketingOverviewRequest request);

    /**
     * 通用活动 boss端天维度
     * @param request
     * @return
     */
    void saveBossCommonDay(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 通用活动 boss端7天维度、30天维度
     * @param request
     * @return
     */
    void saveBossCommon(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 通用活动 boss端自然月维度
     * @param request
     * @return
     */
    void saveBossCommonMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 通用活动 supplier端天维度
     * @param request
     * @return
     */
    void saveSupplierCommonDay(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 通用活动 supplier端7天维度、30天维度
     * @param request
     * @return
     */
    void saveSupplierCommon(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 通用活动 supplier端自然月维度
     * @param request
     * @return
     */
    void saveSupplierCommonMonth(@Param("request") MarketingSituationInsertRequest request);

    /**
     * 砍价活动 boss端天维度
     * @param request
     * @return
     */
    void saveBossBargainDay(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 砍价活动 boss端7天、30天维度
     * @param request
     * @return
     */
    void saveBossBargainCommon(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 砍价活动 boss端自然月维度
     * @param request
     * @return
     */
    void saveBossBargainMonth(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 砍价活动 supplier端天维度
     * @param request
     * @return
     */
    void saveSupplierBargainDay(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 砍价活动 supplier端7天/30天维度
     * @param request
     * @return
     */
    void saveSupplierBargainCommon(@Param("request") MarketingSituationInsertRequest request);
    /**
     * 砍价活动 supplier端自然月维度
     * @param request
     * @return
     */
    void saveSupplierBargainMonth(@Param("request") MarketingSituationInsertRequest request);
}
