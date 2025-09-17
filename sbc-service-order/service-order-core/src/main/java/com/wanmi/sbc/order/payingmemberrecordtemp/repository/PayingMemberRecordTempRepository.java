package com.wanmi.sbc.order.payingmemberrecordtemp.repository;

import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>付费记录临时表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Repository
public interface PayingMemberRecordTempRepository extends JpaRepository<PayingMemberRecordTemp, String>,
        JpaSpecificationExecutor<PayingMemberRecordTemp> {

    /**
     * 单个删除付费记录临时表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecordTemp set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where recordId = ?1")
    void deleteById(String recordId);

    /**
     * 批量删除付费记录临时表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecordTemp set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where recordId in ?1")
    void deleteByIdList(List<String> recordIdList);

    /**
     * 查询单个付费记录临时表
     * @author zhanghao
     */
    Optional<PayingMemberRecordTemp> findByRecordIdAndDelFlag(String id, DeleteFlag delFlag);

}
