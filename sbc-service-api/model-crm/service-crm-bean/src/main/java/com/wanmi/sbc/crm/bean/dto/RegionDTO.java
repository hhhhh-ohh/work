package com.wanmi.sbc.crm.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-11
 * \* Time: 14:08
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDTO {

/*
    @Schema(description = "省份id")
    private String provinceId;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "城市id")
    private String regionId;

    @Schema(description = "城市名称")
    private String regionName;
*/

    @Schema(description = "省市id")
    private String regionId;

    @Schema(description = "省市名称")
    private String regionName;
}
