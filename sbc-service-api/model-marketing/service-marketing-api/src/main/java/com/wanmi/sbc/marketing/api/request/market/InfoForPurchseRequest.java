package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.bean.vo.GoodsInfoMarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoForPurchseRequest extends BaseRequest {

    @NotEmpty
    private List<GoodsInfoMarketingVO> goodsInfos;

    private CustomerVO customer;

    private Map<Long, CommonLevelVO> storeLevelsMap;

    private PluginType pluginType;
}
