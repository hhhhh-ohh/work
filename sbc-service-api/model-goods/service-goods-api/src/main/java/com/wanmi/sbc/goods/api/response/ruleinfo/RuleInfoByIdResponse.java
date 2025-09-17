package com.wanmi.sbc.goods.api.response.ruleinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.RuleInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）规则说明信息response</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 规则说明信息
     */
    @Schema(description = "规则说明信息")
    private RuleInfoVO ruleInfoVO;
}
