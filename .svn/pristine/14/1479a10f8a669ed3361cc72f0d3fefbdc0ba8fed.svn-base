package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统配置更新请求
 * @author Created by liutao on 2019/2/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@EqualsAndHashCode(callSuper = true)
public class ConfigListModifyRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    @Schema(description = "系统配置更新请求信息")
    List<ConfigVO> configRequestList;
}
