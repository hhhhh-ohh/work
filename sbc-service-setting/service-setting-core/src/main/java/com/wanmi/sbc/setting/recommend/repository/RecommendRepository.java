package com.wanmi.sbc.setting.recommend.repository;

import com.wanmi.sbc.setting.recommend.model.root.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>种草信息表DAO</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long>,
        JpaSpecificationExecutor<Recommend> {

    /**
     * 单个删除种草信息表
     * @author 黄昭
     */
    @Modifying
    @Query("update Recommend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除种草信息表
     * @author 黄昭
     */
    @Modifying
    @Query("update Recommend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个种草信息表
     * @author 黄昭
     */
    Optional<Recommend> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 种草统计数据同步mysql
     * @author xufeng
     */
    @Modifying
    @Query("update Recommend r set r.readNum=:readNum,r.visitorNum=:visitorNum,r.fabulousNum=:fabulousNum," +
            "r.forwardNum=:forwardNum where r.pageCode = :pageCode")
    void recommendSync(@Param("readNum") Long readNum,
                       @Param("visitorNum") Long visitorNum,
                       @Param("fabulousNum") Long fabulousNum,
                       @Param("forwardNum") Long forwardNum,
                       @Param("pageCode") String pageCode);

    /**
     * 通过PageCode查询种草信息
     * @author 黄昭
     */
    Optional<Recommend> findByPageCodeAndDelFlag(String pageCode,DeleteFlag delFlag);

    /**
     * 删除分类同步删除种草分类
     * @author xufeng
     */
    @Modifying
    @Query("update Recommend r set r.cateId = null where r.cateId = :cateId")
    void updateCateId(@Param("cateId") Long cateId);

    /**
     * 删除分类同步删除种草新分类
     * @author xufeng
     */
    @Modifying
    @Query("update Recommend r set r.newCateId = null where r.cateId = :cateId")
    void updateNewCateId(Long cateId);
}
