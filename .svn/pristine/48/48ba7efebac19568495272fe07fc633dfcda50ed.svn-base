package com.wanmi.sbc.goods.suppliercommissiongoods.repository;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 商家与代销商品关联表
 * @author  wur
 * @date: 2021/9/10 10:01
 **/
@Repository
public interface SupplierCommissionGoodRepository extends JpaRepository<SupplierCommissionGood, Long>,
        JpaSpecificationExecutor<SupplierCommissionGood> {

    /**
     * 根据商家查询所有代销商品信息
     * @param storeId  商家Id
     * @param delFlag  删除表示
     * @return
     */
    List<SupplierCommissionGood> findByStoreIdAndDelFlag(Long storeId, DeleteFlag delFlag);

    /**
     * 根据供应商商品Id查询
     * @author  wur
     * @date: 2021/9/14 13:49
     * @param providerGoodsId   供应商商品Id
     * @param delFlag           删除表示
     * @return
     **/
    List<SupplierCommissionGood> findByProviderGoodsIdAndDelFlag(String providerGoodsId, DeleteFlag delFlag);

    /**
     * 根据商家和供应商商品Id查询
     * @param storeId      商家Id
     * @param providerGoodsId   供应商商品Id
     * @return
     */
    SupplierCommissionGood findByStoreIdAndProviderGoodsIdAndDelFlag(Long storeId, String providerGoodsId, DeleteFlag deleteFlag);

    /**
    *
     * @description
     * @author  wur
     * @date: 2021/9/16 14:30
     * @param idList    Id
     * @param updateFlag 更新状态
     * @return
     **/
    @Query("from SupplierCommissionGood w where w.id in ?1 and w.storeId = ?2 and w.updateFlag = ?3 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<SupplierCommissionGood> findByIdAndUpdateFlag(List<String> idList, Long storeId, DefaultFlag updateFlag);

    /**
     * 更新同步操作
     * @param idList
     * @param synStatus
     * @return
     */
    @Modifying
    @Query("update SupplierCommissionGood w set w.synStatus = ?2 where w.id in ?1")
    void updateSynStatusById(List<String> idList, DefaultFlag synStatus);

    /**
     * 删除代销商品
     * @author  wur
     * @date: 2021/9/14 11:31
     * @param goodsId
     * @param storeId
     * @return
     **/
    @Modifying
    @Query("update SupplierCommissionGood w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, w.updateTime = now() where w.goodsId in ?1 and w.storeId=?2")
    void delCommissionGoodsList(List<String> goodsId, Long storeId);

    /**
     * @description   修改代销商品同步状态
     * @author  wur
     * @date: 2021/9/14 13:45
     * @param providerGoodsId   跨境商品Id
     * @param synStatus    同步状态
     * @return
     **/
    @Modifying
    @Query("update SupplierCommissionGood w set w.synStatus = ?3 where w.providerGoodsId = ?1 and w.storeId = ?2")
    void updateSynStatus(String providerGoodsId, Long storeId, DefaultFlag synStatus);

    /**
     * @description   修改跨境商品同步状态
     * @author  wur
     * @date: 2021/9/14 13:45
     * @param providerGoodsId   跨境商品Id
     * @param updateFlag    更新标识
     * @return
     **/
    @Modifying
    @Query("update SupplierCommissionGood w set w.updateFlag = ?2, w.synStatus = ?3, w.updateTime = now() where w.providerGoodsId = ?1")
    void updateUpAndSynFlag(String providerGoodsId, DefaultFlag updateFlag, DefaultFlag synStatus);

    /**
     * @description   修改供应商同步状态
     * @author  wur
     * @date: 2021/9/14 13:45
     * @param providerGoodsId   跨境商品Id
     * @param updateFlag    更新标识
     * @return
     **/
    @Modifying
    @Query("update SupplierCommissionGood w set w.updateFlag = ?2, w.updateTime = now() where w.providerGoodsId = ?1")
    void updateUpFlag(String providerGoodsId, DefaultFlag updateFlag);

}
