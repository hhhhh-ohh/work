package com.wanmi.sbc.goods.distributor.goods.repository;

import com.wanmi.sbc.goods.distributor.goods.model.root.DistributorGoodsInfo;
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

@Repository
public interface DistributiorGoodsInfoRepository extends JpaRepository<DistributorGoodsInfo, String>, JpaSpecificationExecutor<DistributorGoodsInfo> {

    /**
     * 根据分销员-会员ID查询分销员商品列表
     *
     * @param customerId
     * @return
     */
    List<DistributorGoodsInfo> findByCustomerIdOrderBySequence(String customerId);

    /**
     * 根据分销员-会员ID查询分销员商品列表
     *
     * @param customerId
     * @return
     */
    @Query("SELECT dis FROM DistributorGoodsInfo dis,GoodsInfo info WHERE dis.goodsInfoId = info.goodsInfoId " +
            "AND dis.customerId = :customerId " +
            "AND info.stock > :stock ")
    List<DistributorGoodsInfo> findByCustomerIdAndStock(@Param("customerId") String customerId, @Param("stock") Long stock);

    List<DistributorGoodsInfo> findByCustomerIdAndStoreIdOrderBySequence(String customerId, Long storeId);

    /**
     * 根据分销员-会员ID查询分销员商品列表
     *
     * @param customerId
     * @return
     */
    @Query("SELECT dis FROM DistributorGoodsInfo dis,GoodsInfo info WHERE dis.goodsInfoId = info.goodsInfoId " +
            "AND dis.customerId = :customerId " +
            "AND dis.storeId = :storeId " +
            "AND info.stock > :stock ")
    List<DistributorGoodsInfo> findByCustomerIdAndStoreIdOrderAndStock(@Param("customerId") String customerId,
                                                                       @Param("storeId") Long storeId,
                                                                       @Param("stock") Long stock);

    /**
     * 根据分销员-会员ID和SkuId删除分销员商品信息
     *
     * @param customerId
     * @param goodsInfoId
     * @return
     */
    int deleteByCustomerIdAndGoodsInfoId(String customerId, String goodsInfoId);
    

    @Modifying
    @Query("update DistributorGoodsInfo dg set dg.status = ?1 where dg.customerId = ?2  and dg.goodsInfoId = ?3")
    void updateStatusByCustomerIdAndGoodsInfoId(Integer status, String customerId, String goodsInfoId);

    /**
     * 根据分销员-会员ID和SPU编号查询分销员商品列表
     *
     * @param customerId 会员ID
     * @param goodsId    SPU编号
     * @return
     */
    List<DistributorGoodsInfo> findByCustomerIdAndGoodsId(String customerId, String goodsId);

    /**
     * 根据分销员-会员ID获取管理的分销商品排序最大值
     *
     * @param customerId
     * @return
     */
    @Query(nativeQuery = true, value = "select max(d.sequence) from distributor_goods_info d where d.customer_id = ?1 ")
    Integer findMaxSequenceByCustomerId(String customerId);

    /**
     * 根据分销员-会员ID查询分销员商品列表（分页接口）
     *
     * @param customerId
     * @param status
     * @param pageable
     * @return
     */
    Page<DistributorGoodsInfo> findByCustomerIdAndStatusOrderBySequence(String customerId, Integer status, Pageable pageable);


    /**
     * 根据分销员-会员ID查询分销员商品列表（分页接口）
     *
     * @param customerId
     * @return
     */
    @Query("SELECT dis FROM DistributorGoodsInfo dis,GoodsInfo info WHERE dis.goodsInfoId = info.goodsInfoId " +
            "AND dis.customerId = :customerId " +
            "AND dis.status = :status " +
            "AND info.stock > :stock  " +
            "ORDER BY dis.sequence")
    Page<DistributorGoodsInfo> findByCustomerIdAndStatusOrderAndStock(@Param("customerId") String customerId,
                                                                       @Param("status") Integer status,
                                                                       @Param("stock") Long stock, Pageable pageable);

