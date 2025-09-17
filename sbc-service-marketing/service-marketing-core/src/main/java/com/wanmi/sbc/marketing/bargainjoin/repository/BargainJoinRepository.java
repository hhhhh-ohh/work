package com.wanmi.sbc.marketing.bargainjoin.repository;

import com.wanmi.sbc.marketing.bargainjoin.model.root.BargainJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * <p>帮砍记录DAO</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Repository
public interface BargainJoinRepository extends JpaRepository<BargainJoin, Long>,
        JpaSpecificationExecutor<BargainJoin> {

    BargainJoin findByJoinCustomerIdAndBargainId(String joinCustomerId, Long bargainId);

}
