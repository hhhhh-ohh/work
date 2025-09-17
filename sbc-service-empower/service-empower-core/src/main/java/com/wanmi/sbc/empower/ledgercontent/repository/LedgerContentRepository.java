package com.wanmi.sbc.empower.ledgercontent.repository;

import com.wanmi.sbc.empower.ledgercontent.model.root.LedgerContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>拉卡拉经营内容表DAO</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Repository
public interface LedgerContentRepository extends JpaRepository<LedgerContent, Long>,
        JpaSpecificationExecutor<LedgerContent> {

    /**
     * 单个删除拉卡拉经营内容表
     * @author zhanghao
     */
    @Modifying
    @Query("update LedgerContent set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where contentId = ?1")
    void deleteById(Long contentId);

    /**
     * 批量删除拉卡拉经营内容表
     * @author zhanghao
     */
    @Modifying
    @Query("update LedgerContent set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where contentId in ?1")
    void deleteByIdList(List<Long> contentIdList);

    /**
     * 查询单个拉卡拉经营内容表
     * @author zhanghao
     */
    Optional<LedgerContent> findByContentIdAndDelFlag(Long id, DeleteFlag delFlag);

}
