package com.wanmi.sbc.goods.price.repository;

import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品级别价格数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface GoodsLevelPriceRepository extends JpaRepository<GoodsLevelPrice, Long>{

    /**
     * 根据商品ID查询SPU的级别价
     * @param goodsId 商品ID
     * @return
     */
    @Query("from GoodsLevelPrice w where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    List<GoodsLevelPrice> findByGoodsId(String goodsId);

    /**
     * 根据商品ID查询SKU的级别价
     * @param goodsId 商品ID
     * @return
     */
    @Query("from GoodsLevelPrice w where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsLevelPrice> findSkuLevelPriceByGoodsId(String goodsId);

    /**
     * 根据商品SkuID查询SKU的级别价
     * @param goodsInfoId 商品SkuID
     * @return
     */
    @Query("from GoodsLevelPrice w where w.goodsInfoId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsLevelPrice> findSkuByGoodsInfoId(String goodsInfoId);

    /**
     * 根据商品SkuID查询SKU的级别价
     * @param goodsInfoIds 多商品SkuID
     * @return
     */
    @Query("from GoodsLevelPrice w where w.goodsInfoId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsLevelPrice> findSkuByGoodsInfoIds(List<String> goodsInfoIds);

    /**
     * 根据批量商品ID和批量等级查询SKU的级别价
     * @param goodsInfoIds 商品ID
     * @param levelIds 会员ID
     * @return
     */
    @Query("from GoodsLevelPrice w where w.goodsInfoId in ?1 and w.levelId in ?2 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsLevelPrice> findSkuByGoodsInfoIdAndLevelIds(List<String> goodsInfoIds, List<Long> levelIds);

    /**
     * 根据商品ID删除
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("delete from GoodsLevelPrice w where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    void deleteByGoodsId(String goodsId);

    /**
     * 根据商品ID批量删除
     * @param goodsIds 商品IDs
     */
    @Modifying
    @Query("delete from GoodsLevelPrice w where w.goodsId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    void deleteByGoodsIds(List<String> goodsIds);

    /**
     * 根据商品SkuID删除
     * @param goodsInfoId 商品SkuID
     */
    @Modifying
    @Query("delete from GoodsLevelPrice w where w.goodsInfoId = ?1")
    void deleteByGoodsInfoId(String goodsInfoId);

    /**
     * 根据商品SkuID批量删除
     * @param goodsInfoId 商品SkuID
     */
    @Modifying
    @Query("delete from GoodsLevelPrice w where w.goodsInfoId in ?1")
    void deleteByGoodsInfoIds(List<String> goodsInfoId);

    /**
     * 根据商品SkuID和类型批量删除
     * @param goodsInfoId 商品SkuID
     */
    @Modifying
    @Query("delete from GoodsLevelPrice w where w.goodsInfoId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    void deleteByGoodsInfoIdsAndType(List<String> goodsInfoId);

    /**
     * 根据商品新的SpuID更新老的SpuID
     * @author xufeng
     */
    @Modifying
    @Query("update GoodsLevelPrice set goodsId = ?1 where goodsId = ?2")
    void updateByGoodsId(String oldGoodsId, String newGoodsId);

    /**
     * 根据商品新的SkuID更新老的SkuID
     * @author xufeng
     */
    @Modifying
    @Query("update GoodsLevelPrice set goodsInfoId = ?1 where goodsInfoId = ?2")
    void updateByGoodsInfoId(String oldGoodsInfoId, String newGoodsInfoId);
}
