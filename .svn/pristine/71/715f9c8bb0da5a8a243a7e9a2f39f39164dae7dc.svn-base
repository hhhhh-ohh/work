package com.wanmi.sbc.empower.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>达达配送接口响应</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Data
public class DadaOrderDetailVO implements Serializable {

    @Schema(description = "骑手姓名")
    private String transporterName;

    @Schema(description = "骑手电话")
    private String transporterPhone;

    @Schema(description = "骑手经度")
    private String transporterLng;

    @Schema(description = "骑手纬度")
    private String transporterLat;

    @Schema(description = "收货码")
    private String orderFinishCode;

    @Schema(description = "配送距离,单位为米")
    private Double distance;
}