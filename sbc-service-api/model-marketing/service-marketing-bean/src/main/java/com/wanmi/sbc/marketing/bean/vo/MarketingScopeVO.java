package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 14:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingScopeVO extends BasicResponse {

    private static final long serialVersionUID = 6067051217747580996L;
    /**
     * 货品与促销规则表Id
     */
    @Schema(description = "营销和商品关联表Id")
    private Long marketingScopeId;

    /**
     * 促销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 促销范围Id
     */
    @Schema(description = "营销范围Id")
    private String scopeId;

}
