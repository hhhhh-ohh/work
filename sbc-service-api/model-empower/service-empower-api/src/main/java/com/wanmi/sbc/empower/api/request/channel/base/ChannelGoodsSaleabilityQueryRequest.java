package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelGoodsBuyNumDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @description 渠道商品可售性查询请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsSaleabilityQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -1L;

    @NotEmpty
    @Schema(description = "批量渠道商品id")
    private List<ChannelGoodsBuyNumDTO> thirdSkuIdList;

    @Schema(description = "批量渠道省份id")
    private String thirdProvinceId;

    @Schema(description = "批量渠道城市id")
    private String thirdCityId;

    @Schema(description = "批量渠道区县id")
    private String thirdAreaId;

    @Schema(description = "批量渠道街道id")
    private String thirdStreetId;

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;
}
