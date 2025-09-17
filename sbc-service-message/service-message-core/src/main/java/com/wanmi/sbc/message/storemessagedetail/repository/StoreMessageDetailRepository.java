package com.wanmi.sbc.message.storemessagedetail.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>商家消息/公告DAO</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Repository
public interface StoreMessageDetailRepository extends JpaRepository<StoreMessageDetail, String>,
        JpaSpecificationExecutor<StoreMessageDetail> {

    /**
     * 单个删除商家消息/公告
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1 and storeId = ?2")
    void deleteById(String id, Long storeId);

    /**
     * 单个已读商家消息/公告
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageDetail set isRead = com.wanmi.sbc.message.bean.enums.ReadFlag.YES where id = ?1 and " +
            "storeId = ?2")
    void readById(String id, Long storeId);

    /**
     * 批量删除节点ID或公告ID的消息
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where joinId = ?1")
    void deleteByJoinId(Long joinId);

    /**
     * 批量删除商家消息/公告
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1 and storeId = ?2")
    void deleteByIdList(List<String> idList, Long storeId);

    /**
     * 查询单个商家消息/公告
     * @author 马连峰
     */
    Optional<StoreMessageDetail> findByIdAndStoreIdAndDelFlag(String id, Long storeId, DeleteFlag delFlag);
}