    /**
     * 根据分销员-会员ID和skuID查询分销员商品信息
     *
     * @param customerId
     * @param goodsInfoId
     * @return
     */
    DistributorGoodsInfo findByCustomerIdAndGoodsInfoId(String customerId, String goodsInfoId);

    /**
     * 商家-社交分销开关，更新对应的分销员商品状态
     *
     * @param storeId 店铺ID
     * @param status
     * @return
     */
    @Modifying
    @Query("update DistributorGoodsInfo dg set dg.status = :status,dg.updateTime = :updateTime where dg.storeId = :storeId")
    int modifyByStoreIdAndStatus(@Param("storeId") Long storeId, @Param("status") Integer status, @Param("updateTime") LocalDateTime updateTime);


    /**
     *
     *
     * @param storeId 店铺ID
     * @param status
     * @return
     */
    @Modifying
    @Query("update DistributorGoodsInfo dg set dg.status = :status,dg.updateTime = :updateTime where dg.storeId = :storeId and dg.customerId = :customerId and dg.status = 2")
    int modifyByStoreIdAndStatusForLaKaLA(@Param("storeId") Long storeId, @Param("status") Integer status, @Param("updateTime") LocalDateTime updateTime, @Param("customerId")String customerId);

    /**
     * 根据SkuId删除分销员商品信息
     *
     * @param goodsInfoId
     * @return
     */
    int deleteByGoodsInfoId(String goodsInfoId);

    /**
     * 根据SpuId删除分销员商品信息
     *
     * @param goodsId
     * @return
     */
    int deleteByGoodsId(String goodsId);

    /**
     * 根据店铺ID集合删除分销员商品表数据
     *
     * @param storeId
     * @return
     */
    int deleteByStoreId(Long storeId);

    /**
     * 根据店铺ID集合批量删除分销员商品表数据
     *
     * @param storeIds
     * @return
     */
    @Modifying
    @Query("delete from DistributorGoodsInfo dg where dg.storeId in ?1")
    int deleteByStoreIdsIn(List<Long> storeIds);

    /**
     * 查询分销员商品表-店铺ID集合数据
     *
     * @return
     */
    @Query(value = "select distinct(d.storeId) as storeId from DistributorGoodsInfo d ")
    List<Long> findAllStoreId();

    /**
     * 根据会员id查询这个店铺下的分销商品数
     *
     * @param customerId
     * @return
     */
    @Query("select count(1) from DistributorGoodsInfo where status=0 and customerId = ?1")
    Long getCountsByCustomerId(String customerId);

    /**
     * 销量排序。前10分销商品
     *
     * @return
     */
    @Query(value = "SELECT d.goods_id FROM " +
            "( SELECT c.* FROM ( SELECT DISTINCT ( a.goods_id ) FROM distributor_goods_info a ) b LEFT JOIN goods c" +
            " ON c.goods_id = b.goods_id ) d " +
            "ORDER BY goods_sales_num DESC LIMIT 10", nativeQuery = true)
    List<String> salesNumDescLimit10();

    /**
     * 分组查询分销员id跟店铺ID
     * @param limitOne
     * @param limitTwo
     * @return
     */
    @Query(value = "select concat(d.customer_id,'-',d.store_id) as concat  from distributor_goods_info d group by d.customer_id,d.store_id limit ?1,?2",nativeQuery = true)
    List<String> findGroupByCustomerAndStoreId(Integer limitOne,Integer limitTwo);


    /**
     * 拉卡拉刷新分销员商品状态
     * @param storeId
     * @param customerId
     */
    @Modifying
    @Query(value = "update distributor_goods_info set status = 2 where store_id= ?1 and customer_id = ?2 and status = 0",nativeQuery = true)
    void updateStatusForLaKaLa(Long storeId,String customerId);


    /**
     * 拉卡拉刷新分销员商品状态
     */
    @Modifying
    @Query(value = "update distributor_goods_info set status = 0 where status= 2",nativeQuery = true)
    void updateStatusForLaKaLa();
}
