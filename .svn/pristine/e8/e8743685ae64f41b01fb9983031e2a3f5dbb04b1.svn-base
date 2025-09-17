package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className SellPlatformAuditCateVO
 * @description TODO
 * @date 2022/4/11 10:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
@Builder
public class SellPlatformAuditCateVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 一级类目
     */
    @Schema(description = "一级类目")
    @NotNull
    private Integer level1;

    /**
     * 二级类目
     */
    @Schema(description = "二级类目")
    @NotNull
    private Integer level2;

    /**
     * 三级类目
     */
    @Schema(description = "三级类目")
    @NotNull
    private Integer level3;

    /**
     * 资质材料，图片url，图片类型，最多不超过10张
     */
    @Schema(description = "资质材料，图片url，图片类型，最多不超过10张")
    @NotNull
    private List<String> certificate;

}