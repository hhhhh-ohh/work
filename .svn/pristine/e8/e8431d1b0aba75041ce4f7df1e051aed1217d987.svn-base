package com.wanmi.sbc.goods.goodspropertydetail.repository;

import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

/**
 * <p>商品属性值DAO</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Repository
public interface GoodsPropertyDetailRepository extends JpaRepository<GoodsPropertyDetail, Long>,
        JpaSpecificationExecutor<GoodsPropertyDetail> {

    /**
     * 单个删除商品属性值
     * @param detailId
     */
    @Modifying
    @Query("update GoodsPropertyDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where detailId = ?1")
    @Override
    void deleteById(Long detailId);

    /**
     * 删除商品属性类目关系
     * @param propId
     */
    @Modifying
    @Query("update GoodsPropertyDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = :propId")
    void deleteGoodsPropertyDetail(@Param("propId") Long propId);

    /**
     * 批量删除商品属性值
     * @author chenli
     */
    @Modifying
    @Query("update GoodsPropertyDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where detailId in ?1")
    void deleteByIdList(List<Long> detailIdList);

    Optional<GoodsPropertyDetail> findByDetailIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据属性id查询
     * @param idList  属性id
     * @param delFlag 是否删除
     * @return GoodsPropertyDetail
     */
    List<GoodsPropertyDetail> findByPropIdInAndDelFlag(Collection<Long> idList, DeleteFlag delFlag);

    /**
     * 根据属性值id查询
     * @param detailIdList  属性值id
     * @param delFlag 是否删除
     * @return GoodsPropertyDetail
     */
    List<GoodsPropertyDetail> findByDetailIdInAndDelFlag(Collection<Long> detailIdList, DeleteFlag delFlag);

    /**
     * 根据属性id查询
     * @param id  属性id
     * @param delFlag 是否删除
     * @return GoodsPropertyDetail
     */
    List<GoodsPropertyDetail> findByPropIdAndDelFlagOrderByDetailSortDesc(Long id, DeleteFlag delFlag);

}
