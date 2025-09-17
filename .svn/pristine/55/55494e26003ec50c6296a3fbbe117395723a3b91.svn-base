package com.wanmi.sbc.authority;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.MenuInfoProvider;
import com.wanmi.sbc.setting.api.provider.MenuInfoQueryProvider;
import com.wanmi.sbc.setting.api.request.*;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;


import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限管理Controller
 * Author: bail
 * Time: 2017/01/03
 */
@RestController
@Validated
@RequestMapping("/menuAuth")
@Tag(name = "BossMenuAuthController", description = "S2B 平台端-菜单权限管理API")
public class BossMenuAuthController {

    @Autowired
    private MenuInfoProvider menuInfoProvider;

    @Autowired
    private MenuInfoQueryProvider menuInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 查询所有的菜单,功能信息
     */
    @Operation(summary = "查询菜单及功能列表")
    @GetMapping(value = "/func")
    public BaseResponse<List<MenuInfoVO>> getFunc(){
        MenuAndFunctionListRequest request = new MenuAndFunctionListRequest();
        request.setSystemTypeCd(Platform.PLATFORM);

        return BaseResponse.success(menuInfoQueryProvider.listMenuAndFunction(request).getContext().getMenuInfoVOList());
    }

    /**
     * 查询所有的菜单,功能,权限信息
     */
    @Operation(summary = "查询菜单及权限列表")
    @Parameter(name = "currPlatform", description = "平台类型 3 商家端 4 平台端", required = true)
    @GetMapping(value = "/{currPlatform}")
    public BaseResponse<List<MenuInfoVO>> get(@PathVariable String currPlatform){
        MenuAndAuthorityListRequest request = new MenuAndAuthorityListRequest();
        request.setSystemTypeCd(Platform.forValue(currPlatform));

        return BaseResponse.success(menuInfoQueryProvider.listMenuAndAuthority(request).getContext().getMenuInfoVOList());
    }

    /**
     * 添加菜单
     */
    @Operation(summary = "添加菜单")
    @PostMapping(value = "/menu")
    public BaseResponse addMenu(@RequestBody MenuAddRequest menuInfo){
        this.check();
        menuInfoProvider.addMenuInfo(menuInfo);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改菜单
     */
    @Operation(summary = "修改菜单")
    @PutMapping(value = "/menu")
    public BaseResponse updateMenu(@RequestBody MenuModifyRequest menuInfo){
        this.check();
        menuInfo.setDelFlag(DeleteFlag.NO);
        menuInfoProvider.modifyMenuInfo(menuInfo);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除菜单
     */
    @Operation(summary = "删除菜单")
    @Parameter(name = "menuId", description = "菜单id", required = true)
    @DeleteMapping(value = "/menu/{menuId}")
    public BaseResponse deleteMenu(@PathVariable String menuId){
        this.check();
        MenuDeleteRequest request = new MenuDeleteRequest();
        request.setMenuId(menuId);
        menuInfoProvider.deleteMenuInfo(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加功能
     */
    @Operation(summary = "添加功能")
    @PostMapping(value = "/func")
    public BaseResponse addFunc(@RequestBody FunctionInfoAddRequest functionInfo){
        this.check();
        menuInfoProvider.addFunction(functionInfo);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改功能
     */
    @Operation(summary = "修改功能")
    @PutMapping(value = "/func")
    public BaseResponse updateFunc(@RequestBody FunctionModifyRequest functionInfo){
        this.check();
        functionInfo.setDelFlag(DeleteFlag.NO);
        menuInfoProvider.modifyFunction(functionInfo);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除功能
     */
    @Operation(summary = "删除功能")
    @Parameter(name = "funcId", description = "功能id", required = true)
    @DeleteMapping(value = "/func/{funcId}")
    public BaseResponse deleteFunc(@PathVariable String funcId){
        this.check();
        FunctionDeleteRequest request = new FunctionDeleteRequest();
        request.setFunctionId(funcId);
        menuInfoProvider.deleteFunction(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加权限
     */
    @Operation(summary = "添加权限")
    @PostMapping(value = "/auth")
    public BaseResponse addAuth(@RequestBody AuthorityAddRequest authority){
        this.check();
        menuInfoProvider.addAuthority(authority);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改权限
     */
    @Operation(summary = "修改权限")
    @PutMapping(value = "/auth")
    public BaseResponse updateAuth(@RequestBody AuthorityModifyRequest authority){
        this.check();
        authority.setDelFlag(DeleteFlag.NO);
        menuInfoProvider.modifyAuthority(authority);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除权限
     */
    @Operation(summary = "删除权限")
    @Parameter(name = "authId", description = "权限id", required = true)
    @DeleteMapping(value = "/auth/{authId}")
    public BaseResponse deleteAuth(@PathVariable String authId){
        this.check();
        AuthorityDeleteRequest request = new AuthorityDeleteRequest();
        request.setAuthorityId(authId);
        menuInfoProvider.deleteAuthority(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 验证权限
     */
    private void check(){
        if((!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform()))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000015);
        }
    }
}
