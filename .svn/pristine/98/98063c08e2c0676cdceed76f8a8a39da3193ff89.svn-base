package com.wanmi.sbc.authority;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.setting.api.provider.MenuInfoQueryProvider;
import com.wanmi.sbc.setting.api.request.MenuAndFunctionListRequest;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单权限管理Controller
 * Author: bail
 * Time: 2017/01/03
 */
@Tag(name = "StoreMenuAuthController", description = "菜单权限管理 API")
@RestController
@Validated
@RequestMapping("/menuAuth")
public class StoreMenuAuthController {

    @Autowired
    private MenuInfoQueryProvider menuInfoQueryProvider;


    /**
     * 查询所有的菜单,功能信息
     */
    @Operation(summary = "查询所有的菜单,功能信息")
    @RequestMapping(value = "/func", method = RequestMethod.GET)
    public BaseResponse<List<MenuInfoVO>> getFunc(){
        MenuAndFunctionListRequest request = new MenuAndFunctionListRequest();
        request.setSystemTypeCd(Platform.PROVIDER);
        return BaseResponse.success(menuInfoQueryProvider.listMenuAndFunction(request).getContext().getMenuInfoVOList());
    }
}
