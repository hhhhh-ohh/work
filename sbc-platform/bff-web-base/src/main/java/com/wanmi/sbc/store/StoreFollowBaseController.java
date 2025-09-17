package com.wanmi.sbc.store;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.follow.StoreCustomerFollowProvider;
import com.wanmi.sbc.customer.api.provider.follow.StoreCustomerFollowQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.follow.StoreCustomerFollowAddRequest;
import com.wanmi.sbc.customer.api.request.follow.StoreCustomerFollowCountRequest;
import com.wanmi.sbc.customer.api.request.follow.StoreCustomerFollowDeleteRequest;
import com.wanmi.sbc.customer.api.request.follow.StoreCustomerFollowExistsBatchRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * 店铺关注Controller
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "StoreFollowBaseController", description = "店铺关注 API")
@RestController
@Validated
@RequestMapping("/store")
public class StoreFollowBaseController {

    @Autowired
    private StoreCustomerFollowQueryProvider storeCustomerFollowQueryProvider;

    @Autowired
    private StoreCustomerFollowProvider storeCustomerFollowProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 新增店铺关注
     * @param addRequest 数据
     * @return 结果
     */
    @Operation(summary = "新增店铺关注")
    @RequestMapping(value = "/storeFollow", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse add(@Valid @RequestBody StoreCustomerFollowAddRequest addRequest) {
        addRequest.setCustomerId(commonUtil.getOperatorId());
        addRequest.setTerminalSource(commonUtil.getTerminal());
        storeCustomerFollowProvider.addStoreCustomerFollow(addRequest);

        // 增加成长值
        customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .type(OperateType.GROWTH)
                .serviceType(GrowthValueServiceType.FOCUSONSTORE)
                .content(JSONObject.toJSONString(Collections.singletonMap("storeId", addRequest.getStoreId())))
                .build());
        // 增加积分
        customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .type(OperateType.GROWTH)
                .serviceType(PointsServiceType.FOCUSONSTORE)
                .content(JSONObject.toJSONString(Collections.singletonMap("storeId", addRequest.getStoreId())))
                .build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 取消店铺关注
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "取消店铺关注")
    @RequestMapping(value = "/storeFollow", method = RequestMethod.DELETE)
    public BaseResponse delete(@Valid @RequestBody StoreCustomerFollowDeleteRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return storeCustomerFollowProvider.deleteStoreCustomerFollow(request);
    }

    /**
     * 批量验证店铺是否已关注
     * @return 结果，相应的SkuId就是已收藏的商品ID
     */
    @Operation(summary = "批量验证店铺是否已关注")
    @RequestMapping(value = "/isStoreFollow", method = RequestMethod.POST)
    public BaseResponse<List<Long>> isGoodsFollow(@Valid @RequestBody StoreCustomerFollowExistsBatchRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return BaseResponse.success(storeCustomerFollowQueryProvider.queryStoreCustomerFollowByStoreIds(request)
                .getContext().getStoreIds());
    }

    /**
     * 统计客户关注数量
     * @return 关注数量
     */
    @Operation(summary = "统计客户关注数量")
    @RequestMapping(value = "/storeFollowNum", method = RequestMethod.GET)
    public BaseResponse<Long> storeFollowNum() {
        return BaseResponse.success(
                storeCustomerFollowQueryProvider.queryStoreCustomerFollowCountByCustomerId(
                        StoreCustomerFollowCountRequest.builder().customerId(commonUtil.getOperatorId()).build()
                ).getContext().getFollowNum());
    }
}
