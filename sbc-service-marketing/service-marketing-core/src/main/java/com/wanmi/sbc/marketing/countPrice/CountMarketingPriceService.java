package com.wanmi.sbc.marketing.countPrice;

import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;

/**
 * @Author: wur
 * @Date: 2022/2/28 14:45
 */
public interface CountMarketingPriceService extends CountPriceService {

    /**
     * 计算营销费用  -   没标商品优惠金额 和 总优惠金额
     * @param countPriceItemVO   订单数据商品信息
     * @return
     */
    CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO);
}
