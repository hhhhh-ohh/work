package com.wanmi.sbc.goods.price.repository;

import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品订货区间价格数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface GoodsIntervalPriceRepository extends JpaRepository<GoodsIntervalPrice, Long>{

    /**
     * 根据商品ID查询SPU区间价
     * @param goodsId 商品ID
     * @return
     */
    @Query("from GoodsIntervalPrice w where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    List<GoodsIntervalPrice> findByGoodsId(String goodsId);

    /**
     * 根据商品SkuID查询SKU区间价
     * @param goodsInfoId 商品ID
     * @return
     */
    @Query("from GoodsIntervalPrice w where w.goodsInfoId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsIntervalPrice> findSkuByGoodsInfoId(String goodsInfoId);

    /**
     * 根据商品ID批量查询SPU区间价
     * @param goodsIds 多个商品ID
     * @return
     */
    @Query("from GoodsIntervalPrice w where w.goodsId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    List<GoodsIntervalPrice> findByGoodsIds(List<String> goodsIds);

    /**
     * 根据商品SkuID批量查询SKU区间价
     * @param goodsInfoIds 多个商品Sku编号
     * @return
     */
    @Query("from GoodsIntervalPrice w where w.goodsInfoId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsIntervalPrice> findSkuByGoodsInfoIds(List<String> goodsInfoIds);

    /**
     * 根据商品ID删除
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("delete from GoodsIntervalPrice w  where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    void deleteByGoodsId(String goodsId);

    /**
     * 根据商品ID批量删除
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("delete from GoodsIntervalPrice w  where w.goodsId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SPU")
    void deleteByGoodsIds(List<String> goodsIds);

    /**
     * 根据商品SkuID删除
     * @param goodsInfoId 商品SkuID
     */
    @Modifying
    @Query("delete from GoodsIntervalPrice w  where w.goodsInfoId = ?1")
    void deleteByGoodsInfoId(String goodsInfoId);

    /**
     * 根据商品SkuID批量删除
     * @param goodsInfoId 商品SkuID
     */
    @Modifying
    @Query("delete from GoodsIntervalPrice w  where w.goodsInfoId in ?1")
    void deleteByGoodsInfoIds(List<String> goodsInfoId);

    /**
     * 根据商品SkuID批量删除SKU区间价
     * @param goodsInfoId 多个商品Sku编号
     * @return
     */
    @Modifying
    @Query("delete from GoodsIntervalPrice w where w.goodsInfoId in ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    void deleteByGoodsInfoIdsAndType(List<String> goodsInfoId);

    /**
     * 根据商品新的SpuID更新老的SpuID
     * @author xufeng
     */
    @Modifying
    @Query("update GoodsIntervalPrice set goodsId = ?1 where goodsId = ?2")
    void updateByGoodsId(String oldGoodsId, String newGoodsId);

    /**
     * 根据商品新的SkuID更新老的SkuID
     * @author xufeng
     */
    @Modifying
    @Query("update GoodsIntervalPrice set goodsInfoId = ?1 where goodsInfoId = ?2")
    void updateByGoodsInfoId(String oldGoodsInfoId, String newGoodsInfoId);

    /**
     * 根据商品ID查询SKU的区间价
     * @param goodsId
     * @return
     */
    @Query("from GoodsIntervalPrice w where w.goodsId = ?1 and w.type = com.wanmi.sbc.goods.bean.enums.PriceType.SKU")
    List<GoodsIntervalPrice> findSkuByGoodsId(String goodsId);
}
