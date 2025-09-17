package com.wanmi.sbc.setting.cancellation.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.cancellation.model.root.CancellationReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 退款原因DO
 * @author houshuai
 */
@Repository
public interface CancellationReasonRepository extends JpaRepository<CancellationReason,String> {


    /**
     * 删除全部
     */
    @Override
    @Modifying
    @Query("update CancellationReason set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where  delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    void deleteAll();

    /**
     * 查寻未删除的注销原因
     * @param deleteFlag
     * @return
     */
    List<CancellationReason> findByDelFlag(DeleteFlag deleteFlag);
}
