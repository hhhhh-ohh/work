package com.wanmi.sbc.message.storenoticesend.repository;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.storenoticesend.model.root.StoreNoticeSend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>商家公告DAO</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Repository
public interface StoreNoticeSendRepository extends JpaRepository<StoreNoticeSend, Long>,
        JpaSpecificationExecutor<StoreNoticeSend> {

    /**
     * 单个删除商家公告
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreNoticeSend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 查询单个商家公告
     * @author 马连峰
     */
    Optional<StoreNoticeSend> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 更改公告状态
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreNoticeSend set sendStatus = ?2 where id = ?1")
    void modifySendStatusById(Long id, StoreNoticeSendStatus sendStatus);

    /**
     * 更改公告扫描标识
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreNoticeSend set scanFlag = ?2 where id in ?1")
    void modifyScanFlag(List<Long> noticeIds, BoolFlag scanFlag);
}
