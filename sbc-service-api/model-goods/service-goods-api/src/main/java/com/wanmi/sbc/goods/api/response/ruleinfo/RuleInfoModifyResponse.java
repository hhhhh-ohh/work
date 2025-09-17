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
 * <p>规则说明修改结果</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的规则说明信息
     */
    @Schema(description = "已修改的规则说明信息")
    private RuleInfoVO ruleInfoVO;
}
