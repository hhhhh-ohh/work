package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @description 渠道配置响应
 * @author wur
 * @date 2021/5/13 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelConfigListResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "系统配置信息")
    private List<ChannelConfigVO> configVOList;
}
