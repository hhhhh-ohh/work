package com.wanmi.sbc.setting.api.response.yunservice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 云配置YunConfigByIdResponse
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunConfigListResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    @Schema(description = "云服务配置信息")
    private List<SystemConfigVO> systemConfigVOS;
}
