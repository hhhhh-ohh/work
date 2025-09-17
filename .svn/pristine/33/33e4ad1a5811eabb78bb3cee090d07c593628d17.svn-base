package com.wanmi.sbc.vas.api.request.sellplatform.promoter;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  SellPlatformPromoterListRequest  查询推广员列表
 * @author  wur
 * @date: 2022/4/13 15:02
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellPlatformPromoterListRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 第x页，大于等于1
     */
    private Integer page;

    /**
     * 每页订单数，上限100
     */
    private Integer page_size;
}
