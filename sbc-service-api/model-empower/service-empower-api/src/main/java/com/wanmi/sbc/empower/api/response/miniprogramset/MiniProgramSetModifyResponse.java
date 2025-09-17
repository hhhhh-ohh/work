package com.wanmi.sbc.empower.api.response.miniprogramset;

import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>小程序配置修改结果</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的小程序配置信息
     */
    @Schema(description = "已修改的小程序配置信息")
    private MiniProgramSetVO miniProgramSetVO;
}
