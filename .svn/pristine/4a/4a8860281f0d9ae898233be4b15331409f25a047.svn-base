package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder.PlatformDefaultAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description  修改退货默认地址
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformUpdateShopAddressRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 小程序地址
     */
    @NotEmpty
    @Schema(description = "小程序地址")
    private String service_agent_path;

    @Schema(description = "客服联系方式")
    private String service_agent_phone;

    @Schema(description = "客服类型，支持多个，0: 小程序客服，1: 自定义客服path 2: 联系电话")
    private List<Integer> service_agent_type;

    @Schema(description = "默认收货地址信息")
    private PlatformDefaultAddressVO channelsDefaultAddressVO;

}
