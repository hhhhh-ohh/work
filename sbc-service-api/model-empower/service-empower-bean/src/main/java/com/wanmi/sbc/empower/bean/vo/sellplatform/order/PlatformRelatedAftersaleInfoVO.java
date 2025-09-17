package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description   售后单
 * @author  wur
 * @date: 2022/4/13 11:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformRelatedAftersaleInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 售后单信息
     */
    @Schema(description = "售后单信息")
    private List<PlatformRelatedAfterSaleVO> aftersale_list;

}
