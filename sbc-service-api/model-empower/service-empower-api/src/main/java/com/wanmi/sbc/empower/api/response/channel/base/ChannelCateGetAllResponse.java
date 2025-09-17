package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelCateGetAllResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类")
    private List<ChannelGoodsCateVO> channelGoodsCateVOList;
}
