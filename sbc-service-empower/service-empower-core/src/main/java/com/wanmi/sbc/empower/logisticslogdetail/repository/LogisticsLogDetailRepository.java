package com.wanmi.sbc.empower.logisticslogdetail.repository;

import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>物流记录明细DAO</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Repository
public interface LogisticsLogDetailRepository extends JpaRepository<LogisticsLogDetail, Long>,
        JpaSpecificationExecutor<LogisticsLogDetail> {

    /**
     * 单个删除物流记录明细
     * @author 宋汉林
     */
    @Modifying
    @Query("update LogisticsLogDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<LogisticsLogDetail> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
