package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>获取营销入参结构</p>
 * @author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingPluginByGoodsInfoListAndCustomerRequest extends BaseRequest {

    private static final long serialVersionUID = -4324111781295319467L;

    @Schema(description = "商品信息列表")
    @NotEmpty
    private List<GoodsInfoDTO> goodsInfoList;

    /**
     * 当前客户
     */
    @Schema(description = "客户信息")
    private String customerId;
//    private CustomerDTO customerDTO;


    @Schema(description = "插件类型")
    private PluginType pluginType;

    @Schema(description = "门店ID")
    private Long storeId;
}
