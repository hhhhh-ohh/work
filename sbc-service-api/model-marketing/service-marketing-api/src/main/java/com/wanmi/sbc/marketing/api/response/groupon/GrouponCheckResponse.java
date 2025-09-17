package com.wanmi.sbc.marketing.api.response.groupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>拼团信息
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
public class GrouponCheckResponse extends BasicResponse {

    private static final long serialVersionUID = -3734558778559667422L;

}
