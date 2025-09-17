package com.wanmi.sbc.vas.api.request.channel;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * @description 渠道订单验证请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderVerifyRequest extends VasBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "渠道订单列表")
    private List<ChannelOrderDTO> orderList;
}
