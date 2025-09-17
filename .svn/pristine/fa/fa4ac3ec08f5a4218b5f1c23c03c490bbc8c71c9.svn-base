package com.wanmi.sbc.customer.ledgercontract.repository;

import com.wanmi.sbc.customer.ledgercontract.model.root.LedgerContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>分账合同协议配置DAO</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@Repository
public interface LedgerContractRepository extends JpaRepository<LedgerContract, Long>,
        JpaSpecificationExecutor<LedgerContract> {

    /**
     * 查询第一条数据
     * @return
     */
    LedgerContract findFirstByOrderById();

}
