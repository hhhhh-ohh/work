package com.wanmi.perseus.param;

import lombok.Data;

/**
 * @ClassName MarketingRequest
 * @description
 * @Author zhanggaolei
 * @Date 2021/2/2 14:18
 * @Version 1.0
 **/
@Data
public class MarketingRequest {

    /**
     * 营销活动id
     */
    private String marketingId;

    /**
     * 营销活动类型
     */
    private MarketingType marketingType;
}
