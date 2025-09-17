package com.wanmi.sbc.goods.flashsaleRecord.repository;

import com.wanmi.sbc.goods.flashsaleRecord.model.root.FlashSaleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>抢购商品记录表DAO</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Repository
public interface FlashSaleRecordRepository extends JpaRepository<FlashSaleRecord, Long>,
        JpaSpecificationExecutor<FlashSaleRecord> {
    /**
     * @return int
     * @Author xufeng
     * @Description 增加抢购商品数量
     * @Date 9:41 2021/7/12
     * @Param [purchaseNum, recordId]
     **/
    @Modifying
    @Query("update FlashSaleRecord f set f.purchaseNum = f.purchaseNum + ?1, f.updateTime = now() where f.flashGoodsId" +
            " = ?2")
    int plusPurchaseNumByFlashGoodsId(Long purchaseNum, Long flashGoodsId);

    /**
     * 根据秒杀商品主键查询抢购记录
     *
     * @param flashGoodsId
     * @return Optional<FlashSaleRecord>
     */
    Optional<FlashSaleRecord> findByFlashGoodsId(Long flashGoodsId);
}
