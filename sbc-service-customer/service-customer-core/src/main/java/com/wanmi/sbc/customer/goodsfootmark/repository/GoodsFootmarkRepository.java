package com.wanmi.sbc.customer.goodsfootmark.repository;

import com.wanmi.sbc.customer.goodsfootmark.model.root.GoodsFootmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>我的足迹DAO</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Repository
public interface GoodsFootmarkRepository extends JpaRepository<GoodsFootmark, Long>,
        JpaSpecificationExecutor<GoodsFootmark> {

    /**
     * 单个删除我的足迹
     * @author 
     */
    @Modifying
    @Query("delete from GoodsFootmark where footmarkId = ?1 and customerId = ?2")
    void deleteById(Long footmarkId, String customerId);

    /**
     * 批量删除我的足迹
     * @author 
     */
    @Modifying
    @Query("delete from GoodsFootmark  where footmarkId in ?1 and customerId = ?2")
    int deleteByIdList(List<Long> footmarkIdList, String customerId);

    @Modifying
    @Query(value = "insert into goods_footmark (footmark_id,customer_id,goods_info_id,create_time,update_time,del_flag,view_times) values (?1,?2,?3,now(),now(),0,1) \n" +
            "ON DUPLICATE key UPDATE view_times = view_times+1 ,update_time = now()",nativeQuery = true)
    void saveOrUpdate(Long generateNextId, String customerId, String goodsInfoId);


    /**
     * 批量删除我的足迹 失效的数据
     * @author
     */
    @Modifying
    @Query(value = "delete from goods_footmark where update_time <= ?1",nativeQuery = true)
    void deleteByUpdateTime(LocalDate updateTimeEnd);
}
