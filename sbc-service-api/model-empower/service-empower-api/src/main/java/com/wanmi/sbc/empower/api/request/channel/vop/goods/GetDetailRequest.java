package com.wanmi.sbc.empower.api.request.channel.vop.goods;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xufan
 * @Date: 2020/2/27 15:07
 * @Description: 查询ku详情请求参数
 *
 */
@Data
@NoArgsConstructor
public class GetDetailRequest extends VopBaseRequest {

    public GetDetailRequest(Long sku){
        this.sku = String.valueOf(sku);
    }

    public GetDetailRequest(String sku){
        this.sku = sku;
    }

    /**
     * 商品编号，只支持单个查询
     */
    private String sku;

    /**
     * 以下为商品维度扩展字段，暂时无用，非必填
     */
    private String queryExts;
}
