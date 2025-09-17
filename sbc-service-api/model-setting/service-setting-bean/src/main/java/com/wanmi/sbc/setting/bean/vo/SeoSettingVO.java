package com.wanmi.sbc.setting.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * SEO设置VO
 */
@Schema
@Data
public class SeoSettingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 首页标题
     */
    @Schema(description = "首页标题")
    private String indexTitle;

    /**
     * 首页关键字
     */
    @Schema(description = "首页关键字")
    private String indexKeyword;

    /**
     * 首页描述
     */
    @Schema(description = "首页描述")
    private String indexDescribe;
}
