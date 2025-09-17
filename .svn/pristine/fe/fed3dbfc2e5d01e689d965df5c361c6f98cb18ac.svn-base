package com.wanmi.sbc.goods.standard.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品库SPU数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface StandardGoodsRepository extends JpaRepository<StandardGoods, String>, JpaSpecificationExecutor<StandardGoods>{

    /**
     * 根据多个商品ID编号进行删除
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update StandardGoods w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);


    /**
     * 商品库id 查找
     * @param standardGoodsIds
     * @return
     */
    @Query
    List<StandardGoods> findByGoodsIdIn(List<String> standardGoodsIds);

    @Modifying
    @Query("update StandardGoods g set g.addedFlag = ?2 , g.updateTime = now() where g.goodsId = ?1 ")
    void updateAddedFlag(String standardId ,Integer addedFlag );


    @Modifying
    @Query(value = "UPDATE standard_goods SET cate_id=:cateId WHERE goods_source=:source AND third_cate_id=:thirdCateId",nativeQuery = true)
    void updateThirdCateMap(@Param("source") int source, @Param("thirdCateId") long thirdCateId, @Param("cateId") long cateId);

    StandardGoods findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag deleteFlag, Integer goodsSource,String thirdPlatformSpuId);

    @Query("select distinct g.goodsId from  StandardGoods g where g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and g.thirdCateId in ?1 and g.goodsSource=?2")
    List<String> findAllByThirdCateIdInAndGoodsSource(List<Long> thirdCateIds, int goodsSource);

    /**
     * 根据商品id查询商品信息
     * @param goodsIds
     * @return
     */
    @Query
    List<StandardGoods> findAllByGoodsIdIn(List<String> goodsIds);
}
