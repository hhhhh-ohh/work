package com.wanmi.sbc.goods.livegoods.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.livegoods.LiveGoodsQueryRequest;
import com.wanmi.sbc.goods.livegoods.model.root.LiveGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * <p>直播商品DAO</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Repository
public interface LiveGoodsRepository extends JpaRepository<LiveGoods, Long>,
        JpaSpecificationExecutor<LiveGoods> {



    /**
     * 单个删除直播商品
     * @author zwb
     */
    @Modifying
    @Query("update LiveGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除直播商品
     * @author zwb
     */
    @Modifying
    @Query("update LiveGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> goodsIdList);


    Optional<LiveGoods> findByGoodsIdAndDelFlag(Long id,DeleteFlag delFlag);


    /**
     * 单个修改直播商品
     * @author zwb
     */
    @Modifying
    @Query("update LiveGoods set auditStatus = ?1 where goodsId in ?2")
    int updateByGoodsIdList(Integer auditStatus ,List<Long> goodsIdList);

    /**
     * 单个修改直播商品goodsId
     * @author zwb
     */
    @Modifying
    @Query("update LiveGoods set goodsId = ?1,auditStatus=?2 where id = ?3")
    int updateGoodsIdAndAuditStatusById(Long goodsIdNew,Integer auditStatus, Long id);

    /**
     * 单个修改直播商品goodsId
     * @author zwb
     */
    @Query("SELECT  l from LiveGoods l where l.goodsId in ?1 and l.delFlag= ?2")
    List<LiveGoods> findByGoodsIdList(List<Long> goodsIdList, DeleteFlag no);
    /**
     * 修改状态
     * @author zwb
     */
    @Modifying
    @Query("update LiveGoods set auditStatus = ?1,auditReason=?2 where id = ?3")
    void updateAuditStatusById(Integer auditStatus, String auditReason, Long id);

    /**
     * 根据goodsInfoId分页查询
     * @param goodsInfoIdList
     * @param deleteFlag
     * @return
     */
    Page<LiveGoods> findByGoodsInfoIdInAndDelFlag(List<String> goodsInfoIdList, DeleteFlag deleteFlag, Pageable pageable);


    /**
     * 分页查询小程序直播
     * @param request
     * @param size
     * @param pageable
     * @return
     */
    @Query(value = "SELECT " +
            "live_goods.name AS `name`, " +
            "live_goods.id AS id, " +
            "live_goods.goods_info_id AS goods_info_id, " +
            "live_goods.audit_status AS audit_status, " +
            "live_goods.price AS price, " +
            "live_goods.cover_img_url AS cover_img_url, " +
            "live_goods.url AS url, " +
            "live_goods.store_id AS store_id, " +
            "live_goods.price_type AS price_type, " +
            "live_goods.price2 AS price2, " +
            "live_goods.create_time AS create_time, " +
            "live_goods.create_person AS create_person, " +
            "live_goods.submit_time AS submit_time, " +
            "live_goods.update_time AS update_time, " +
            "live_goods.audit_id AS audit_id, " +
            "live_goods.audit_reason AS audit_reason, " +
            "live_goods.delete_time AS delete_time, " +
            "live_goods.delete_person AS delete_person, " +
            "live_goods.del_flag AS del_flag, " +
            "live_goods.third_party_tag AS third_party_tag, " +
            "live_goods.update_person AS update_person, " +
            "live_goods.goods_id AS goods_id, " +
            "live_goods.goods_type AS goods_type, " +
            "goods_info.stock AS stock " +
            "FROM " +
            "live_goods " +
            "JOIN goods_info ON live_goods.goods_info_id = goods_info.goods_info_id  " +
            "AND live_goods.del_flag = '0' " +
            "JOIN goods ON goods_info.goods_id = goods.goods_id  " +
            "AND goods_info.del_flag = '0'  " +
            "WHERE " +
            "IF(:#{#request.name} IS NULL, 1 = 1, live_goods.name LIKE CONCAT('%',:#{#request.name},'%') )  " +
            "AND " +
            "IF(:#{#request.goodsType} IS NULL, 1 = 1, live_goods.goods_type = :#{#request.goodsType})  " +
            "AND " +
            "IF(:#{#request.storeId} IS NULL, 1 = 1, live_goods.store_id = :#{#request.storeId})  " +
            "AND " +
            "IF(:#{#request.auditStatus} IS NULL, 1 = 1, live_goods.audit_status = :#{#request.auditStatus})  " +
            "AND " +
            "IF(:#{#request.minMarketPrice} IS NULL, 1 = 1, live_goods.price >= :#{#request.minMarketPrice})  " +
            "AND " +
            "IF(:#{#request.maxMarketPrice} IS NULL, 1 = 1, live_goods.price <= :#{#request.maxMarketPrice})  " +
            "AND " +
            "IF(:#{#request.minStock} IS NULL, 1 = 1, goods_info.stock >= :#{#request.minStock}) " +
            "AND " +
            "IF(:#{#request.maxStock} IS NULL, 1 = 1, goods_info.stock <= :#{#request.maxStock})  " +
            "AND " +
            "IF(:#{#request.minGoodsSalesNum} IS NULL, 1 = 1, goods.goods_sales_num >= :#{#request.minGoodsSalesNum} )  " +
            "AND " +
            "IF(:#{#request.maxGoodsSalesNum} IS NULL, 1 = 1, goods.goods_sales_num <= :#{#request.maxGoodsSalesNum} )  " +
            "AND " +
            "IF(:storeSize = 0, 1 = 1, live_goods.store_id in (:#{#request.storeIdList}))  " +
            "ORDER BY live_goods.create_time DESC ",
            countQuery = "SELECT " +
                    "COUNT(live_goods.id)  " +
                    "FROM " +
                    "live_goods " +
                    "JOIN goods_info ON live_goods.goods_info_id = goods_info.goods_info_id  " +
                    "AND live_goods.del_flag = '0' " +
                    "JOIN goods ON goods_info.goods_id = goods.goods_id  " +
                    "AND goods_info.del_flag = '0'  " +
                    "WHERE " +
                    "IF(:#{#request.name} IS NULL, 1 = 1, live_goods.name LIKE CONCAT('%',:#{#request.name},'%') )  " +
                    "AND " +
                    "IF(:#{#request.goodsType} IS NULL, 1 = 1, live_goods.goods_type = :#{#request.goodsType})  " +
                    "AND " +
                    "IF(:#{#request.storeId} IS NULL, 1 = 1, live_goods.store_id = :#{#request.storeId})  " +
                    "AND " +
                    "IF(:#{#request.auditStatus} IS NULL, 1 = 1, live_goods.audit_status = :#{#request.auditStatus})  " +
                    "AND " +
                    "IF(:#{#request.minMarketPrice} IS NULL, 1 = 1, live_goods.price >= :#{#request.minMarketPrice})  " +
                    "AND " +
                    "IF(:#{#request.maxMarketPrice} IS NULL, 1 = 1, live_goods.price <= :#{#request.maxMarketPrice})  " +
                    "AND " +
                    "IF(:#{#request.minStock} IS NULL, 1 = 1, goods_info.stock >= :#{#request.minStock}) " +
                    "AND " +
                    "IF(:#{#request.maxStock} IS NULL, 1 = 1, goods_info.stock <= :#{#request.maxStock})  " +
                    "AND " +
                    "IF(:#{#request.minGoodsSalesNum} IS NULL, 1 = 1, goods.goods_sales_num >= :#{#request.minGoodsSalesNum} )  " +
                    "AND " +
                    "IF(:#{#request.maxGoodsSalesNum} IS NULL, 1 = 1, goods.goods_sales_num <= :#{#request.maxGoodsSalesNum} )  " +
                    "AND " +
                    "IF(:storeSize = 0, 1 = 1, live_goods.store_id in (:#{#request.storeIdList}))  " ,nativeQuery = true)
    Page<LiveGoods> findLiveGoodsPage(@Param("request") LiveGoodsQueryRequest request,@Param("storeSize") int size,Pageable pageable);
}
