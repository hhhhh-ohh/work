package com.wanmi.sbc.goods.info.repository;

import com.wanmi.sbc.goods.info.model.root.GoodsPropDetailRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @auther ruilinxin
 * @create 2018/03/20 10:04
 */
@Repository
public interface GoodsPropDetailRelRepository extends JpaRepository<GoodsPropDetailRel, Long>, JpaSpecificationExecutor<GoodsPropDetailRel> {

    @Query(value = " from GoodsPropDetailRel a where a.goodsId= ?1 and a.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    List<GoodsPropDetailRel> queryByGoodsId(String goodsId);

    @Query
    List<GoodsPropDetailRel> findAllByPropId(Long propId);

    @Query
    List<GoodsPropDetailRel> findAllByDetailId(Long detailId);

    /**
     * 根据多个商品ID编号进行删除
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsPropDetailRel w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);

    /**
     * 根据多个SpuID查询
     * @param goodsIds
     * @return
     */
    @Query(value = "SELECT p.prop_id, IFNULL(r.detail_id, 0) AS detail_id, g.goods_id FROM goods_prop p JOIN goods g ON p.cate_id = g.cate_id LEFT JOIN goods_prop_detail_rel r ON p.prop_id = r.prop_id AND g.goods_id = r.goods_id WHERE g.goods_id in ?1", nativeQuery = true)
    List<Object> findRefByGoodIds(List<String> goodsIds);

    /**
     * 根据商品ID编号进行删除
     *
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("update GoodsPropDetailRel w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId = ?1")
    void deleteByGoodsId(String goodsId);
}
