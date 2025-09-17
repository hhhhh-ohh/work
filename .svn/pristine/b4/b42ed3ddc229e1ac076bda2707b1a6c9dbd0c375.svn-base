package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginFlushCacheRequest
 * @description TODO
 * @date 2021/7/26 10:48 上午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginFlushCacheRequest extends BaseRequest {

    List<GoodsInfoMarketingCacheDTO> goodsInfoMarketingCacheDTOS;

    //是否删除
    DeleteFlag deleteFlag = DeleteFlag.NO;
}
