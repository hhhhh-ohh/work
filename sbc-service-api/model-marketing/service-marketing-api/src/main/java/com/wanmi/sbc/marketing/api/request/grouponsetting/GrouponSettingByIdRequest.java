package com.wanmi.sbc.marketing.api.request.grouponsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询拼团活动信息表请求参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:19:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponSettingByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull
    private String id;
}