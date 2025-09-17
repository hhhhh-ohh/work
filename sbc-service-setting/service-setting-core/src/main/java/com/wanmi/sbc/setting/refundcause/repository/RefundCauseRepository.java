package com.wanmi.sbc.setting.refundcause.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.refundcause.model.root.RefundCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/11/16 11:03
 * @description <p> 退款原因DO</p>
 */
@Repository
public interface RefundCauseRepository extends JpaRepository<RefundCause,String> {


    /**
     * 删除全部
     */
    @Override
    @Modifying
    @Query("update RefundCause set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where  delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    void deleteAll();

    /**
     * 查寻未删除的退款原因
     * @param deleteFlag
     * @return
     */
    List<RefundCause> findByDelFlag(DeleteFlag deleteFlag);
}
