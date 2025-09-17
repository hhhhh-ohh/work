package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description   售后单详情
 * @author  wur
 * @date: 2022/4/13 11:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PlatformRelatedAfterSaleVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧售后单id
     */
    @Schema(description = "微信侧售后单id")
    private String aftersale_id;

    /**
     * 商家侧售后单id
     */
    @Schema(description = "商家侧售后单id")
    private String out_aftersale_id;

}
