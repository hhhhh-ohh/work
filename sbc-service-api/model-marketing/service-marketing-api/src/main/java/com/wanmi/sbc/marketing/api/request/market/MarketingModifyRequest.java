package com.wanmi.sbc.marketing.api.request.market;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
public class MarketingModifyRequest extends MarketingBaseRequest{

    private static final long serialVersionUID = -680714222400623533L;

}
