package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 客户等级查询请求参数
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
public class CustomerLevelPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 是否是默认 0：否 1：是
     */
    @Schema(description = "是否是默认", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isDefault;

    /**
     * 删除标记 0未删除 1已删除
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

}
