package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsSaleabilityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @description 渠道商品可售性查询响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsSaleabilityQueryResponse extends BaseRequest {

    private static final long serialVersionUID = -1L;

    @Schema(description = "不可售的第三方商品id，用于同步状态")
    private List<String> offSaleSkuIdList;

    @Schema(description = "下架的第三方商品id，用于同步状态")
    private List<String> offAddedSkuIdList;

    @Schema(description = "购买受限的第三方商品id")
    private List<String> limitSkuIdList;
}
