package com.wanmi.sbc.goods.xsitegoodscate.repository;

import com.wanmi.sbc.goods.xsitegoodscate.model.root.XsiteGoodsCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>魔方商品分类表DAO</p>
 *
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@Repository
public interface XsiteGoodsCateRepository extends JpaRepository<XsiteGoodsCate, Long>,
        JpaSpecificationExecutor<XsiteGoodsCate> {

    /**
     * 根据pageCode查询数量
     * @param pageCode
     * @return
     */
    @Query("select count(c.pageCode) from XsiteGoodsCate c where c.pageCode = :pageCode")
    int findCountByPageCode(@Param("pageCode") String pageCode);

    /**
     * 根据pageCode删除
     * @param pageCode
     * @return
     */
    @Modifying
    @Query("delete from XsiteGoodsCate where pageCode=:pageCode")
    int delAllByPageCode(@Param("pageCode") String pageCode);

    /**
     * 根据cateUuid查询数据
     * @param cateUuid
     * @return
     */
    @Query("from XsiteGoodsCate c where c.cateUuid = :cateUuid")
    XsiteGoodsCate findByCateUuid(@Param("cateUuid") String cateUuid);

}
