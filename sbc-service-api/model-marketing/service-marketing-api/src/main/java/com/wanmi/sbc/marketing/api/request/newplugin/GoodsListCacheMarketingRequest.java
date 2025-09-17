package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description  查询商品缓存中的营销活动 （秒杀 限时购 拼团 预约 预售）
 * @author  wur
 * @date: 2022/3/17 11:46
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListCacheMarketingRequest extends BaseRequest {

    private List<String> goodsInfoIdList;
}
