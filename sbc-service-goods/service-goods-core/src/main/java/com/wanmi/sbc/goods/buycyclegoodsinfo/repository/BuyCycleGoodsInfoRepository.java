package com.wanmi.sbc.goods.buycyclegoodsinfo.repository;

import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>周期购sku表DAO</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Repository
public interface BuyCycleGoodsInfoRepository extends JpaRepository<BuyCycleGoodsInfo, Long>,
        JpaSpecificationExecutor<BuyCycleGoodsInfo> {

    /**
     * 单个删除周期购sku表
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoodsInfo set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsInfoId = ?1")
    void deleteById(String goodsInfoId);


    /**
     *根据SPUID删除周期购sku表
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoodsInfo set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where buyCycleId = ?1")
    void deleteByBuyCycleId(Long buyCycleId);


    /**
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoodsInfo set cycleState = ?2 where buyCycleId = ?1")
    void modifyStateByBuyCycleId(Long buyCycleId, Integer cycleState);

    /**
     * 批量删除周期购sku表
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoodsInfo set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> ids);

    /**
     * 查询单个周期购sku表
     * @author zhanghao
     */
    Optional<BuyCycleGoodsInfo> findByGoodsInfoIdAndCycleStateAndDelFlag(String id,Integer cycleState, DeleteFlag delFlag);

    /**
     * 查询有其他生效活动的商品
     * @param goodsInfoIds
     * @param buyCycleId
     * @return
     */
    @Query("select distinct goodsInfoId from BuyCycleGoodsInfo where goodsInfoId in ?1 and buyCycleId <> ?2 and cycleState = 0 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findActiveCountExclude(List<String> goodsInfoIds, Long buyCycleId);

}
