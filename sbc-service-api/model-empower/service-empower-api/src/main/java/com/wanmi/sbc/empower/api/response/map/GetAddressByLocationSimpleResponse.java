package com.wanmi.sbc.empower.api.response.map;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className MapResponse
 * @description 经纬度反查地址信息
 * @author    张文昌
 * @date      2021/7/23 11:43
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressByLocationSimpleResponse implements Serializable {

    private static final long serialVersionUID = -6223541246044169782L;

    @Schema(description = "区域编码")
    private String adcode;

    @Schema(description = "所在乡镇/街道")
    private String township;

    @Schema(description = "乡镇街道编码")
    private String towncode;
}
