package com.wanmi.sbc.goods.goodspropcaterel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>商品类目与属性关联DAO</p>
 *
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Repository
public interface GoodsPropCateRelRepository extends JpaRepository<GoodsPropCateRel, Long>,
        JpaSpecificationExecutor<GoodsPropCateRel> {

    /**
     * 单个删除商品类目与属性关联
     *
     * @author chenli
     */
    @Modifying
    @Query("update GoodsPropCateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where relId = ?1")
    @Override
    void deleteById(Long relId);

    /**
     * 删除指定cateId数据
     *
     * @param propId
     * @param cateIdList
     */
    @Modifying
    @Query("update GoodsPropCateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = :propId and cateId in :cateIdList")
    void deleteByCateId(@Param("propId") Long propId, @Param("cateIdList") List<Long> cateIdList);

    /**
     * 根据cateid删除数据
     * @param cateIdList
     */
    @Modifying
    @Query("update GoodsPropCateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where cateId in :cateIdList")
    void deletePropByCateId(@Param("cateIdList") List<Long> cateIdList);

    /**
     * 删除商品属性类目关系
     *
     * @param propId
     */
    @Modifying
    @Query("update GoodsPropCateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = :propId")
    void deleteGoodsPropCateRel(@Param("propId") Long propId);

    /**
     * 修改类目属性拖拽排序
     *
     * @param prop
     */
    @Modifying
    @Query("update GoodsPropCateRel set relSort = :#{#prop.sort} where propId = :#{#prop.propId} and cateId in :cateIdList and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    void updatePropCateSort(@Param("prop") GoodsPropertyVO prop, @Param("cateIdList") List<Long> cateIdList);


    /**
     * 根据id查询
     *
     * @param id
     * @param delFlag
     * @return
     */
    Optional<GoodsPropCateRel> findByRelIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据属性id查询属性类目关系表
     *
     * @param idList
     * @param delFlag
     * @return
     */
    List<GoodsPropCateRel> findByPropIdInAndDelFlag(List<Long> idList, DeleteFlag delFlag);

    /**
     * 根据属性id查询属性类目关系表
     *
     * @param id
     * @param delFlag
     * @return
     */
    List<GoodsPropCateRel> findByPropIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据cateId查询数据
     *
     * @param cateId
     * @param delFlag
     * @return
     */
    List<GoodsPropCateRel> findByCateIdAndDelFlag(Long cateId, DeleteFlag delFlag);


    /**
     * 根据cateId集合查询数据
     *
     * @param cateIdList
     * @param delFlag
     * @return
     */
    List<GoodsPropCateRel> findByCateIdInAndDelFlag(List<Long> cateIdList, DeleteFlag delFlag);

    /**
     * 根据分类id和属性id查询未删除的关联关系
     *
     * @param cateId
     * @param propId
     * @param delFlag
     * @return
     */
    Optional<GoodsPropCateRel> findByCateIdAndPropIdAndDelFlag(Long cateId, Long propId, DeleteFlag delFlag);

}
