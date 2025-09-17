package com.wanmi.sbc.message.storenoticescope.repository;

import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>商家公告发送范围DAO</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Repository
public interface StoreNoticeScopeRepository extends JpaRepository<StoreNoticeScope, Long>,
        JpaSpecificationExecutor<StoreNoticeScope> {

    /**
     * 单个删除商家公告发送范围
     * @author 马连峰
     */
    @Modifying
    @Query("delete StoreNoticeScope where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除商家公告ID的发送范围
     * @author 马连峰
     */
    @Modifying
    @Query("delete StoreNoticeScope where noticeId = ?1")
    void deleteByNoticeId(Long noticeId);

    /**
     * 批量删除商家公告发送范围
     * @author 马连峰
     */
    @Modifying
    @Query("delete StoreNoticeScope where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个商家公告发送范围
     * @author 马连峰
     */
    Optional<StoreNoticeScope> findById(Long id);

}
