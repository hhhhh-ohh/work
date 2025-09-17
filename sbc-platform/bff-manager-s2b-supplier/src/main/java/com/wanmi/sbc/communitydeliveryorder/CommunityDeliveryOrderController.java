package com.wanmi.sbc.communitydeliveryorder;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderProvider;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDeliveryByIdListRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@Tag(name =  "社区团购发货单管理API", description =  "CommunityDeliveryOrderController")
@RestController
@RequestMapping(value = "/communityDeliveryOrder")
public class CommunityDeliveryOrderController {

    @Autowired
    private CommunityDeliveryOrderProvider communityDeliveryOrderProvider;

    @MultiSubmit
    @Operation(summary = "根据idList批量发货社区团购发货单")
    @PutMapping("/delivery-by-id-list")
    public BaseResponse deliveryByIdList(@RequestBody @Valid CommunityDeliveryOrderDeliveryByIdListRequest request) {
        return communityDeliveryOrderProvider.modifyDeliveryStatusByActivityId(request);
    }
}
