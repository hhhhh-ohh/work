package com.wanmi.sbc.order.payingmemberpayrecord.repository;

import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>付费会员支付记录表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Repository
public interface PayingMemberPayRecordRepository extends JpaRepository<PayingMemberPayRecord, String>,
        JpaSpecificationExecutor<PayingMemberPayRecord> {

    /**
     * 单个删除付费会员支付记录表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberPayRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(String id);

    /**
     * 批量删除付费会员支付记录表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberPayRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<String> idList);

    /**
     * 查询单个付费会员支付记录表
     * @author zhanghao
     */
    Optional<PayingMemberPayRecord> findByIdAndDelFlag(String id, DeleteFlag delFlag);


    PayingMemberPayRecord findByBusinessId(String businessId);

}
