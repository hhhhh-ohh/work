package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.SellPlatformType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 第三方平台物流公司详情展示
 * @author malianfeng
 * @date 2022/4/26 19:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdExpressCompanyVO implements Serializable {

    private static final long serialVersionUID = -7014174263074953392L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id	;

    /**
     * 物流公司名称
     */
    @Schema(description = "物流公司名称")
    private String expressName;

    /**
     * 物流公司代码
     */
    @Schema(description = "物流公司名称")
    private String expressCode;

    /**
     * 第三方代销平台(0:微信视频号)
     */
    @Schema(description = "第三方代销平台，0:微信视频号")
    private SellPlatformType sellPlatformType;
}

