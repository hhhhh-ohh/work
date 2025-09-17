package com.wanmi.sbc.empower.api.request.channel.vop.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author EDZ
 * @className VopSkuTempRequest
 * @description SKU临时表查询请求
 * @date 2021/5/12 16:22
 **/
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VopSpuTempRequest extends BaseQueryRequest {
}
