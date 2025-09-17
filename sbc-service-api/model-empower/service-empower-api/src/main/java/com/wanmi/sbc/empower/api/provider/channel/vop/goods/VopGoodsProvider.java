package com.wanmi.sbc.empower.api.provider.channel.vop.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelCheckSkuStateRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.goods.*;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelCheckStuStateResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author EDZ
 * @className GoodsProider
 * @description 商品API
 * @date 2021/5/11 14:31
 **/
@FeignClient(value = "${application.empower.name}", contextId = "VopGoodsProvider")
public interface VopGoodsProvider {
    /**
     * 查询商品售卖价
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/goods/getSellingPrice")
    BaseResponse<List<SkuSellingPriceResponse>> getSellingPrice(@RequestBody @Valid SkuSellingPriceRequest request);

    /**
     * 验证商品可售性
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/goods/checkSku")
    BaseResponse<List<CheckSkuResponse>> checkSku(@RequestBody @Valid CheckSkuRequest request);
}
