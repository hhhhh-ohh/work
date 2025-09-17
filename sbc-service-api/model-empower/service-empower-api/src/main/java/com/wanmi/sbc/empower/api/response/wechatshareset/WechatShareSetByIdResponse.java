package com.wanmi.sbc.empower.api.response.wechatshareset;

import com.wanmi.sbc.empower.bean.vo.WechatShareSetVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）微信分享配置信息response</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatShareSetByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信分享配置信息
     */
    @Schema(description = "微信分享配置信息")
    private WechatShareSetVO wechatShareSetVO;
}
