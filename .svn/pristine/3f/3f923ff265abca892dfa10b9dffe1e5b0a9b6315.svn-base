package com.wanmi.sbc.goods.api.request.ruleinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.RuleType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>规则说明查詢</p>
 *
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoByRuleTypeRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;


    /**
     * 规则类型 0:预约 1:预售
     */
    @Schema(description = "规则类型 0:预约 1:预售")
    private RuleType ruleType;

}