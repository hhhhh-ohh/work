package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelByCustomerIdAndStoreIdRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelListRequest;
import com.wanmi.sbc.customer.api.response.storelevel.StoreLevelByCustomerIdAndStoreIdResponse;
import com.wanmi.sbc.customer.api.response.storelevel.StoreLevelListResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @author yang
 * @since 2019/3/7
 */
@Tag(name = "StoreLevelBaseController", description = "店铺等级信息 API")
@RestController
@Validated
@RequestMapping("/store")
public class StoreLevelBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    /**
     * 查询店铺等级信息
     *
     * @param storeId
     * @return
     */
    @Operation(summary = "查询店铺等级信息")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/level/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<StoreLevelListResponse> queryStoreLevelList(@PathVariable Long storeId) {
        StoreLevelListRequest request = StoreLevelListRequest.builder().storeId(storeId).build();
        return BaseResponse.success(storeLevelQueryProvider.listAllStoreLevelByStoreId(request).getContext());
    }

    /**
     * 客户该店铺等级信息
     *
     * @param storeId
     * @return
     */
    @Operation(summary = "客户该店铺等级信息")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/customer/level/{storeId}", method = RequestMethod.GET)
    public BaseResponse<StoreLevelByCustomerIdAndStoreIdResponse> queryStoreCustomerLevel(@PathVariable Long storeId) {
        StoreLevelByCustomerIdAndStoreIdRequest request = StoreLevelByCustomerIdAndStoreIdRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .storeId(storeId)
                .build();
        return BaseResponse.success(storeLevelQueryProvider.getByCustomerIdAndStoreId(request).getContext());
    }

}
