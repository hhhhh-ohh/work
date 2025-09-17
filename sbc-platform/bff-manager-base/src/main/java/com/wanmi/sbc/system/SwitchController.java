package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.SwitchProvider;
import com.wanmi.sbc.setting.api.provider.SwitchQueryProvider;
import com.wanmi.sbc.setting.api.request.SwitchGetByIdRequest;
import com.wanmi.sbc.setting.api.request.SwitchModifyRequest;
import com.wanmi.sbc.setting.api.response.SwitchGetByIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuanlinling on 2017/4/26.
 */
@Tag(name = "SwitchController", description = "系统设置服务 Api")
@RestController
@Validated
@RequestMapping("/system")
public class SwitchController {

    @Autowired
    private SwitchProvider switchProvider;

    @Autowired
    private SwitchQueryProvider switchQueryProvider;

    /**
     * 根据id查询
     */
    @Operation(summary = "根据id查询")
    @Parameter(name = "switchId", description = "系统设置Id", required = true)
    @RequestMapping(value = "/switchId/{switchId}", method = RequestMethod.GET)
    public ResponseEntity<SwitchGetByIdResponse> findInvoiceProjectById(@PathVariable("switchId") String switchId) {
        SwitchGetByIdRequest request = new SwitchGetByIdRequest();
        request.setId(switchId);

        SwitchGetByIdResponse response = switchQueryProvider.getById(request).getContext();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050169);
        }
    }

    /**
     * 开关开启关闭
     *
     * @param switchRequest
     * @return
     */
    @Operation(summary = "开关开启关闭")
    @RequestMapping(value = "/switch", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> updateSwitch(@RequestBody SwitchModifyRequest switchRequest) {
        switchProvider.modify(switchRequest);

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

}
