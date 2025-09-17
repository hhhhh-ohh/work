package com.wanmi.sbc.customerserver;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2019-12-30 16:10
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigResponse extends BasicResponse {
    @Schema(description = "QQ客服")
    private CustomerServiceSettingVO onlineServiceVO;

    @Schema(description = "阿里云客服")
    private SystemConfigVO systemConfigVO;

    @Schema(description = "企微客服")
    private CustomerServiceSettingVO weChatServiceVO;

    @Schema(description = "网易七鱼客服")
    private CustomerServiceSettingVO qiYuServiceVO;
}