package com.wanmi.sbc.marketing.api.request.grouponcate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * <p>单个删除拼团活动信息表请求参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCateDelByIdRequest extends BaseRequest {
    private static final long serialVersionUID = -7368082532695123844L;

    /**
     * 拼团分类Id
     */
    @Schema(description = "拼团分类Id")
    @NotBlank
    @Length(max = 32)
    private String grouponCateId;

    /**
     * 删除人
     */
    @Schema(description = "创建人")
    @Length(max = 32)
    private String delPerson;
}