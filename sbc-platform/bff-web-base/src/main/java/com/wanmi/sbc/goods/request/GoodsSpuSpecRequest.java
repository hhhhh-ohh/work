package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.PlatformAddress;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className GoodsSpuSpecRequest
 * @description TODO
 * @date 2022/9/22 15:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsSpuSpecRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "是否获取秒杀、限时购库存")
    private Boolean flashStockFlag;

    @Schema(description = "省份id")
    private String provinceId;

    @Schema(description = "城市id")
    private String cityId;

    @Schema(description = "区县id")
    private String areaId;

    @Schema(description = "街道id")
    private String streetId;

}