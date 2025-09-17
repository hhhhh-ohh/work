package com.wanmi.sbc.setting.helpcenterarticle.repository;

import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>帮助中心文章信息DAO</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Repository
public interface HelpCenterArticleRepository extends JpaRepository<HelpCenterArticle, Long>,
        JpaSpecificationExecutor<HelpCenterArticle> {

    /**
     * 单个删除帮助中心文章信息
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticle set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除帮助中心文章信息
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticle set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个帮助中心文章信息
     * @author 吕振伟
     */
    Optional<HelpCenterArticle> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 增加文章浏览量
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticle set viewNum = viewNum + 1 where id = ?1")
    void addViewNum(Long id);

    /**
     * 增加文章解决次数
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticle set solveNum = solveNum + 1 where id = ?1")
    void clickSolve(Long id);

    /**
     * 增加文章未解决次数
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticle set unresolvedNum = unresolvedNum + 1 where id = ?1")
    void clickUnresolved(Long id);

}
