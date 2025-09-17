package com.wanmi.sbc.setting.api.request.yunservice;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>云文件删除请求参数</p>
 * Created by of628-wenzhi on 2020-12-14-3:08 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class YunFileDeleteRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "待删除的远程云文件key集合")
    @NotEmpty
    private List<String> resourceKeys;
}
