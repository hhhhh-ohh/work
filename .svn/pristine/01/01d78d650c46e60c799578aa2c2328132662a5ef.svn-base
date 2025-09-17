package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsListPluginRequest
 * @description TODO
 * @date 2021/6/25 19:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListPluginRequest extends MarketingPluginBaseRequest {

    private List<GoodsInfoSimpleVO> goodsInfoPluginRequests;

    /***
     * 插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /***
     * 门店ID
     */
    private Long storeId;

    /**
     * 企业会员标识
     */
    @Builder.Default
    private Boolean iepCustomerFlag=false;
}
