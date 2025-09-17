package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zhanggaolei
 * @className GoodsDetailPluginRequest
 * @description TODO
 * @date 2021/6/25 19:23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoPluginRequest extends MarketingPluginBaseRequest{

    private GoodsInfoSimpleVO goodsInfoPluginRequest;


    /***
     * 插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /***
     * 门店ID
     */
    private Long storeId;
}
