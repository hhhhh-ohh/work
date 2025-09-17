package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.goodstemplate.GoodsTemplateQueryProvider;
import com.wanmi.sbc.goods.api.request.goodstemplate.GoodsTemplateByGoodsIdRequest;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateByIdResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name =  "GoodsTemplate管理API", description =  "GoodsTemplateBaseController")
@RestController
@Validated
@RequestMapping(value = "/goodstemplate")
public class GoodsTemplateBaseController {
    @Autowired
    private GoodsTemplateQueryProvider goodsTemplateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "根据商品id获取模版信息")
    @GetMapping("/goods/{goodsId}")
    public BaseResponse<GoodsTemplateByIdResponse> joinGoodsDetails(@PathVariable String goodsId) {
        BaseResponse<GoodsTemplateByIdResponse> response = goodsTemplateQueryProvider.getByGoodsId(GoodsTemplateByGoodsIdRequest
                .builder()
                .goodsId(goodsId)
                .build());
        if (Objects.nonNull(response.getContext().getGoodsTemplateVO())){
            commonUtil.checkStoreId(response.getContext().getGoodsTemplateVO().getStoreId());
        }
        return response;
    }
}
