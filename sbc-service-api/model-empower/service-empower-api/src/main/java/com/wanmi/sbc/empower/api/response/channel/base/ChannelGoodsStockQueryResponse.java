package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsStockVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @description 渠道商品库存查询响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsStockQueryResponse extends BaseRequest {

    private static final long serialVersionUID = -1L;

    @Schema(description = "批量渠道商品id")
    private List<ChannelGoodsStockVO> channelGoodsStockList;
}
