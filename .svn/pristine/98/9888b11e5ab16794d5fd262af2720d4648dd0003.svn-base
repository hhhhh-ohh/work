package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 企业购插件
 *
 * @author zhanggaolei
 * @className MarketingEnterprisePlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.ENTERPRISE_PRICE)
public class MarketingEnterprisePlugin implements MarketingPluginInterface {


    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelVO.class));
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        return this.setLabel(request,MarketingPluginSimpleLabelVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request,MarketingPluginLabelDetailVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request,MarketingPluginLabelDetailVO.class);
    }

    private <T extends MarketingPluginSimpleLabelVO> T setLabel(
            GoodsInfoPluginRequest request, Class<T> c) {
        if (Objects.equals(
                request.getGoodsInfoPluginRequest().getEnterPriseAuditState(),
                EnterpriseAuditState.CHECKED) &&
                (StringUtils.isBlank(request.getCustomerId()) ||
                        (StringUtils.isNotBlank(request.getCustomerId())
                                && Objects.nonNull(request.getEnterpriseCheckState())
                                && request.getEnterpriseCheckState() == EnterpriseCheckState.CHECKED)
                )
        ) {
            // 填充营销描述<营销编号,描述>
            MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
            label.setMarketingType(MarketingPluginType.ENTERPRISE_PRICE.getId());
            label.setMarketingDesc("企业价");
            label.setPluginPrice(request.getGoodsInfoPluginRequest().getEnterPrisePrice());
            return (T) label;
        }
        return null;
    }
}
