package com.wanmi.sbc.empower.ledgermcc.repository;

import com.wanmi.sbc.empower.ledgermcc.model.root.LedgerMcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>拉卡拉mcc表DAO</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Repository
public interface LedgerMccRepository extends JpaRepository<LedgerMcc, Long>,
        JpaSpecificationExecutor<LedgerMcc> {

    /**
     * 单个删除拉卡拉mcc表
     * @author zhanghao
     */
    @Modifying
    @Query("update LedgerMcc set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where mccId = ?1")
    void deleteById(Long mccId);

    /**
     * 批量删除拉卡拉mcc表
     * @author zhanghao
     */
    @Modifying
    @Query("update LedgerMcc set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where mccId in ?1")
    void deleteByIdList(List<Long> mccIdList);

    /**
     * 查询单个拉卡拉mcc表
     * @author zhanghao
     */
    Optional<LedgerMcc> findByMccIdAndDelFlag(Long id, DeleteFlag delFlag);

}
