package com.wanmi.sbc.hovernavmobile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.hovernavmobile.HoverNavMobileProvider;
import com.wanmi.sbc.setting.api.provider.hovernavmobile.HoverNavMobileQueryProvider;
import com.wanmi.sbc.setting.api.request.hovernavmobile.BottomNavMobileByIdRequest;
import com.wanmi.sbc.setting.api.request.hovernavmobile.BottomNavModifyRequest;
import com.wanmi.sbc.setting.api.response.hovernavmobile.BottomNavMobileByIdResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "移动端底部导航栏管理API", description =  "BottomNavMobileController")
@RestController
@Validated
@RequestMapping(value = "/bottomNavMobile")
public class BottomNavMobileController {

    @Autowired
    private HoverNavMobileQueryProvider hoverNavMobileQueryProvider;

    @Autowired
    private HoverNavMobileProvider hoverNavMobileProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "查询移动端底部导航栏")
    @GetMapping()
    public BaseResponse<BottomNavMobileByIdResponse> get() {
        Long storeId = commonUtil.getStoreId();
        if (storeId == null) {
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        BottomNavMobileByIdRequest idReq = new BottomNavMobileByIdRequest();
        idReq.setStoreId(storeId);
        return hoverNavMobileQueryProvider.getBottomNavById(idReq);
    }

    @Operation(summary = "修改移动端底部导航栏")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid BottomNavModifyRequest modifyReq) {
        Long storeId = commonUtil.getStoreId();
        if (storeId == null) {
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        modifyReq.setStoreId(storeId);
        return hoverNavMobileProvider.modifyBottomNav(modifyReq);
    }
}
