package com.wanmi.sbc.storecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreIdResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 店铺分类管理Controller
 * Author: bail
 * Time: 2017/11/14.10:21
 */
@RestController
@Validated
@RequestMapping("/storeCate")
@Tag(name = "BossStoreCateController", description = "店铺分类管理API")
public class BossStoreCateController {

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;


    /**
     * 查询店铺商品分类List
     */
    @Operation(summary = "查询店铺商品分类List")
    @Parameter(name = "storeId",
            description = "店铺Id", required = true)
    @RequestMapping(value = "/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<StoreCateResponseVO>> list(@PathVariable Long storeId) {
        BaseResponse<StoreCateListByStoreIdResponse> baseResponse =
                storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeId));
        StoreCateListByStoreIdResponse storeCateListByStoreIdResponse = baseResponse.getContext();
        if (Objects.isNull(storeCateListByStoreIdResponse)) {
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(storeCateListByStoreIdResponse.getStoreCateResponseVOList());
    }

}
