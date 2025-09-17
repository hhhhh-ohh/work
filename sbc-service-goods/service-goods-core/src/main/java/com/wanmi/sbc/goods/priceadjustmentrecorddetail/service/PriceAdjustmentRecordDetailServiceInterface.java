package com.wanmi.sbc.goods.priceadjustmentrecorddetail.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.api.response.price.adjustment.AdjustPriceExecuteResponse;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.priceadjustmentrecorddetail.model.root.PriceAdjustmentRecordDetail;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/***
 * <p>调价单详情表业务逻辑</p>
 * @className PriceAdjustmentRecordDetailServiceInterface
 * @author zhengyang
 * @date 2021/11/30 19:25
 **/
public interface PriceAdjustmentRecordDetailServiceInterface {

    /**
     * 定时调价任务
     *
     * @param adjustNo
     * @param storeId
     */
    @Transactional
    AdjustPriceExecuteResponse adjustPriceTaskExecute(String adjustNo, Long storeId, PluginType pluginType);

    /**
     * 立即调价
     *
     * @param adjustNo
     * @param storeId
     */
    @Transactional
    void adjustPriceNow(String adjustNo, Long storeId);

    /**
     * 改价确认
     *
     * @param adjustNo
     * @param storeId
     * @param effectiveTime
     */
    @Transactional
    void confirmAdjust(String adjustNo, Long storeId, LocalDateTime effectiveTime,boolean isAudit);

    /**
     * 调价执行失败,更新执行状态
     *
     * @param adjustNo
     * @param result
     * @param failReason
     */
    @Transactional
    void executeFail(String adjustNo, PriceAdjustmentResult result, String failReason);

    /**
     * 更新其他sku的阶梯价
     *
     * @param goodsRelMap
     */
    @Transactional
    void modifyOtherSku(Map<String, List<String>> goodsRelMap, Map<String, PriceAdjustmentRecordDetail> priceAdjustMap);
}
