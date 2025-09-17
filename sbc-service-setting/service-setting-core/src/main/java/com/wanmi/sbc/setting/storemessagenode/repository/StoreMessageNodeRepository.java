package com.wanmi.sbc.setting.storemessagenode.repository;

import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>商家消息节点DAO</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Repository
public interface StoreMessageNodeRepository extends JpaRepository<StoreMessageNode, Long>,
        JpaSpecificationExecutor<StoreMessageNode> {

    /**
     * 单个删除商家消息节点
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageNode set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除商家消息节点
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageNode set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个商家消息节点
     * @author 马连峰
     */
    Optional<StoreMessageNode> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
