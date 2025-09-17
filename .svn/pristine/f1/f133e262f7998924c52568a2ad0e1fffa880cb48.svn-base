package com.wanmi.sbc.setting.storemessagenodesetting.repository;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingModifyStatusRequest;
import com.wanmi.sbc.setting.storemessagenodesetting.model.root.StoreMessageNodeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>商家消息节点设置DAO</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Repository
public interface StoreMessageNodeSettingRepository extends JpaRepository<StoreMessageNodeSetting, Long>,
        JpaSpecificationExecutor<StoreMessageNodeSetting> {

    /**
     * 单个删除商家消息节点设置
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageNodeSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除商家消息节点设置
     * @author 马连峰
     */
    @Modifying
    @Query("update StoreMessageNodeSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个商家消息节点设置
     * @author 修改商家消息节点开关
     */
    @Modifying
    @Query("update StoreMessageNodeSetting set status = :#{#modifyStatusRequest.getStatus()}, updatePerson = :#{#modifyStatusRequest.getUpdatePerson()}, warningStock = :#{#modifyStatusRequest.getWarningStock()},updateTime = now() where storeId = :#{#modifyStatusRequest.getStoreId()} and nodeCode = :#{#modifyStatusRequest.getNodeCode()}")
    int modifyStatus(StoreMessageNodeSettingModifyStatusRequest modifyStatusRequest);

    /**
     * 查询单个商家消息节点设置
     * @author 马连峰
     */
    Optional<StoreMessageNodeSetting> findByIdAndStoreIdAndDelFlag(Long id, Long storeId, DeleteFlag delFlag);

    /**
     * 查询单个商家sku库存预警值
     */
    @Query("from StoreMessageNodeSetting where storeId = ?1 and nodeCode = ?2 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    StoreMessageNodeSetting getWarningStock (Long storeId,String nodeCode);

    /**
     * 查询单个商家库存预警的开关状态
     */
    @Query("from StoreMessageNodeSetting where storeId = ?1 and nodeCode = ?2 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    StoreMessageNodeSetting getStatus (Long storeId, String nodeCode);
}
