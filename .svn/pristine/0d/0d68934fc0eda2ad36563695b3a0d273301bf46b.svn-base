package com.wanmi.sbc.goods.standard.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品库SKU数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface StandardSkuRepository extends JpaRepository<StandardSku, String>, JpaSpecificationExecutor<StandardSku>{

    /**
     * 根据多个商品ID编号进行删除
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update StandardSku w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);

    /**
     * 根据商品ID编号进行删除
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("update StandardSku w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId = ?1")
    void deleteByGoodsId(String goodsId);

    /**
     * 根据商品库sku
     * @param goodsId
     * @return
     */
    @Query
    List<StandardSku> findByGoodsId(String goodsId);

    @Modifying
    @Query("update StandardSku w set w.addedFlag = ?2 ,w.goodsInfoNo = ?3 ,w.updateTime = now() where w.goodsInfoId = ?1")
    void updateAddedFlagAndGoodsInfoNo(String goodsInfoId,Integer addedFlag ,String goodsInfoNo);

    @Modifying
    @Query("update StandardSku w set w.addedFlag = ?2 ,w.updateTime = now() where w.goodsInfoId = ?1")
    void updateAddedFlag(String goodsInfoId,Integer addedFlag );

    @Query("from StandardSku w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId in ?1")
    List<StandardSku> findByGoodsIdIn(List<String> standardGoodsIds);

    StandardSku findByDelFlagAndGoodsSourceAndThirdPlatformSpuIdAndThirdPlatformSkuId(DeleteFlag deleteFlag,Integer goodsSource, String thirdPlatformSpuId,String thirdPlatformSkuId);

    List<StandardSku> findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag deleteFlag, int goodsSource, String thirdPlatformSpuId);

    @Modifying
    @Query("update StandardSku  set supplyPrice = ?2 where providerGoodsInfoId = ?1")
    void updateSupplyPriceByProviderGoodsInfoId(String providerGoodsInfoId, BigDecimal supplyPrice);

    @Query("from StandardSku w where w.providerGoodsInfoId in ?1 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<StandardSku> findByProviderGoodsInfoIdIn(List<String> providerGoodsInfoIds);

    /**
     * 根据skuIdList查询sku(包含已删除的)
     *
     * @param goodsInfoIdList
     */
    @Query("from StandardSku s where  s.goodsInfoId in ?1")
    List<StandardSku> findByGoodsInfoIds(List<String> goodsInfoIdList);
}
