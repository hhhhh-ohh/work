package com.wanmi.sbc.account.api.response.credit.account;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CreditAccountCheckResponse
 * @Description 在线还款校验账户是否已还款，是否还款中
 * @author chenli
 * @date 2021/3/3 16:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditAccountCheckResponse extends BasicResponse {

    private static final long serialVersionUID = 3866734696033194504L;
    /**
     * 是否还款中 true 还款中 false 否
     */
    @Schema(description = "是否还款中 true 还款中 false 否")
    private Boolean waitRepay;
}
