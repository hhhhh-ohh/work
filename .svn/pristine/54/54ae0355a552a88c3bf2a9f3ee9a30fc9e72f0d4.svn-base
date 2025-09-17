package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StorePageRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 店铺信息查询bff
 * Created by bail on 2017/11/29.
 */
@RestController
@Validated
@Tag(name = "StoreController", description = "mobile 店铺信息查询bff")
public class StoreController {
    @Autowired
//    private StoreService storeService;
    private StoreQueryProvider storeQueryProvider;

    /**
     * 店铺列表
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "店铺列表")
    @RequestMapping(value = "/stores", method = RequestMethod.POST)
    public BaseResponse<Page<StoreVO>> list(@RequestBody StorePageRequest queryRequest) {
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setStoreState(StoreState.OPENING);
        queryRequest.setGteContractStartDate(LocalDateTime.now());
        queryRequest.setLteContractEndDate(LocalDateTime.now());
        List<Integer> storeTypeList = Arrays.asList(StoreType.SUPPLIER.toValue(),StoreType.CROSS_BORDER.toValue());
        queryRequest.setStoreTypeList(storeTypeList);
        MicroServicePage<StoreVO> storePage = storeQueryProvider.page(queryRequest).getContext().getStoreVOPage();
        return BaseResponse.success(storePage);
    }

}
