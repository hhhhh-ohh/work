package com.wanmi.sbc.marketing.bargain.repository;

import com.wanmi.sbc.marketing.bargain.model.root.Bargain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


/**
 * <p>砍价DAO</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Repository
public interface BargainRepository extends JpaRepository<Bargain, Long>,
        JpaSpecificationExecutor<Bargain> {
    @Query("FROM Bargain WHERE customerId = ?1 AND bargainGoodsId in ?2")
    List<Bargain> forCustomer(String customerId, List<Long> bargainGoodsIds);

    @Query("select count(*) from Bargain where bargainGoodsId = ?1 and customerId = ?2 and (endTime > now() or targetJoinNum = joinNum)")
    Integer originated(Long bargainGoodsId, String customerId);

    @Modifying
    @Query("update Bargain set joinNum = joinNum + 1,bargainedAmount = bargainedAmount + ?2 where bargainId = ?1 and joinNum < targetJoinNum")
    Integer join(Long bargainId, BigDecimal bargainedAmount);

    @Query("update Bargain set orderId = ?2,updateTime = now() where bargainId = ?1 and (orderId is null or orderId = '')")
    @Modifying
    int commitTrade(Long bargainId, String orderId);

    @Query("update Bargain set orderId = '',updateTime = now() where bargainId = ?1")
    @Modifying
    int cancelTrade(Long bargainId);

    @Query(" select count(*) from Bargain where bargainId = ?1 and (orderId is null or orderId = '')")
    int canCommit(Long bargainId);

}
