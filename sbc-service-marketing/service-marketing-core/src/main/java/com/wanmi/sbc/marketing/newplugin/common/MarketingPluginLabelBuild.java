package com.wanmi.sbc.marketing.newplugin.common;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginLabelBuild
 * @description
 * @date 2022/3/29 17:01
 **/
@Slf4j
public class MarketingPluginLabelBuild {

    public static GoodsInfoDetailPluginResponse getDetailResponse(MarketingPluginLabelVO label){
        GoodsInfoDetailPluginResponse detailResponse = null;
        if(label!=null){
            detailResponse = new GoodsInfoDetailPluginResponse();
            List<MarketingPluginLabelVO> labelList = new ArrayList();
            labelList.add(label);
            detailResponse.setMarketingLabels(labelList);

            log.debug(" {} goodsDetail process", MarketingPluginType.fromId(label.getMarketingType()).getDescription());
        }
        return detailResponse;
    }
}
