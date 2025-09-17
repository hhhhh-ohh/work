package com.wanmi.sbc.customer.ledgerreceiverrel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>分账绑定关系DAO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Repository
public interface LedgerReceiverRelRepository extends JpaRepository<LedgerReceiverRel, String>,
        JpaSpecificationExecutor<LedgerReceiverRel> {

    /**
     * 单个删除分账绑定关系
     * @author 许云鹏
     */
    @Override
    @Modifying
    @Query("update LedgerReceiverRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除分账绑定关系
     * @author 许云鹏
     */
    @Modifying
    @Query("update LedgerReceiverRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<String> idList);

    /**
     * 查询单个分账绑定关系
     * @author 许云鹏
     */
    Optional<LedgerReceiverRel> findByIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 根据商户id查询未删除的绑定信息
     * @param supplierId
     * @param receiverId
     * @param deleteFlag
     * @return
     */
    LedgerReceiverRel findBySupplierIdAndReceiverIdAndDelFlag(Long supplierId, String receiverId, DeleteFlag deleteFlag);


    /**
     * 查询单个分账绑定关系
     * @author 许云鹏
     */
    Optional<LedgerReceiverRel> findByApplyId(String applyId);


    /**
     * 修改状态
     * @author 许云鹏
     */
    @Modifying
    @Query("update LedgerReceiverRel set bindState = ?1, rejectReason = ?2, bindTime = ?3 where id = ?4")
    void updateBindState(Integer bindState, String rejectReason, LocalDateTime bindTime, String id);

    /**
     * 根据接收方ids查询已存在的数据
     * @param receiverIds
     * @return
     */
    @Query("select receiverId from LedgerReceiverRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and receiverId in ?1 and supplierId = ?2")
    List<String> filterByReceiverId(List<String> receiverIds, Long supplierId);

    /**
     * 根据接收方ids查询已存在的数据
     * @param supplierIds
     * @return
     */
    @Query("select distinct supplierId from LedgerReceiverRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and supplierId in ?1 and receiverId = ?2")
    List<Long> filterBySupplierId(List<Long> supplierIds, String receiverId);

    /**
     * 根据商户id和接收方id查询
     * @param supplierId
     * @param receiverId
     * @return
     */
    @Query("from LedgerReceiverRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and supplierId = ?1 and receiverId = ?2")
    LedgerReceiverRel findRelByBusinessId(Long supplierId, String receiverId);

    /**
     * 根据接收方id查询数量
     * @param receiverId
     * @return
     */
    @Query("select count(id) from LedgerReceiverRel where receiverId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and bindState in (1,2)")
    long countReceiver(String receiverId);

    /**
     * 修改code
     * @param account
     * @param receiverId
     */
    @Modifying
    @Query("update LedgerReceiverRel set receiverAccount = ?1 where receiverId = ?2")
    void updateAccount(String account, String receiverId);

    /**
     * 修改名称
     * @param name
     * @param receiverId
     */
    @Modifying
    @Query("update LedgerReceiverRel set receiverName = ?1 where receiverId = ?2")
    void updateName(String name, String receiverId);

    /**
     * 根据商户id和接收方ids查询
     * @param supplierId
     * @param receiverIds
     * @return
     */
    @Query("select receiverId from LedgerReceiverRel where supplierId = ?1 and receiverId in (?2) and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and bindState = 2")
    List<String> findRelList(Long supplierId, List<String> receiverIds);

    /**
     * 根据商户ids和接收方id查询
     * @param receiverId
     * @param supplierIds
     * @return
     */
    @Query("select supplierId from LedgerReceiverRel where receiverId = ?1 and supplierId in (?2) and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and bindState = 2")
    List<Long> findDistributionRelList(String receiverId, List<Long> supplierIds);

    /**
     * 查询接收方已绑定的商户id
     * @param receiverId
     * @return
     */
    @Query("select supplierId from LedgerReceiverRel where receiverId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and bindState = 2")
    List<Long> findSupplierIdByReceiverId(String receiverId);


    @Query(value = "select apply_id from ledger_receiver_rel where bind_state != 2 and apply_id is not null",nativeQuery = true)
    List<String> findApplyIdForCallBack();

    /**
     * 根据id和supplierId查询
     * @param id
     * @param deleteFlag
     * @param supplierId
     * @return
     */
    LedgerReceiverRel findByIdAndDelFlagAndSupplierId(String id, DeleteFlag deleteFlag, Long supplierId);

    /**
     * @description 电子合同EC003申请ID
     * @author  edz
     * @date: 2022/9/13 15:26
     * @param ecApply
     * @return com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel
     */
    LedgerReceiverRel findByEcApplyId(String ecApply);

    /**
     * @description 更新合同EC003相关信息
     * @author  edz
     * @date: 2022/9/13 17:03
     * @param ecContentId
     * @param ecNO
     * @param ecUrl
     * @param ecApplyId
     * @return int
     */
    @Modifying
    @Query(value = "update LedgerReceiverRel set ecContentId = :ecContentId, ecNo = :ecNO, ecUrl = :ecUrl where " +
            "ecApplyId = :ecApplyId")
    int updateEC003Info(@Param("ecContentId") String ecContentId, @Param("ecNO") String ecNO,
                        @Param("ecUrl") String ecUrl, @Param("ecApplyId") String ecApplyId);

    @Modifying
    @Query(value = "update LedgerReceiverRel set ecContentId = :ecContentId, ecNo = :ecNO where " +
            "ecApplyId = :ecApplyId")
    int updateEC003Info(@Param("ecContentId") String ecContentId, @Param("ecNO") String ecNO,
                        @Param("ecApplyId") String ecApplyId);

    List<LedgerReceiverRel> findBySupplierIdInAndReceiverIdAndDelFlag(List<Long> supplierId, String receiverId, DeleteFlag deleteFlag);
}
