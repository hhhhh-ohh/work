package com.wanmi.sbc.setting.api.response.thirdplatformconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by feitingting on 2019/11/6.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPlatformConfigQueryResponse extends BasicResponse {

    @Schema(description = "系统配置信息")
    private List<ConfigVO> configVOList;
}
