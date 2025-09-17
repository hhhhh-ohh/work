package com.wanmi.sbc.setting.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @description 第三方平台区域类
 * @author  daiyitian
 * @date 2021/5/13 14:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdAddress {


    @Schema(description = "省份id")
    private String provinceId;

    @Schema(description = "城市id")
    private String cityId;

    @Schema(description = "区县id")
    private String areaId;

    @Schema(description = "街道id")
    private String streetId;

    /**
     * @description 判断是否为null
     * @author  daiyitian
     * @date 2021/5/25 19:15
     * @return java.lang.Boolean
     **/
    public Boolean hasNull(){
        return StringUtils.isBlank(provinceId)
                || StringUtils.isBlank(cityId)
                || StringUtils.isBlank(areaId);
    }
}
