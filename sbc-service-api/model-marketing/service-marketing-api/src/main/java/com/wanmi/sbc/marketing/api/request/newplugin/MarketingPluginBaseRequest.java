package com.wanmi.sbc.marketing.api.request.newplugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import lombok.*;

import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginBaseRequest
 * @description TODO
 * @date 2021/8/12 6:58 下午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginBaseRequest extends BaseRequest {

    private String customerId;

    private EnterpriseCheckState enterpriseCheckState;

    private MarketingPluginType marketingPluginType;

    /**
     * 查询数据终端，为了处理pc端分销和企业价逻辑
     */
    private String terminalSource;

    /**
     * 处理叠加标识，结合marketingPluginType使用
     * handlePosit=false ，只处理marketingPluginType指定的营销类型 允许叠加的不可处理
     */
    private Boolean handlePosit = Boolean.TRUE;

    /**
     * 需要排除的营销集合
     */
    private List<MarketingPluginType> excludeMarketingPluginList;
}
