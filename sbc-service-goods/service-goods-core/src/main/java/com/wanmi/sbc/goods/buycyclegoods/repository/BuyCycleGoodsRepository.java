package com.wanmi.sbc.goods.buycyclegoods.repository;

import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>周期购spu表DAO</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Repository
public interface BuyCycleGoodsRepository extends JpaRepository<BuyCycleGoods, Long>,
        JpaSpecificationExecutor<BuyCycleGoods> {

    /**
     * 单个删除周期购spu表
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 修改周期购spu表状态
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoods set cycleState = ?2 where id in ?1")
    void modifyState(Long id,Integer cycleState);


    /**
     * 修改周期购spu商品名称
     * @author zhanghao
     */
    @Modifying
    @Query("update BuyCycleGoods set goodsName = ?2 where goodsId = ?1")
    void modifyGoodsNameByGoodsId(String goodsId,String goodsName);

    /**
     * 查询单个周期购spu表
     * @author zhanghao
     */
    Optional<BuyCycleGoods> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据SPUid以及状态查询
     * @param goodsId
     * @param delFlag
     * @return
     */
    Optional<BuyCycleGoods> findByGoodsIdAndCycleStateAndDelFlag(String goodsId,Integer cycleState, DeleteFlag delFlag);

}
