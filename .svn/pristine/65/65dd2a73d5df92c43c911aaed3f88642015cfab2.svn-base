package com.wanmi.sbc.setting.api.response.statisticssetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>微信设置响应结果</p>
 * @author dyt
 * @date 2020-11-05 16:15:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QmStatisticsSettingResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;

	 @Schema(description = "appkey")
    private String appKey;

    @Schema(description = "app_secret")
    private String appSecret;

    @Schema(description = "启用状态 0:未启用1:已启用")
    private Integer status;

    /**
     * api地址
     */
    @Schema(description = "api地址")
    private String apiUrl;

}