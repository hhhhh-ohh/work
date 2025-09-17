package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>订单商品快照验证请求结构</p>
 * Created by of628-wenzhi on 2017-07-13-上午9:15.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeItemConfirmRequest extends BaseRequest {

    private static final long serialVersionUID = -3106790833666168436L;
    
    /**
     * 商品信息，必传
     */
    @Schema(description = "商品信息")
    @NotEmpty
    @Valid
    private List<TradeItemRequest> tradeItems;

    /**
     * 订单营销信息快照
     */
    @Schema(description = "订单营销信息快照")
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 是否强制确认，用于营销活动有效性校验，true: 无效依然提交， false: 无效做异常返回
     */
    @Schema(description = "是否强制确认，用于营销活动有效性校验,true: 无效依然提交， false: 无效做异常返回")
    public boolean forceConfirm;

    @Schema(description = "收货地址区的地址码")
    public String areaId;

    @Schema(description = "是否o2o")
    public Boolean isO2O;

    @Schema(description = "门店id")
    public Long storeId;

    /** 地域编码-多级中间用|分割 */
    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

    @Schema(description = "社区团购")
    private CommunityBuyRequest communityBuyRequest;
}
