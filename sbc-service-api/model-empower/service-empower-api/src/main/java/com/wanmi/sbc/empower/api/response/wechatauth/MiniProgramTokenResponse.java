package com.wanmi.sbc.empower.api.response.wechatauth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: saas_h
 * @description:
 * @author: Mr.Tian
 * @create: 2020-08-05 19:45
 **/


@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 操作日志列表
     */
    @Schema(description = "小程序accessToken")
    private String accessToken;
}
