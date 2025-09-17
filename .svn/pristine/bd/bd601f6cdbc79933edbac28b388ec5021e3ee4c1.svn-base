package com.wanmi.sbc.empower.api.response.sellplatform.cate;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckResponse
 * @description TODO
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCateResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 类目ID
     */
    @Schema(description = "类目ID")
    private int third_cat_id;

    /**
     * 类目名称
     */
    @Schema(description = "类目名称")
    private String third_cat_name;

    /**
     * 类目资质
     */
    @Schema(description = "类目资质")
    private String qualification;

    /**
     * 类目资质类型,0:不需要,1:必填,2:选填
     */
    @Schema(description = "类目资质类型,0:不需要,1:必填,2:选填")
    private int qualification_type;

    /**
     * 商品资质
     */
    @Schema(description = "商品资质")
    private String product_qualification;

    /**
     * 商品资质类型,0:不需要,1:必填,2:选填
     */
    @Schema(description = "商品资质类型,0:不需要,1:必填,2:选填")
    private String product_qualification_type;

    /**
     * 二级类目ID
     */
    @Schema(description = "二级类目ID")
    private int second_cat_id;

    /**
     * 二级类目名称
     */
    @Schema(description = "二级类目名称")
    private String second_cat_name;

    /**
     * 一级类目ID
     */
    @Schema(description = "一级类目ID")
    private int first_cat_id;

    /**
     * 一级类目名称
     */
    @Schema(description = "一级类目名称")
    private String first_cat_name;
}