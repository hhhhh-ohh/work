package com.wanmi.sbc.order.refund.repository;


import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 退款单数据源
 * Created by zhangjin on 2017/4/21.
 */
@Repository
public interface RefundOrderRepository extends JpaRepository<RefundOrder, String>, JpaSpecificationExecutor<RefundOrder> {

    /**
     * 修改退单状态
     * @param refundStatus refundStatus
     * @param refundIds refundIds
     * @return rows
     */
    @Modifying
    @Query("update RefundOrder r set r.refundStatus = :refundStatus where r.refundId in :refundIds")
    int updateRefundOrderStatus(@Param("refundStatus") RefundStatus refundStatus, @Param("refundIds") List<String>
            refundIds);

    /**
     * 修改退单备注
     * @param refundId refundId
     * @param comment comment
     * @return rows
     */
    @Modifying
    @Query("update RefundOrder r set r.refuseReason = :comment where r.refundId = :refundId")
    int updateRefundOrderReason(@Param("refundId") String refundId, @Param("comment") String comment);

    Optional<RefundOrder> findAllByReturnOrderCodeAndDelFlag(String returnOrderCode, DeleteFlag delFlag);

    /**
     * @description 增加根据退单批量查询退款单
     * @author  daiyitian
     * @date 2021/4/19 18:02
     * @param returnOrderCodeIn 退单号
     * @param delFlag 删除标识
     * @return java.util.List<com.wanmi.sbc.order.refund.model.root.RefundOrder> 退款单列表
     **/
    List<RefundOrder> findByReturnOrderCodeInAndDelFlag(List<String> returnOrderCodeIn, DeleteFlag delFlag);

    Optional<RefundOrder> findByRefundIdAndDelFlag(String refundId, DeleteFlag delFlag);

    /**
     * 分页查询主键id
     * @return List
     */
    @Query(" select refundId  from RefundOrder  where delFlag = 0 and refundStatus <> 0  and refundStatus <> 1")
    List<String> listByPage(Pageable pageable);

    /**
     * 根据id 查询详情，结果通过创建时间排序
     * @return List
     */
    List<RefundOrder> findByRefundIdInOrderByCreateTimeDesc(List<String> refundIds);

}
