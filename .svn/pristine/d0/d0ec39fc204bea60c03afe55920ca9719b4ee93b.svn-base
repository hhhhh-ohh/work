package com.wanmi.sbc.goods.flashsaleRecord.service;

import com.wanmi.sbc.goods.flashsaleRecord.model.root.FlashSaleRecord;
import com.wanmi.sbc.goods.flashsaleRecord.repository.FlashSaleRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>抢购商品记录表业务逻辑</p>
 *
 * @author xufeng
 * @date 2021-07-12 14:54:31
 */
@Service("FlashSaleRecordService")
public class FlashSaleRecordService {

    @Autowired
    private FlashSaleRecordRepository flashSaleRecordRepository;

    /**
     * 新增秒杀记录
     */
    @Transactional
    public FlashSaleRecord add(FlashSaleRecord entity) {
        flashSaleRecordRepository.save(entity);
        return entity;
    }

    /**
     * 单个查询秒杀记录
     */
    public FlashSaleRecord getByFlashGoodsId(Long flashGoodsId) {
        return flashSaleRecordRepository.findByFlashGoodsId(flashGoodsId).orElse(null);
    }

    /**
     * 增加购买数量
     **/
    @Transactional
    public void plusPurchaseNumByFlashGoodsId(FlashSaleRecord entity) {
        flashSaleRecordRepository.plusPurchaseNumByFlashGoodsId(entity.getPurchaseNum(), entity.getFlashGoodsId());
    }

}
