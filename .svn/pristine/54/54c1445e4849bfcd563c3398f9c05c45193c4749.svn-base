package com.wanmi.sbc.vas.api.response.channel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 渠道商品状态获取响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsStatusGetResponse extends BasicResponse {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "渠道平台商品列表")
    private List<ChannelGoodsInfoVO> goodsInfoList = new ArrayList<>();

    @Schema(description = "需要下架供应商商品id")
    private List<String> offAddedSkuId = new ArrayList<>();
}
