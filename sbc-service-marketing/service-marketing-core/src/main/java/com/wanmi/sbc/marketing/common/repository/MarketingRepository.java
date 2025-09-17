package com.wanmi.sbc.marketing.common.repository;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.model.root.Marketing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 营销规则
 */
@Repository
public interface MarketingRepository extends JpaRepository<Marketing, Long>, JpaSpecificationExecutor<Marketing> {

    /**
     * 获取类型重复的skuIds
     * @param skuIds
     * @param
     * @return
     */
    @Query("select s.scopeId from Marketing m left join m.marketingScopeList s " +
            "where m.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and m.marketingType = :marketingType " +
            "and (m.auditStatus = 1 or m.auditStatus = 0 ) " +
            "and (:storeId is null or m.storeId = :storeId) and m.pluginType = :pluginType " +
            "and s.scopeId in :skuIds and not(m.beginTime > :endTime or m.endTime < :startTime) " +
            "and (:excludeId is null or m.marketingId <> :excludeId)")
    List<String> getExistsSkuByMarketingType(@Param("skuIds") List<String> skuIds, @Param("marketingType") MarketingType marketingType,
                                             @Param("pluginType") PluginType pluginType, @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime, @Param("storeId") Long storeId, @Param("excludeId") Long excludeId);


    /**
     * 删除活动
     * @param marketingId
     * @return
     */
    @Modifying
    @Query("update Marketing set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where marketingId = :marketingId")
    int deleteMarketing(@Param("marketingId") Long marketingId);


    /**
     * 暂停活动
     * @param marketingId
     * @return
     */
    @Modifying
    @Query("update Marketing set isPause = :isPause where marketingId = :marketingId")
    int pauseOrStartMarketing(@Param("marketingId") Long marketingId, @Param("isPause") BoolFlag isPause);

    /**
     * 获取验证进行中的营销
     * @param marketingIds
     * @return
     */
    @Query("select t.marketingId from Marketing t " +
            "where t.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND now() >= t.beginTime AND now() <= t.endTime AND t.isPause = 0 AND t.marketingId in (:marketingIds)")
    List<String> queryStartingMarketing(@Param("marketingIds") List<Long> marketingIds);

    /**
     * 根据skuId获取进行中组合购活动的id
     * @param skuId
     * @param
     * @return
     */
    @Query(value = "select distinct m.marketing_id from marketing m left join marketing_suits_sku s on m.marketing_id = s.marketing_id " +
            "where m.del_flag = 0 and m.store_id = :storeId and m.marketing_type = 6 and s.sku_id = :skuId " +
            "and not(m.begin_time > :endTime or m.end_time < :startTime) and (:excludeId is null or m.marketing_id <> :excludeId)", nativeQuery = true)
    List<String> getMarketingSuitsExists(@Param("skuId") String skuId, @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime, @Param("storeId") Long storeId, @Param("excludeId") Long excludeId);

    /**
     * 根据skuId查询正在进行中的组合购活动
     * @param skuId
     * @return
     */
    @Query(value = "SELECT m.* from marketing m left join marketing_suits_sku s on m.marketing_id = s.marketing_id " +
            "where m.del_flag = 0 and m.is_pause = 0 and m.marketing_type = 6 and m.begin_time <= now() and m.end_time >= now() " +
            "and s.sku_id = :skuId", nativeQuery = true)
    List<Marketing> getMarketingBySuitsSkuId(@Param("skuId")String skuId);

    /**
     * 根据skuId查询组合购活动（排除已结束）
     * @param skuId
     * @return
     */
    @Query(value = "SELECT m.* from marketing m left join marketing_suits_sku s on m.marketing_id = s.marketing_id " +
            "where m.del_flag = 0 and m.is_pause = 0 and m.marketing_type = 6 and m.end_time > now() " +
            "and s.sku_id = :skuId", nativeQuery = true)
    List<Marketing> getMarketingNotEndBySuitsSkuId(@Param("skuId")String skuId);

    /**
     * 关闭活动
     * @param marketingId
     */
    @Modifying
    @Query("update Marketing set endTime = now() where marketingId = ?1")
    void closeActivity(Long marketingId);

