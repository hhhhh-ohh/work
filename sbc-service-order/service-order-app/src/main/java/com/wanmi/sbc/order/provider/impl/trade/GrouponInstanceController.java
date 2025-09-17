package com.wanmi.sbc.order.provider.impl.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.trade.GrouponInstanceProvider;
import com.wanmi.sbc.order.api.request.trade.GrouponInstanceByGrouponNoRequest;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.trade.model.root.GrouponInstance;
import com.wanmi.sbc.order.trade.service.GrouponInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-8-23 10:04
 */
@Validated
@RestController
public class GrouponInstanceController implements GrouponInstanceProvider {

    @Autowired
    private GrouponOrderService grouponOrderService;

    @Autowired
    private GrouponInstanceService grouponInstanceService;


    @Override
    public BaseResponse modifyGrouponExpiredSendFlagByGrouponNo(@RequestBody @Valid GrouponInstanceByGrouponNoRequest request) {
        //查询团实例
        GrouponInstance grouponInstance = grouponInstanceService.detailByGrouponNo(request.getGrouponNo());
        // 修改推送设置
        grouponInstance.setGrouponExpiredSendFlag(Boolean.TRUE);
        grouponOrderService.updateGrouponInstance(grouponInstance);

        return BaseResponse.SUCCESSFUL();
    }


}
