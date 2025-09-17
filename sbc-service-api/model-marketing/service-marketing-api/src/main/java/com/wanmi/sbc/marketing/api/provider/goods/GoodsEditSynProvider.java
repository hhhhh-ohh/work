package com.wanmi.sbc.marketing.api.provider.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
*
 * @description
 * @author  wur
 * @date: 2022/8/29 18:02
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "GoodsEditSynProvider")
public interface GoodsEditSynProvider {


    /**
     * 新增满赠数据
     * @param addRequest 新增参数 {@link GoodsEditSynRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/goods-edit/syn")
    BaseResponse goodsEdit(@RequestBody @Valid GoodsEditSynRequest addRequest);
}
