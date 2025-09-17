package com.wanmi.sbc.marketing.api.response.plugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * <p>获取营销返回视图</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingPluginByGoodsInfoListAndCustomerLevelResponse extends BasicResponse {

    private static final long serialVersionUID = 4851715972034288549L;

    @Schema(description = "单品营销活动map<key为单品id，value为营销活动列表>")
    private HashMap<String, List<MarketingViewVO>> marketingMap;
}
