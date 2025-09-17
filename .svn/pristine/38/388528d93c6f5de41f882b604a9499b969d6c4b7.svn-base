package com.wanmi.sbc.empower.api.response.miniprogramset;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>小程序配置分页结果</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序配置分页结果
     */
    @Schema(description = "小程序配置分页结果")
    private MicroServicePage<MiniProgramSetVO> miniProgramSetVOPage;
}
