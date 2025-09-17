package com.wanmi.sbc.goods.goodsproperty.repository;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyModifyRequest;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p>商品属性DAO</p>
 *
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Repository
public interface GoodsPropertyRepository extends JpaRepository<GoodsProperty, Long>,
        JpaSpecificationExecutor<GoodsProperty> {

    /**
     * 单个删除商品属性
     *
     * @param propId
     */
    @Modifying
    @Query("update GoodsProperty set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = ?1")
    @Override
    void deleteById(Long propId);

    /**
     * 根据id删除商品属性
     *
     * @param propId
     * @return
     */
    @Modifying
    @Query("update GoodsProperty set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = :propId")
    int deleteGoodsProperty(@Param("propId") Long propId);

    /**
     * 根据id查询
     *
     * @param id
     * @param delFlag
     * @return
     */
    Optional<GoodsProperty> findByPropIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据id集合查询
     *
     * @param idList
     * @param delFlag
     * @return
     */
    List<GoodsProperty> findByPropIdInAndDelFlag(Collection<Long> idList, DeleteFlag delFlag);


    /**
     * 取最大sort
     *
     * @return
     */
    @Query("select nullif(max(propSort),0) as sort from GoodsProperty")
    Long getMaxSort();

    /**
     * 修改sort
     *
     * @param sort
     */
    @Modifying
    @Query("update GoodsProperty prop set prop.propSort = prop.propSort+1 where prop.propSort >= :sort")
    void updateGoodsPropSort(@Param("sort") Long sort);

    /**
     * 修改sort
     *
     * @param request
     */
    @Modifying
    @Query("update GoodsProperty prop " +
            "set prop.propCharacter = :#{#request.propCharacter}," +
            "prop.propName = :#{#request.propName}," +
            "prop.propRequired = :#{#request.propRequired}," +
            "prop.propPinYin = :#{#request.propPinYin}," +
            "prop.propType = :#{#request.propType} " +
            "where prop.propId = :#{#request.propId}")
    void updateGoodsProperty(@Param("request") GoodsPropertyModifyRequest request);


    /**
     * 修改索引类型
     *
     * @param propId
     * @param indexFlag
     */
    @Modifying
    @Query("update GoodsProperty prop set prop.indexFlag = :indexFlag where prop.propId = :propId")
    void updateIndexFlag(@Param("propId") Long propId, @Param("indexFlag") DefaultFlag indexFlag);

    /**
     * 下方新增查询修改后的排序数据
     * @param propId
     * @return
     */
    @Query(value = "from GoodsProperty where propSort > (select propSort from GoodsProperty where propId = :propId)")
    List<GoodsProperty> findPropSort(@Param("propId") Long propId);

}
