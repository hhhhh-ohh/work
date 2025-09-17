package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service;

import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;

/**
 * @description 立即购买
 * @author edz
 * @date 2022/2/23 16:11
 */
public interface TradeBuyInterface {

    /**
     * @description 所有的数据查询及简单检验
     * @author edz
     * @date: 2022/2/23 16:11
     * @return void
     */
    void queryData(ParamsDataVO paramsDataVO);

    /**
     * @description 业务校验
     * @author edz
     * @date 2022/3/18 14:08
     */
    void check(ParamsDataVO paramsDataVO);

    /**
     * @description 拼装订单
     * @author edz
     * @date 2022/3/18 14:08
     */
    void assembleTrade(ParamsDataVO paramsDataVO);

    /**
     * @description 拼装营销信息
     * @author edz
     * @date 2022/3/18 14:08
     */
    void getMarketing(ParamsDataVO paramsDataVO);

    /**
     * @description 构建订单快照且入缓存
     * @author edz
     * @date 2022/3/18 14:09
     */
    void saveTrade(ParamsDataVO paramsDataVO);
}
