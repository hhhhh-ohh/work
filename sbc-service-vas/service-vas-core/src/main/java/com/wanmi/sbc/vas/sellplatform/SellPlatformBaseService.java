package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.enums.SellPlatformType;

/**
 * @Author: wur
 * @Date: 2022/4/15 11:06
 */
public interface SellPlatformBaseService {

    SellPlatformType getType();

    SellPlatformServiceType getServiceType();

}
