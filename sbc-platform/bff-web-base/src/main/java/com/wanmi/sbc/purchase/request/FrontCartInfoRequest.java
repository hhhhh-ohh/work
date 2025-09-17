package com.wanmi.sbc.purchase.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FrontCartInfoRequest extends BaseRequest {

    /**
     * 商品信息
     */
	@Schema(description = "商品信息")
    private List<CartGoodsInfoRequest> goodsInfoList;

    /**
     * 登录用户
     */
	@Schema(description = "登录用户")
    private CustomerVO customer;

    /**
     * 邀请人
     */
	@Schema(description = "邀请人")
    private String inviteeId;

    /**
     * 区的区域码
     */
	@Schema(description = "区的区域码")
    private Long areaId;

    /**
     * 本地地址区域
     */
	@Schema(description = "本地地址区域")
    private PlatformAddress address;

    @Schema(description = "插件类型")
    private List<PluginType> pluginTypes;

}
