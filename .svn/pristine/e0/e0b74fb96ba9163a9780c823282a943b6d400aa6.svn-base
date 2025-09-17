package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service;

import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;

/***
 * @description 订单校验
 * @author edz
 * @date 2022/2/23 17:10
 **/
public interface TradeBuyCheckInterface {

    /**
     * @description 商品限售校验
     * @author edz
     * @date: 2022/2/23 17:40
     * @param request
     * @return void
     */
    void validateOrderRestricted(GoodsRestrictedBatchValidateRequest request);

    /**
     * @description 渠道验证
     * @author edz
     * @date: 2022/3/18 16:00
     * @param paramsDataVO
     * @return void
     */
    void verifyChannelGoods(ParamsDataVO paramsDataVO);

    /**
     * @description 预约活动校验是否有资格
     * @author edz
     * @date: 2022/3/18 16:00
     * @param paramsDataVO
     * @return void
     */
    void validateAppointmentQualification(ParamsDataVO paramsDataVO);

    /**
     * @description 预售活动校验
     * @author edz
     * @date: 2022/3/18 16:01
     * @param paramsDataVO
     * @return void
     */
    void validateBookingSale(ParamsDataVO paramsDataVO);

    /**
     * @description 商品库存校验
     * @author edz
     * @date: 2022/3/18 16:01
     * @param paramsDataVO
     * @return void
     */
    void validateGoodsStock(ParamsDataVO paramsDataVO);

    /**
     * @description 校验组合购
     * @author  edz
     * @date: 2022/3/31 18:13
     * @param paramsDataVO
     * @return void
     **/
    void validateMarketingSuits(ParamsDataVO paramsDataVO);

    /**
     * @description 校验开店礼包
     * @author  edz
     * @date: 2022/4/19 16:27
     * @param paramsDataVO
     * @return void
     **/
    void validateStoreBag(ParamsDataVO paramsDataVO);
}
