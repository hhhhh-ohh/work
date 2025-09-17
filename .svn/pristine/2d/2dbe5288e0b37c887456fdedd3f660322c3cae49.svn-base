package com.wanmi.sbc.setting.stockWarning.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.bean.vo.StockWarningVO;
import com.wanmi.sbc.setting.stockWarning.model.root.StockWarning;
import com.wanmi.sbc.setting.stockWarning.repository.StockWarnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("StockWarningService")
public class StockWarningService {

    @Autowired
    private StockWarnRepository stockWarnRepository;

    /**
     * 批量修改isWarning的状态
     * @author 马连峰
     */
    @Transactional(rollbackFor = Exception.class)
    public int modifyIsWarning(Long storeId) {
        return stockWarnRepository.modifyIsWarning(storeId);
    }

    /**
     * 查询商品是否预警
     */
    @Transactional
    public BoolFlag findIsWarning (Long storeId, String skuId) {
        return stockWarnRepository.findIsWarning(storeId,skuId);
    }

    /**
     * 新增商家库存预警信息
     * @author 马连峰
     */
    @Transactional
    public StockWarning add(StockWarning entity) {
        stockWarnRepository.save(entity);
        return entity;
    }

    /**
     * 删除商家库存预警信息
     * @author 马连峰
     */
    @Transactional
    public void delete(String skuId) {
        stockWarnRepository.deleteBySkuId(skuId);
    }

    /**
     * 将实体包装成VO
     * @author 马连峰
     */
    public StockWarningVO wrapperVo(StockWarning stockWarning) {
        if (stockWarning != null){
            StockWarningVO stockWarningVO = KsBeanUtil.convert(stockWarning, StockWarningVO.class);
            return stockWarningVO;
        }
        return null;
    }
}
