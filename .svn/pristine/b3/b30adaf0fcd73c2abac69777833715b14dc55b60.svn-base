package com.wanmi.sbc.goods.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品图片数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long>{

    /**
     * 根据商品ID查询
     * @param goodsId 商品ID
     * @return 商品图片信息
     */
    @Query("from GoodsImage w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId = ?1 order by w.sort asc")
    List<GoodsImage> findByGoodsId(String goodsId);

    /**
     * 根据商品ID批量查询
     * @param goodsIds 商品ID
     * @return 商品图片信息
     */
    @Query("from GoodsImage w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId in ?1")
    List<GoodsImage> findByGoodsIds(List<String> goodsIds);


    /**
     * 根据商品ID单个删除
     * @param goodsId 商品ID
     * @return
     */
    @Modifying
    @Query("update GoodsImage w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES , w.updateTime = now() where w.goodsId = ?1")
    void deleteByGoodsId(String goodsId);

    /**
     * 根据商品ID批量删除
     * @param goodsIds 商品IDs
     * @return
     */
    @Modifying
    @Query("update GoodsImage w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES , w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);
}
