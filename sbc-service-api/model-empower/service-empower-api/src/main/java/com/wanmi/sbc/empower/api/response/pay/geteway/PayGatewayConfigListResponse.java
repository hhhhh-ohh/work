package com.wanmi.sbc.empower.api.response.pay.geteway;

import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>支付网关配置response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:54.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayGatewayConfigListResponse implements Serializable{

    private static final long serialVersionUID = -6112956373933576159L;
    /**
     * 网关配置列表
     */
    @Schema(description = "网关配置列表")
    private List<PayGatewayConfigVO> gatewayConfigVOList;
}
