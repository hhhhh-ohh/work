package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className GoodsBrandCenterVO
 * @description 品牌中心（请勿删增字段，品牌中心返回数据较多，精简字段）
 * @date 2021/6/21 16:39
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandCenterVO extends BasicResponse {

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 品牌logo
     */
    @Schema(description = "品牌名称")
    private String logo;

    /**
     * 别名
     */
    @Schema(description = "品牌名称")
    private String nickName;
}
