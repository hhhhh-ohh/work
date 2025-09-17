package com.wanmi.sbc.empower.api.request.channel.vop.goods;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xufan
 * @Date: 2020/3/5 14:01
 * @Description: 商品上下架状态请求参数
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuStateRequest extends VopBaseRequest {

    /**
     * 商品编号，支持批量，以“,”（半角）分隔  (最高支持100个商品)
     */
    private String sku;
}
