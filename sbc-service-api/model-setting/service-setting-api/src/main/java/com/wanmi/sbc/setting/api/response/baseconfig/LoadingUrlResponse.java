package com.wanmi.sbc.setting.api.response.baseconfig;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄昭
 * @className LoadingUrlResponse
 * @description TODO
 * @date 2022/4/19 10:40
 **/
@Data
@Schema
public class LoadingUrlResponse implements Serializable {

    private static final long serialVersionUID = -5802782281375295578L;
    /**
     * 加载地址
     */
    @Schema(description = "加载地址")
    private String loadingUrl;
}