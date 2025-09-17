package com.wanmi.sbc.common.base;

import com.wanmi.sbc.common.enums.ChannelType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:25 2019/3/1
 * @Description: 分销渠道
 */
@Schema
@Data
public class DistributeChannel {

    /**
     * 分销渠道类型（默认PC_MALL）
     */
    @Schema(description = "分销渠道类型")
    private ChannelType channelType = ChannelType.PC_MALL;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    private String inviteeId;

}
