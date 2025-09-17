package com.wanmi.sbc.empower.api.response.pay.geteway;

import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>支付网关信息Response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:23.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class PayGatewayResponse extends PayGatewayVO implements Serializable{

    private static final long serialVersionUID = 5473662282421786840L;
}
