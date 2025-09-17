package com.wanmi.sbc.goods.api.response.ruleinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.RuleInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>规则说明分页结果</p>
 * @author zxd
 * @date 2020-05-25 18:55:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 规则说明分页结果
     */
    @Schema(description = "规则说明分页结果")
    private MicroServicePage<RuleInfoVO> ruleInfoVOPage;
}
