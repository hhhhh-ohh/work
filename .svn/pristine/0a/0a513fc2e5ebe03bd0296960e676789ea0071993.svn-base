package com.wanmi.sbc.vas.api.request.channel;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.vas.api.request.VasBaseRequest;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * @description 渠道商品状态获取请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsStatusGetRequest extends VasBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "渠道商品列表")
    private List<ChannelGoodsInfoDTO> goodsInfoList;

    @Schema(description = "外部平台订单id")
    private PlatformAddress address;
}
