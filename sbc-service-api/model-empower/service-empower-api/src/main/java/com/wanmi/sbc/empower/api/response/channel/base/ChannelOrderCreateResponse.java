package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @description 渠道订单创建响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderCreateResponse extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "渠道平台订单id")
    private List<String> channelOrderIds;

    @Schema(description = "外部平台订单id")
    private List<String> outOrderIds;
}
