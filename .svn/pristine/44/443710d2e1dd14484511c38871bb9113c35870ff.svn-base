package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


/**
 * 修改SEO设置
 */
@Schema
@Data
public class SeoSettingModifyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 首页标题
     */
    @Schema(description = "首页标题")
    @Length(max = 200)
    private String indexTitle;

    /**
     * 首页关键字
     */
    @Schema(description = "首页关键字")
    @Length(max = 200)
    private String indexKeyword;

    /**
     * 首页描述
     */
    @Schema(description = "首页描述")
    @Length(max = 200)
    private String indexDescribe;
}
