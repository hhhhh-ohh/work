package com.wanmi.sbc.goods.flashpromotionactivity.service;

import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashPromotionActivityQueryRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityModifyStatusRequest;
import com.wanmi.sbc.goods.flashpromotionactivity.model.root.FlashPromotionActivity;
import com.wanmi.sbc.goods.flashpromotionactivity.repository.FlashPromotionActivityRepository;
import com.wanmi.sbc.goods.flashsalegoods.repository.FlashSaleGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>抢购商品表业务逻辑</p>
 *
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@Service("flashPromotionActivityService")
public class FlashPromotionActivityService {

    @Autowired
    private FlashPromotionActivityRepository flashPromotionActivityRepository;

    @Autowired
    private FlashSaleGoodsRepository flashSaleGoodsRepository;

    /**
     * 限时购启动暂停活动
     *
     * @author xufeng
     */
    @Transactional
    public void modifyByActivityId(FlashPromotionActivityModifyStatusRequest flashPromotionActivityModifyStatusRequest) {
        flashPromotionActivityRepository.modifyByActivityId(flashPromotionActivityModifyStatusRequest.getStatus(),
                flashPromotionActivityModifyStatusRequest.getActivityId());
        flashSaleGoodsRepository.modifyByActivityId(flashPromotionActivityModifyStatusRequest.getStatus(),
                flashPromotionActivityModifyStatusRequest.getActivityId());
    }

    /**
     * 分页查询抢购活动表
     *
     * @author xufeng
     */
    public Page<FlashPromotionActivity> page(FlashPromotionActivityQueryRequest queryReq) {
        Page<FlashPromotionActivity> page = flashPromotionActivityRepository.findAll(
                FlashPromotionActivityWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
        return page;
    }

    /**
     * 单个查询抢购活动表
     *
     * @author xufeng
     */
    public FlashPromotionActivity findByActivityId(String activityId) {
        return flashPromotionActivityRepository.findByActivityId(activityId);
    }
}
