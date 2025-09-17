package com.wanmi.sbc.marketing.api.request.grouponcate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

/**
 * <p>拼团活动信息表修改参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
public class GrouponCateModifyRequest extends BaseRequest {
    private static final long serialVersionUID = -1526557909207838417L;

    /**
     * 拼团分类Id
     */
    @Schema(description = "拼团分类Id")
    @NotBlank
    @Length(max = 32)
    private String grouponCateId;

    /**
     * 拼团分类名称
     */
    @Schema(description = "拼团分类名称")
    @NotBlank
    @Length(max = 20)
    private String grouponCateName;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @Length(max = 32)
    private String updatePerson;
}