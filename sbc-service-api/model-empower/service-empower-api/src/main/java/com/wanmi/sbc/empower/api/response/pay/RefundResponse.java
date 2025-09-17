package com.wanmi.sbc.empower.api.response.pay;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>退款请求返回结构</p>
 * Created by of628-wenzhi on 2018-08-18-下午2:36.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse implements Serializable {

    private static final long serialVersionUID = 6491764079121186091L;
    /**
     * 退款成功后返回的退款对象
     */
    @Schema(description = "退款成功后返回的退款对象")
    private Object object;
}
