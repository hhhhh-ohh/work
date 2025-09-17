package com.wanmi.sbc.goods.flashsalegoods.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.flashsalegoods.model.root.FlashSaleGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>抢购商品表DAO</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Repository
public interface FlashSaleGoodsRepository extends JpaRepository<FlashSaleGoods, Long>,
        JpaSpecificationExecutor<FlashSaleGoods> {

    /**
     * 单个删除抢购商品表
     *
     * @author bob
     */
    @Modifying
    @Query("update FlashSaleGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    int modifyDelFlagById(Long id);

    /**
     * 批量删除抢购商品表
     *
     * @author bob
     */
    @Modifying
    @Query("update FlashSaleGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    int deleteByIdList(List<Long> idList);

    /**
     * 批量删除未开始的抢购商品表
     *
     * @author bob
     */
    @Modifying
    @Query("update FlashSaleGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where activityTime in ?1 and activityFullTime > now()")
    int deleteByTimeList(List<String> activityTimeList);

    /**
     * 商品是否在指定时间内
     *
     * @author bob
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsId = :goodsId and activityFullTime <= :begin and " +
            "activityFullTime > :end")
    List<FlashSaleGoods> queryByGoodsIdAndActivityFullTime(@Param("goodsId") String goodsId,
                                                           @Param("begin") LocalDateTime begin,
                                                           @Param("end") LocalDateTime end);

    /**
     * 根据skuId判断商品是否在指定时间内
     * @author bob
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsInfoId = :goodsInfoId and activityFullTime <= :begin and " +
            "activityFullTime > :end")
    List<FlashSaleGoods> queryByGoodsInfoIdAndActivityFullTime(@Param("goodsInfoId") String goodsInfoId,
                                                               @Param("begin") LocalDateTime begin,
                                                               @Param("end") LocalDateTime end);

    /**
     * 根据活动时间分组查询
     *
     * @author yxz
     */
    @Query(value = "SELECT min(s.activity_date) activity_date,min(s.activity_time) activity_time,s.activity_full_time," +
            "count(distinct s.goods_info_id) as goodsNum," +
            "count(distinct s.store_id) as storeNum " +
            "from flash_sale_goods s where if(?1 is NULL,1=1,s.activity_full_time >= ?1) and s.activity_full_time <= ?2 " +
            "and s.del_flag = 0 and if(?3 is null,1=1,s.store_id = ?3) " +
            "group by s.activity_full_time ",
            countQuery = "SELECT count(1) " +
                    "from flash_sale_goods s where if(?1 is NULL,1=1,s.activity_full_time >= ?1) and s.del_flag = 0 " +
                    "and s.activity_full_time <= ?2 and if(?3 is null,1=1,s.store_id = ?3) group by s.activity_full_time",
            nativeQuery = true)
    Page<Object> queryGroupByFullTime(String startTime, String endTime, Long storeId, Pageable pageable);

    /**
     * 根据活动时间分组查询
     *
     * @author yxz
     */
    @Query(value = "SELECT min(s.activity_date) activity_date,min(s.activity_time) activity_time,s.activity_full_time," +
            "count(distinct s.goods_info_id) as goodsNum," +
            "count(distinct s.store_id) as storeNum " +
            "from flash_sale_goods s  left join goods_info g on s.goods_info_id = g. goods_info_id where if(?1 is " +
            "NULL,1=1,s" +
            ".activity_full_time >= ?1)" +
            " and s" +
            ".activity_full_time <=" +
            " ?2 " +
            "and s.del_flag = 0 and if(?3 is null,1=1,s.store_id = ?3)  and g.del_flag = 0 and g" +
            ".audit_status = 1 and (g.vendibility = 1 or g.vendibility is null) and (g.provider_status = 1 or g" +
            ".provider_status is null)" +
            "group by s.activity_full_time", nativeQuery = true)
    List<Object> queryGroupByFullTime(String startTime, String endTime, Long storeId);

    /**
     * 根据秒杀抢购商品ID减库存
     *
     * @param stock 库存数
     * @param id    秒杀抢购商品ID
     */
    @Modifying
    @Query("update FlashSaleGoods f set f.stock = f.stock - ?1, f.updateTime = now() where f.id = ?2 and f.stock  >= ?1")
    int subStockById(Integer stock, Long id);

    /**
     * 根据秒杀抢购商品ID加库存
     *
     * @param stock 库存数
     * @param id    秒杀抢购商品ID
     */
    @Modifying
    @Query("update FlashSaleGoods f set f.stock = f.stock + ?1, f.updateTime = now() where f.id = ?2")
    int addStockById(Integer stock, Long id);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 增加抢购商品销量
     * @Date 9:41 2019/6/22
     * @Param [salesVolume, id]
     **/
    @Modifying
    @Query("update FlashSaleGoods f set f.salesVolume = f.salesVolume + ?1, f.updateTime = now() where f.id = ?2")
    int plusSalesVolumeById(Long salesVolume, Long id);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 减去抢购商品销量
     * @Date 9:41 2019/6/22
     * @Param [salesVolume, id]
     **/
    @Modifying
    @Query("update FlashSaleGoods f set f.salesVolume = f.salesVolume - ?1, f.updateTime = now() where f.id = ?2")
    int subSalesVolumeById(Long salesVolume, Long id);

    /**
     * @param activityTime
     * @return
     */
    @Query(value = "select activity_time, goods_id " +
            "from flash_sale_goods " +
            "where del_flag = 0 and activity_time = :activityTime and " +
            "date_format(adddate(activity_full_time, interval 2 hour), '%Y-%m-%d %H:%i:%S') > " +
            "date_format(now(), '%Y-%m-%d %H:%i:%S') limit 0, 1", nativeQuery = true)
    Object queryByActivityTime(@Param("activityTime") String activityTime);

    @Query(value = "SELECT COUNT(DISTINCT store_id) FROM flash_sale_goods WHERE del_flag = 0", nativeQuery = true)
    long storeCount();

    /**
     * 返回简单的对象类
     * @param ids
     * @return
     */
    @Query(value = " select id as id," +
            "    activity_date as activityDate," +
            "    activity_time as activityTime," +
            "    goods_info_id as goodsInfoId," +
            "    goods_id as goodsId," +
            "    price as price," +
            "    stock as stock," +
            "    sales_volume as salesVolume," +
            "    cate_id as cateId," +
            "    max_num as maxNum," +
            "    min_num as minNum," +
            "    store_id as storeId," +
            "    postage as postage," +
            "    del_flag as delFlag," +
            "    activity_full_time as activityFullTime," +
            "    start_time as startTime," +
            "    end_time as endTime" +
            " from flash_sale_goods where id in (:ids) and del_flag=0",nativeQuery = true)
    List<Map<String,Object>> querySimpleByIds(@Param("ids")List<Long> ids);

    FlashSaleGoods findByIdAndDelFlag(Long id, DeleteFlag deleteFlag);

    /**
     * 更新秒杀商品分类为null
     * @param id
     * @param no
     */
    @Modifying
    @Query("update FlashSaleGoods set cateId = null where cateId = ?1 and delFlag = ?2")
    void updateCateId(Long id, DeleteFlag no);

    /**
     * 根据skuId判断商品是否在指定时间内
     * @author bob
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsInfoId in :goodsInfoId and activityFullTime <= :begin and " +
            "activityFullTime > :end")
    List<FlashSaleGoods> queryByGoodsInfoIdInAndActivityFullTime(@Param("goodsInfoId") List<String> goodsInfoIdList,
                                                               @Param("begin") LocalDateTime begin,
                                                               @Param("end") LocalDateTime end);

    /**
     * 查询限时购活动
     * @author xufeng
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    List<FlashSaleGoods> findByActivityId(@Param("activityId") String activityId);

    /**
     * 删除限时抢购活动
     *
     * @author xufeng
     */
    @Modifying
    @Query("update FlashSaleGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    int deleteByActivityId(@Param("activityId") String activityId);

    /**
     * 限时购启动暂停活动
     *
     * @author xufeng
     */
    @Modifying
    @Query("update FlashSaleGoods set status = :status where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    int modifyByActivityId(@Param("status") int status, @Param("activityId") String activityId);

    /**
     * 根据活动id查询商品数量
     * @param activityId
     * @return
     */
    @Query("select count(c.activityId) from FlashSaleGoods c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.activityId = :activityId ")
    int findCountByActivityId(@Param("activityId") String activityId);

    /**
     * 根据spuId判断商品是否在指定时间内
     * @author xufeng
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsId = :goodsId and startTime <= now() and endTime > now()")
    List<FlashSaleGoods> queryInProgressByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 根据spuId判断商品是否在指定时间内
     * @author xufeng
     * @param skuIds
     */
    @Query("from FlashSaleGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsInfoId in :skuIds")
    List<FlashSaleGoods> queryBySkuIds(@Param("skuIds") List<String> skuIds);


    /**
     * 根据交集时间分页查询交集活动
     * @param crossStartTime 交集开始时间
     * @param crossEndTime 交集结束时间
     * @return 活动部分数据
     */
    @Query(value = "select id, goods_id, goods_info_id, type from flash_sale_goods where del_flag = 0 and store_id = :storeId " +
                    "and ((activity_full_time <= date_format(:crossEndTime, '%Y-%m-%d %H:%i:%S') and adddate(activity_full_time, interval 2 hour) >= date_format(:crossStartTime, '%Y-%m-%d %H:%i:%S')) " +
                    "or (start_time <= date_format(:crossEndTime, '%Y-%m-%d %H:%i:%S') and end_time >= date_format(:crossStartTime, '%Y-%m-%d %H:%i:%S'))) " +
                    "and (:notId is null or activity_id != :notId) order by id asc",
            countQuery = "select count(id) from flash_sale_goods where del_flag = 0 and store_id = :storeId " +
                    "and ((activity_full_time <= date_format(:crossEndTime, '%Y-%m-%d %H:%i:%S') and adddate(activity_full_time, interval 2 hour) >= adddate(:crossStartTime, '%Y-%m-%d %H:%i:%S')) " +
                    "or (start_time <= date_format(:crossEndTime, '%Y-%m-%d %H:%i:%S') and end_time >= date_format(:crossStartTime, '%Y-%m-%d %H:%i:%S'))) " +
                    "and (:notId is null or activity_id != :notId)"
            , nativeQuery = true)
    Page<Object[]> pageByCrossTime(@Param("crossStartTime") String crossStartTime,
                                             @Param("crossEndTime") String crossEndTime,
                                             @Param("notId") String notId, @Param("storeId") Long storeId, Pageable pageable);
}
