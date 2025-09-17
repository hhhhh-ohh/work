package com.wanmi.sbc.customer.ledgersupplier.repository;

import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>商户分账绑定数据DAO</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Repository
public interface LedgerSupplierRepository extends JpaRepository<LedgerSupplier, String>,
        JpaSpecificationExecutor<LedgerSupplier> {

    /**
     * 修改平台绑定状态
     * @author lvzhenwei
     */
    @Modifying
    @Query("update LedgerSupplier set platBindState = ?1 where id = ?2")
    void updatePlatBindStateById(Integer platBindState,String id);


    /**
     * 修改供应商数量
     * @author lvzhenwei
     */
    @Modifying
    @Query("update LedgerSupplier set providerNum = providerNum + 1 where id = ?1")
    void updateProviderNumById(String id);


    /**
     * 修改分销员数量
     * @author lvzhenwei
     */
    @Modifying
    @Query("update LedgerSupplier set distributionNum = distributionNum + 1 where id = ?1")
    void updateDistributionNumById(String id);

    /**
     * 根据账户id查询
     * @param accountId
     * @return
     */
    LedgerSupplier findByLedgerAccountId(String accountId);

    /**
     * 根据账户ids查询
     * @param accountIds
     * @return
     */
    List<LedgerSupplier> findByLedgerAccountIdIn(List<String> accountIds);

    /**
     * 修改名称和code
     * @param name
     * @param companyInfoId
     */
    @Modifying
    @Query("update LedgerSupplier set companyName = ?1 where companyInfoId = ?2")
    void updateName(String name, Long companyInfoId);
}