    /**
     * @description 根据店铺ID和促销类型查询时间冲突的促销集合  只用与打包一口价
     * @author malianfeng
     * @date 2021/7/16 16:14
     * @param storeId 店铺ID
     * @param scopeType 促销类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的营销ID
     * @return java.util.List<com.wanmi.sbc.marketing.common.model.root.Marketing>
     **/
    @Query(value = "SELECT m.marketing_id from marketing m " +
            "where m.del_flag = 0 and m.sub_type = 6 and m.store_id = :storeId and m.scope_type = :scopeType " +
            "and not(m.begin_time > :endTime or m.end_time < :startTime) " +
            "and m.audit_status = 1 " +
            "and (:storeId is null or m.store_id = :storeId) and m.plugin_type = :pluginType " +
            "and (:excludeId is null or m.marketing_id <> :excludeId)", nativeQuery = true)
    List<Long> getExistsMarketingByStoreIdAndScopeType(@Param("storeId") Long storeId,
                                                            @Param("scopeType") Integer scopeType,
                                                            @Param("startTime") LocalDateTime startTime,
                                                            @Param("endTime") LocalDateTime endTime,
                                                            @Param("excludeId") Long excludeId,
                                                           @Param("pluginType") Integer pluginType);

    /**
   * boss端创建满返校验店铺
   *
   * @param marketingId
   * @param beginTime
   * @param endTime
   * @return
   */
  @Query(
      value =
          "SELECT count(1) "
              + "FROM marketing "
              + "WHERE del_flag = 0 "
              + "AND NOT ( begin_time > :endTime OR end_time < :beginTime ) "
              + "and marketing_type = 7 "
              + "and marketing_id != :marketingId "
              + "and is_boss = 1 "
              + "and end_time > NOW()",
      nativeQuery = true)
  Integer checkMarketingFullStore(
      @Param("marketingId") Long marketingId,
      @Param("beginTime") LocalDateTime beginTime,
      @Param("endTime") LocalDateTime endTime);

    /**
     * boss端创建满返校验店铺
     * @param beginTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT count(1) " +
            "FROM marketing " +
            "WHERE del_flag = 0 " +
            "AND NOT ( begin_time > :endTime OR end_time < :beginTime ) " +
            "and marketing_type = 7 " +
            "and is_boss = 1",nativeQuery = true)
    Integer checkMarketingFullStore(
            @Param("beginTime") LocalDateTime beginTime,
            @Param("endTime") LocalDateTime endTime);

  /**
   * boss端创建满返校验店铺
   *
   * @param marketingId
   * @param beginTime
   * @param endTime
   * @param storeIds
   * @return
   */
  @Query(
      value =
          "SELECT\n"
              + "\tcount( 1 ) \n"
              + "FROM\n"
              + "\tmarketing m\n"
              + "\tLEFT JOIN marketing_full_return_store mf ON m.marketing_id = mf.marketing_id \n"
              + "WHERE\n"
              + "\tm.del_flag = 0 \n"
              + "\tAND m.is_boss = 1 \n"
              + "\tAND m.marketing_type = 7 \n"
              + "\tAND NOT ( m.begin_time > :endTime OR m.end_time < :beginTime ) \n"
              + "\tAND m.marketing_id != :marketingId \n"
              + "\tAND (\n"
              + "\tm.store_type = 0 \n"
              + "\tOR mf.store_id IN :storeIds)"
              + "\tand m.end_time > NOW()",
      nativeQuery = true)
  Integer checkMarketingFullStore(
      @Param("marketingId") Long marketingId,
      @Param("beginTime") LocalDateTime beginTime,
      @Param("endTime") LocalDateTime endTime,
      @Param("storeIds") List<Long> storeIds);

    /**
     * boss端创建满返校验店铺
     *
     * @param marketingId
     * @param beginTime
     * @param endTime
     * @param storeIds
     * @return
     */
    @Query(
            value =
                    "SELECT\n"
                            + "\tcount( 1 ) \n"
                            + "FROM\n"
                            + "\tmarketing m\n"
                            + "\tLEFT JOIN marketing_full_return_store mf ON m.marketing_id = mf.marketing_id \n"
                            + "WHERE\n"
                            + "\tm.del_flag = 0 \n"
                            + "\tAND m.is_boss = 1 \n"
                            + "\tAND m.marketing_type = 7 \n"
                            + "\tAND NOT ( m.begin_time > :endTime OR m.end_time < :beginTime ) \n"
                            + "\tAND (\n"
                            + "\tm.store_type = 0 \n"
                            + "\tOR mf.store_id IN :storeIds)",nativeQuery = true)
    Integer checkMarketingFullStore(
            @Param("beginTime") LocalDateTime beginTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("storeIds") List<Long> storeIds);
}
