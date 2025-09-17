package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 系统配置查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class ConfigQueryRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 键
     */
    @Schema(description = "键")
    private String configKey;

    /**
     * 键
     */
    @Schema(description = "键集合")
    private List<String> configKeyList;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String configType;

    /**
     * 状态
     */
    @Schema(description = "状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer status;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

}
