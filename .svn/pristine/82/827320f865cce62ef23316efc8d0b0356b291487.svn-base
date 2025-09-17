package com.wanmi.sbc.empower.bean.vo.channel.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 渠道地址VO
 * @author wur
 * @date 2021/5/11 16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelAddressVO implements Serializable {
    private static final long serialVersionUID = 6246961037582549416L;

    @Schema(description = "渠道地址ID")
    private String addrId;

    @Schema(description = "渠道地址名")
    private String addrName;

    @Schema(description = "渠道地址级别 0 1 2 3")
    private Integer addrLevel;

    @Schema(description = "父级ID")
    private String parentId;
}
