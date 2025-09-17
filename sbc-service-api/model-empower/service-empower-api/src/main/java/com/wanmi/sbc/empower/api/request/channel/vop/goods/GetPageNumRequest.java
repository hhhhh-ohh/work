package com.wanmi.sbc.empower.api.request.channel.vop.goods;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: xufan
 * @Date: 2020/2/26 15:49
 * @Description: 查询商品池编号请求参数
 *
 */
@Data
@Builder
public class GetPageNumRequest extends VopBaseRequest {

    /**
     * contractSkuPoolExt
     */
    private String queryExts;
}
