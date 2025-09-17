package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * @description 渠道地址响应
 * @author wur
 * @date 2021/5/13 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelAddressResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "渠道地址信息")
    private List<ChannelAddressVO> channelAddressList;
}
