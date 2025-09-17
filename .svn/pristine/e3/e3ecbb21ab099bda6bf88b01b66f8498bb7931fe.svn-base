package com.wanmi.sbc.marketing.bargaingoods.repository;

import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>砍价商品DAO</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Repository
public interface BargainGoodsRepository extends JpaRepository<BargainGoods, Long>,
        JpaSpecificationExecutor<BargainGoods> {

    /**
     * 根据主键id和店铺id查找砍价商品
     * @param bargainGoodsId 主键id
     * @param storeId 店铺id
     * @return
     */
    @Query("from BargainGoods bg where bg.bargainGoodsId = :bargainGoodsId and (:storeId is null or bg.storeId = :storeId) and bg.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    BargainGoods findByIdAndStoreId(Long bargainGoodsId, Long storeId);

    /**
     * 单个删除砍价商品
     *
     * @author
     */
    @Modifying
    @Query("update BargainGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where bargainGoodsId = ?1")
    void deleteById(Long bargainGoodsId);

    /**
     * 批量删除砍价商品
     *
     * @author
     */
    @Modifying
    @Query("update BargainGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where bargainGoodsId in ?1")
    int deleteByIdList(List<Long> bargainGoodsIdList);

    @Override
    Page<BargainGoods> findAll(Specification<BargainGoods> specification, Pageable pageable);

    @Override
    List<BargainGoods> findAll(Specification<BargainGoods> spec, Sort sort);

    @Override
    List<BargainGoods> findAll(Specification<BargainGoods> spec);

    @Query("select count(*) from BargainGoods where bargainGoodsId = ?1 and endTime > now()")
    int countByBargainGoodsIdAndEndTime(Long bargainGoodsId);

    @Query(value = "select goods_info_id from bargain_goods where not (begin_time > ?2 or end_time < ?1 ) and stoped=0 and goods_info_id in ?3 and del_flag=0", nativeQuery = true)
    List<String> findBargainGoodsInActivityTime(LocalDateTime beginTime, LocalDateTime endTime, List<String> goodsInfoIds);

    @Query(value = "select * from bargain_goods where bargain_goods_id in ?1 and del_flag=0",nativeQuery = true)
    List<BargainGoods> findBargainGoodsByIds(List<Long> bargainGoodsIds);

    @Modifying
    @Query(value = "update bargain_goods set audit_status = ?2, reason_for_rejection= ?3, update_time = now() where bargain_goods_id in ?1 ", nativeQuery = true)
    int checkStauts(List<Long> bargainGoodsIds, int auditStatus, String reasonForRejection);

    @Modifying
    @Query("update BargainGoods set leaveStock = leaveStock + ?2 where bargainGoodsId = ?1")
    int addStock(Long bargainGoodsId, Long stock);

    @Modifying
    @Query("update BargainGoods set leaveStock = leaveStock - ?2 where bargainGoodsId = ?1 and leaveStock >= ?2")
    int subStock(Long bargainGoodsId, Long stock);

    /**
     * 追加指定库存（独立库存和剩余库存）
     * @param id 主键ID
     * @param addBargainStock 追加的库存
     */
    @Modifying
    @Query("update BargainGoods set leaveStock = leaveStock + ?2, bargainStock = leaveStock, updateTime = now() where bargainGoodsId = ?1")
    int addBargainStockAndLeaveStockById(Long id, Long addBargainStock);

    @Modifying
    @Query(value = "update bargain_goods set stoped = 1, update_time = now() where bargain_goods_id = ?1 ", nativeQuery = true)
    void terminalActivity(Long bargainGoodsId);

    @Modifying
    @Query(value = "update bargain_goods set stoped = 1, update_time = now() where store_id = ?1 ", nativeQuery = true)
    void storeTerminalActivity(Long storeId);

    @Modifying
    @Query(value = "update bargain_goods set del_flag = 1, update_time = now() where bargain_goods_id = ?1 ", nativeQuery = true)
    void deleteBargainGoodsById(Long bargainGoodsId);


    @Modifying
    @Query(value = "update bargain_goods set goods_status = ?2, update_time = now() where goods_info_id in ?1 and end_time > now() ", nativeQuery = true)
    void updateGoodsStatus(List<String> goodsInfIdList, int deleteFlag);

    @Modifying
    @Query(value = "update bargain_goods set goods_info_name = ?2, goods_info_no = ?3, goods_cate_id = ?4, goods_status = ?5, update_time = now() where goods_info_id = ?1 and end_time > now() ", nativeQuery = true)
    void updateGoodsInfo(String goodsInfId, String goodsInfoName, String goodsInfoNo, Long goodsCateId, int goodsStatus);

    @Modifying
    @Query(value = "update bargain_goods set audit_status = ?1, reason_for_rejection = ?2, update_time = now() " +
            "where now() > end_time and audit_status = 0 and (store_id is null or store_id = ?3)", nativeQuery = true)
    void autoBatchAuditForOverTime(Integer auditStatus, String reasonForRejection, Long storeId);
}
