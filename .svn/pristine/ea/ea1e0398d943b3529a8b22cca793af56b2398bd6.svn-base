package com.wanmi.sbc.vas.api.request.channel;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.vas.api.request.VasBaseRequest;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @description   查询渠道运费
 * @author  wur
 * @date: 2021/9/24 15:53
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderFreightRequest extends VasBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "渠道商品列表")
    private List<ChannelGoodsInfoDTO> goodsInfoList;

    @NotNull
    @Schema(description = "收货人地址信息")
    private PlatformAddress address;
}
