package com.wanmi.sbc.goods.goodspropertydetailrel.repository;

import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
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
 * <p>商品与属性值关联DAO</p>
 *
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Repository
public interface GoodsPropertyDetailRelRepository extends JpaRepository<GoodsPropertyDetailRel, Long>,
        JpaSpecificationExecutor<GoodsPropertyDetailRel> {

    /**
     * 单个删除商品与属性值关联
     *
     * @author chenli
     */
    @Modifying
    @Query("update GoodsPropertyDetailRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where detailRelId = ?1")
    @Override
    void deleteById(Long detailRelId);

    /**
     * 单个删除商品与属性值关联
     *
     * @param propId
     * @return
     */
    @Modifying
    @Query("update GoodsPropertyDetailRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where propId = :propId")
    void deleteGoodsPropertyDetailRel(@Param("propId") Long propId);

    /**
     * 根据cateId删除
     *
     * @param cateIdList
     * @return
     */
    @Modifying
    @Query("update GoodsPropertyDetailRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES  where  propId=:propId and cateId in :cateIdList")
    void deleteByCateId(@Param("propId") Long propId,@Param("cateIdList") List<Long> cateIdList);

    /**
     * 单个删除商品与属性值关联
     *
     * @param goodsIdList
     * @return
     */
    @Modifying
    @Query("update GoodsPropertyDetailRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsId in :goodsIdList")
    void deleteByGoodsId(@Param("goodsIdList") List<String> goodsIdList);

    @Query(value = "SELECT prop_value_text FROM `goods_property` gp LEFT JOIN goods_property_detail_rel gd on gp.prop_id = gd.prop_id\n" +
            "WHERE gd.del_flag = 0 AND gd.goods_id =?1 and gp.prop_name = ?2", nativeQuery = true)
    String findPriceByIdAndGoodsType(String goodsId, String propName);
    /**
     * 根据商品id查询
     * @param goodsId
     * @param deleteFlag
     * @param goodsType
     * @return
     */
    List<GoodsPropertyDetailRel> findByGoodsIdAndDelFlagAndGoodsType(String goodsId, DeleteFlag deleteFlag, GoodsPropertyType goodsType);

    /**
     * 根据商品id查询
     * @param goodsIdList
     * @param deleteFlag
     * @param goodsType
     * @return
     */
    List<GoodsPropertyDetailRel> findByGoodsIdInAndDelFlagAndGoodsType(List<String> goodsIdList,
                                                                       DeleteFlag deleteFlag,
                                                                       GoodsPropertyType goodsType);


    /**
     * 根据商品id查询
     * @param propId
     * @param deleteFlag
     * @return
     */
    List<GoodsPropertyDetailRel> findByPropIdInAndDelFlag(List<Long> propId, DeleteFlag deleteFlag);


    /**
     * 根据CATEID查询
     *
     * @param cateId
     * @return
     */
    List<GoodsPropertyDetailRel> findByCateIdAndDelFlag(Long cateId, DeleteFlag deleteFlag);

    Optional<GoodsPropertyDetailRel> findByDetailRelIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 删除商品下的所有属性
     * @param goodsId
     * @param goodsType
     */
    @Modifying
    void deleteByGoodsIdAndGoodsType(String goodsId, GoodsPropertyType goodsType);
}
