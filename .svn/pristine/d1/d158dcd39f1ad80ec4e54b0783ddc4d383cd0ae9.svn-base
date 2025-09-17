package com.wanmi.sbc.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByStandardIdRequest;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByStandardIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库SKU服务
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@Tag(name =  "商品库SKU基本服务", description =  "StandardSkuBaseController")
public class StandardSkuBaseController {

    @Autowired
    private StandardSkuQueryProvider standardSkuQueryProvider;

    /**
     * 获取商品库SKU列表信息
     *
     * @param standardId 商品库SPU编号
     * @return 商品库SKU列表信息
     */
    @Operation(summary = "获取商品库SKU列表信息")
    @Parameter(name = "standardId", description = "商品库SPUid",
            required = true)
    @RequestMapping(value = "/standard/skus/{standardId}", method = RequestMethod.GET)
    public BaseResponse<StandardSkuByStandardIdResponse> skusByStandardId(@PathVariable String standardId) {
        return standardSkuQueryProvider.listByStandardId(StandardSkuByStandardIdRequest.builder().standardId(standardId).build());
    }
}
