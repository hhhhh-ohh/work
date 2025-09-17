package com.wanmi.sbc.empower.api.provider.channel.linkedmall.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallCateChainByGomallodsIdRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallCategoryChainByGoodsIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.empower.name}" ,contextId = "LinkedMallCateQueryProvider")
public interface LinkedMallCateQueryProvider {

    /**
     * 根据goodsId查询类目链
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/cate/get-gategory-chain-by-goodsId")
    BaseResponse<LinkedMallCategoryChainByGoodsIdResponse> getCategoryChainByGoodsId(@RequestBody @Valid LinkedMallCateChainByGomallodsIdRequest linkedMallCateChainByGoodsIdRequest);
}
