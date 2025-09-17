package com.wanmi.sbc.empower.api.response.miniprogramset;

import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据type查询小程序配置信息response</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetByTypeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序配置信息
     */
    @Schema(description = "小程序配置信息")
    private MiniProgramSetVO miniProgramSetVO;
}
