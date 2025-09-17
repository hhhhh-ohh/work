package com.wanmi.sbc.setting.helpcenterarticlecate.repository;

import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;
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
 * @date 2023-03-16 09:44:52
 */
@Repository
public interface HelpCenterArticleCateRepository extends JpaRepository<HelpCenterArticleCate, Long>,
        JpaSpecificationExecutor<HelpCenterArticleCate> {

    /**
     * 单个删除帮助中心文章信息
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticleCate set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除帮助中心文章信息
     * @author 吕振伟
     */
    @Modifying
    @Query("update HelpCenterArticleCate set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个帮助中心文章信息
     * @author 吕振伟
     */
    Optional<HelpCenterArticleCate> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 查询拼团分类列表
     *
     * @return
     */
    @Query(" from HelpCenterArticleCate c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO order by c.defaultCate desc, c.cateSort asc, c.createTime desc")
    List<HelpCenterArticleCate> getHelpCenterArticleCateList();

    /**
     * 分类排序
     *
     * @param storeCateId 分类Id
     * @param cateSort    分类顺序
     */
    @Modifying
    @Query(" update HelpCenterArticleCate c set c.cateSort = ?2 where c.id = ?1 ")
    void updateCateSort(Long storeCateId, Integer cateSort);

}
