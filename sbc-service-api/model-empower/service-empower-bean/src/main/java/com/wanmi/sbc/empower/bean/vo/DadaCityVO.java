package com.wanmi.sbc.empower.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>达达配送城市接口响应</p>
 *
 * @author zhangwenchang
 */
@Data
public class DadaCityVO implements Serializable {

    @Schema(description = "城市名称")
    private String cityName;

    @Schema(description = "城市编号")
    private String cityCode;
}