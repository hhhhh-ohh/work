package com.wanmi.sbc.setting.recommendcate.repository;

import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>笔记分类表DAO</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Repository
public interface RecommendCateRepository extends JpaRepository<RecommendCate, Long>,
        JpaSpecificationExecutor<RecommendCate> {

    /**
     * 批量删除笔记分类表
     * @author 王超
     */
    @Modifying
    @Query("update RecommendCate set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where cateId in ?1")
    void deleteByIdList(List<Long> cateIdList);

    /**
     * 列表查询笔记分类并排序
     * @return
     */
    @Query(" from RecommendCate c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO order by c.cateSort asc, c.createTime desc")
    List<RecommendCate> findAllBySort();

    /**
     * 查询单个笔记分类表
     * @author 王超
     */
    Optional<RecommendCate> findByCateIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 查询未删除的分类个数
     * @return
     */
    @Query(value = "select count(1) from recommend_cate where del_flag = 0 ", nativeQuery = true)
    int selectCountByFlag();

    /**
     * 分类排序
     *
     * @param cateId 分类Id
     * @param cateSort    分类顺序
     */
    @Modifying
    @Query(" update RecommendCate c set c.cateSort = ?2 where c.cateId = ?1 ")
    void updateCateSort(Long cateId, Integer cateSort);

}
