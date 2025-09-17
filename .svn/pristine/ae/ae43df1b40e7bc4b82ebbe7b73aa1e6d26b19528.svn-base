package com.wanmi.sbc.empower.api.response.wechatloginset;

import com.wanmi.sbc.empower.bean.vo.WechatLoginSetVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）微信授权登录配置信息response</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginSetByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信授权登录配置信息
     */
    @Schema(description = "微信授权登录配置信息")
    private WechatLoginSetVO wechatLoginSetVO;
}
