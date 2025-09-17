package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelGoodsBuyNumDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @description 渠道商品库存查询请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsStockQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -1L;

    @NotEmpty
    @Schema(description = "批量渠道商品信息")
    private List<ChannelGoodsBuyNumDTO> goodsBuyNumList;

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

    @Override
    public void checkParam() {
        if (ThirdPlatformType.VOP.equals(thirdPlatformType)) {
            if (StringUtils.isBlank(thirdProvinceId)
                    || StringUtils.isBlank(thirdCityId)
                    || StringUtils.isBlank(thirdAreaId)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 购买数量必填
            if (goodsBuyNumList.stream().anyMatch(sku -> Objects.isNull(sku.getBuyNum()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}
