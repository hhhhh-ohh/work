package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListWithoutDefaultByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListWithoutDefaultByStoreIdResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 店铺商品分类查询Controller
 * Author: bail
 * Time: 2017/11/29.10:21
 */
@Tag(name = "StoreCateBaseController", description = "店铺商品分类查询 API")
@RestController
@Validated
@RequestMapping("/storeCate")
public class StoreCateBaseController {

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    /**
     * 查询店铺商品分类List(扁平的)
     */
    @Operation(summary = "查询店铺商品分类List(扁平的)")
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<List<StoreCateResponseVO>> list(@RequestBody StoreCateListWithoutDefaultByStoreIdRequest request){
        BaseResponse<StoreCateListWithoutDefaultByStoreIdResponse> baseResponse = storeCateQueryProvider.listWithoutDefaultByStoreId(request);
        StoreCateListWithoutDefaultByStoreIdResponse storeCateListWithoutDefaultByStoreIdResponse = baseResponse.getContext();
        if (Objects.isNull(storeCateListWithoutDefaultByStoreIdResponse)){
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(storeCateListWithoutDefaultByStoreIdResponse.getStoreCateResponseVOList());
    }
}
