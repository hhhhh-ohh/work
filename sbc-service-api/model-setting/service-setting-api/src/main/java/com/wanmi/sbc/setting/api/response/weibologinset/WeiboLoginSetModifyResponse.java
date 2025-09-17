package com.wanmi.sbc.setting.api.response.weibologinset;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.WeiboLoginSetVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>微信登录配置修改结果</p>
 * @author lq
 * @date 2019-11-05 16:17:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeiboLoginSetModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的微信登录配置信息
     */
    @Schema(description = "已修改的微信登录配置信息")
    private WeiboLoginSetVO weiboLoginSetVO;
}
