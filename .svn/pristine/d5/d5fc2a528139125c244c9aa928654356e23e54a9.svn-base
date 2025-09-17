package com.wanmi.sbc.crm.api.request.customertag;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.dw.bean.enums.TagType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAutoTagRequest extends BaseRequest {


    /**
     * 会员id
     */
    @Schema(description = "会员id", required = true)
    @NotBlank
    private String customerId;


    /**
     * 标签类型
     */
    @Schema(description = "标签类型", required = true)
    private TagType tagType;
}
