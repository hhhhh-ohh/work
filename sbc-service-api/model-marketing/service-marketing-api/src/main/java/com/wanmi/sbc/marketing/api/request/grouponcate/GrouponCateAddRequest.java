package com.wanmi.sbc.marketing.api.request.grouponcate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

/**
 * <p>拼团活动信息表新增参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
public class GrouponCateAddRequest extends BaseRequest {
    private static final long serialVersionUID = -5841704766824220778L;

    /**
     * 拼团分类名称
     */
    @Schema(description = "拼团分类名称")
    @NotBlank
    @Length(max = 20)
    private String grouponCateName;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Length(max = 32)
    private String createPerson;

}