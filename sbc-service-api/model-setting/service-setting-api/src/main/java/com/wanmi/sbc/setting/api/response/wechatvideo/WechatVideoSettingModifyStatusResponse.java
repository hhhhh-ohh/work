package com.wanmi.sbc.setting.api.response.wechatvideo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhaiqiankun
 * @className VideoGoodsSettingModifyStatusResponse
 * @description 店铺设置接入结果
 * @date 2022/4/2 18:57
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingModifyStatusResponse implements Serializable {

    private static final long serialVersionUID = 6264382320902137151L;
    /**
     * 设置状态结果,0:未启用1:已启用
     */
    @Schema(description = "设置状态结果,0表示未开通，1表示开通，2表示禁用")
    private Integer status;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failReason;
}
