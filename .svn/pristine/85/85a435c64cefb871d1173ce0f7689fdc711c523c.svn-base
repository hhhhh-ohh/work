package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import com.wanmi.sbc.order.api.provider.groupon.GrouponProvider;
import com.wanmi.sbc.order.api.request.groupon.GrouponOrderStatusGetByOrderIdRequest;
import com.wanmi.sbc.order.api.response.groupon.GrouponOrderStatusGetByOrderIdResponse;
import com.wanmi.sbc.order.api.request.groupon.GrouponActivityQueryRequest;
//import io.protostuff.runtime.ArraySchemas;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 拼团订单Controller
 */
@RestController
@Validated
@RequestMapping("/groupon/order")
@Tag(name = "GrouponOrderController", description = "S2B web公用-拼团订单")
public class GrouponOrderController {


    @Autowired
    private GrouponProvider grouponProvider;

    /**
     * 验证拼团订单是否可支付
     */
    @Operation(summary = "验证拼团订单是否可支付")
    @RequestMapping(value = "/check/{orderId}", method = RequestMethod.POST)
    public BaseResponse<GrouponOrderStatusGetByOrderIdResponse> getGrouponOrderStatusByOrderId(@PathVariable String orderId) {
        return grouponProvider.getGrouponOrderStatusByOrderId(new GrouponOrderStatusGetByOrderIdRequest(orderId));
    }

    @Operation(summary="根据grouponNo查询拼团活动信息")
    @RequestMapping(value="/query/activity/{grouponNo}",method = RequestMethod.POST)
    public BaseResponse<GrouponActivityVO> getGrouponActivityByGrouponNo(@PathVariable String grouponNo){
        return grouponProvider.getGrouponActivityByGrouponNo(new GrouponActivityQueryRequest(grouponNo));
    }
}
