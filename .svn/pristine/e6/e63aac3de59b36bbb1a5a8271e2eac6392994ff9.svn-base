package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeCreateDTO extends TradeRemedyDTO {

    private static final long serialVersionUID = 3529565997588014310L;

    @Schema(description = "自定义")
    private String custom;

    /** 地域编码-多级中间用|分割 */
    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

}
