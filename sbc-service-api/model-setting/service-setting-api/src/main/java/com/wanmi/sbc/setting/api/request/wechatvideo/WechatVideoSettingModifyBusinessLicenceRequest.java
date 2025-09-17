package com.wanmi.sbc.setting.api.request.wechatvideo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhaiqiankun
 * @className WechatVideoSettingModifyBusinessLicenceRequest
 * @description 营业执照设置
 * @date 2022/4/12 14:54
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingModifyBusinessLicenceRequest implements Serializable {

    /**
     * 营业执照文件链接
     */
    @Schema(description = "营业执照文件链接")
    private String businessLicence;

}
