package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wc
 * @className MarketingPluginPreRequest
 * @description TODO
 * @date 2022/2/17 10:48 上午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginPreRequest extends BaseRequest {

    /**
     * skuid
     */
    private String goodsInfoId;

    /**
     * 预热时间
     */
    private Long preTime;
}
