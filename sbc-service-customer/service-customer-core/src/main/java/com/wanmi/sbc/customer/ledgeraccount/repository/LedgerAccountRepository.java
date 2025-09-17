package com.wanmi.sbc.customer.ledgeraccount.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>清分账户DAO</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, String>,
        JpaSpecificationExecutor<LedgerAccount> {

    /**
     * 单个删除清分账户
     * @author 许云鹏
     */
    @Override
    @Modifying
    @Query("update LedgerAccount set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除清分账户
     * @author 许云鹏
     */
    @Modifying
    @Query("update LedgerAccount set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<String> idList);

    /**
     * 查询单个清分账户
     * @author 许云鹏
     */
    Optional<LedgerAccount> findByIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 根据业务ID查询未删除的账户
     * @param businessId
     * @param deleteFlag
     * @return
     */
    LedgerAccount findByBusinessIdAndDelFlag(String businessId, DeleteFlag deleteFlag);

    List<LedgerAccount> findByBusinessIdInAndDelFlag(List<String> businessId, DeleteFlag deleteFlag);


    /**
     * 根据进件id查询清分账户
     * @param contractId
     * @return
     */
    LedgerAccount findByContractId(String contractId);

    /**
     * 根据分账id查询清分账户
     * @param ledgerApplyId
     * @return
     */
    LedgerAccount findByLedgerApplyId(String ledgerApplyId);

    /**
     * 根据电子合同申请id查询清分账户
     * @param ecApplyId
     * @return
     */
    LedgerAccount findByEcApplyId(String ecApplyId);

    /**
     * 根据businessId 更新ecContent
     * @param ecContent
     * @param ecNo
     * @param businessId
     */
    @Modifying
    @Query("update LedgerAccount set ecContent = ?1, ecNo = ?2 where businessId = ?3")
    void updateEcContentByBusinessId(String ecContent, String ecNo,String businessId);

    /**
     * 根据contractId 更新Account （进件成功）
     * @param accountState
     * @param thirdMemNo
     * @param merCupNo
     * @param termNo
     * @param contractId
     */
    @Modifying
    @Query("update LedgerAccount set accountState = ?1, thirdMemNo = ?2, merCupNo = ?3, termNo = ?4, ledgerApplyId = " +
            "?5, ledgerState = 1, passTime = ?7, bankTermNo = ?8, unionTermNo=?9, quickPayTermNo=?10 where contractId = ?6")
    void updateAccountByContractId(Integer accountState, String thirdMemNo, String merCupNo, String termNo,
                                   String ledgerApplyId, String contractId, LocalDateTime passDateTime, String bankTermNo,
             String unionTermNo, String quickTermNo);


    /**
     * 根据contractId 更新Account（进件失败）
     * @param accountState
     * @param accountRejectReason
     * @param contractId
     */
    @Modifying
    @Query("update LedgerAccount set accountState = ?1, accountRejectReason = ?2 where contractId = ?3")
    void updateAccountByContractId(Integer accountState, String accountRejectReason,String contractId);


    /**
     * 根据ledgerApplyId 更新Account
     * @param ledgerState
     * @param businessId
     */
    @Modifying
    @Query("update LedgerAccount set ledgerState = ?1, ledgerRejectReason = ?3 where businessId = ?2")
    void updateAccountById(Integer ledgerState, String businessId, String ledgerRejectReason);

    /**
     * 查询接收方已审核的数据
     * @return
     */
    @Query("from LedgerAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and accountType = 1 and accountState = 2 and receiverType = 1 order by createTime")
    List<LedgerAccount> findReceiverPage(Pageable pageable);

    /**
     * 根据接收方已审核的数量
     * @return
     */
    @Query("select count(id) from LedgerAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and accountType = 1 and accountState = 2 order by createTime")
    Integer countReceiver();

    /**
     * 查询商户已开通分账的数据
     * @return
     */
    @Query("from LedgerAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and accountType = 0 and accountState = 2  and ledgerState = 2 order by createTime")
    List<LedgerAccount> findMerPage(Pageable pageable);

    /**
     * 根据商户已开通分账的数量
     * @return
     */
    @Query("select count(id) from LedgerAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and accountType = 0 and accountState = 2 and ledgerState = 2 order by createTime")
    Integer countMer();

    /**
     * 查询已经申请电子合同，但并未下载的分账账户的申请id集合
     * @return
     */
    @Query(value = "select ec_apply_id from ledger_account where ec_apply_id is not null and (ec_content is null or ec_content = '')", nativeQuery = true)
    List<String> findEcApplyIdListForCallBack();

    @Query(value = "select ec_apply_id from ledger_receiver_rel where ec_apply_id is not null and (ec_content_id is null or ec_content_id = '')", nativeQuery = true)
    List<String> findEc003ApplyIdListForCallBack();


    /**
     * 查询进件审核中，已存在进件id的分账账户的进件id集合
     * @return
     */
    @Query(value = "select contract_id from ledger_account where contract_id is not null and account_state = 1", nativeQuery = true)
    List<String> findContractIdListForCallBack();



    /**
     * 查询已经进件，但分账状态异常的拉卡拉内部商户号集合
     * @return
     */
    @Query(value = "select third_mem_no from ledger_account where account_state = 2 and ledger_state != 2", nativeQuery = true)
    List<String> findMerInnerNoListForCallBack();

    /**
     * 更新b2b网银新增状态
     * @param b2bAddState
     * @param businessId
     */
    @Modifying
    @Query("update LedgerAccount set b2bAddState = ?1, b2bAddApplyId = ?3 where businessId = ?2")
    void updateB2bAddStateById(Integer b2bAddState, String businessId, String b2bAddApplyId);

    /**
     * 根据b2bAddApplyId查询账户
     * @param b2bAddApplyId
     * @return
     */
    LedgerAccount findByB2bAddApplyId(String b2bAddApplyId);
}
