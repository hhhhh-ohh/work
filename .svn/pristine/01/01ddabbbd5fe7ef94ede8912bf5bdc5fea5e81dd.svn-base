package com.wanmi.sbc.setting.helpcenterarticlerecord.repository;

import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>帮助中心文章记录DAO</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Repository
public interface HelpCenterArticleRecordRepository extends JpaRepository<HelpCenterArticleRecord, Long>,
        JpaSpecificationExecutor<HelpCenterArticleRecord> {

    /**
     * 单个删除帮助中心文章记录
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticleRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除帮助中心文章记录
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticleRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个帮助中心文章记录
     * @author 吕振伟
     */
    Optional<HelpCenterArticleRecord> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
