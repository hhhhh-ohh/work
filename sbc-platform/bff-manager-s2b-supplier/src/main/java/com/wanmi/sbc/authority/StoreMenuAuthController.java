package com.wanmi.sbc.authority;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.setting.api.provider.MenuInfoQueryProvider;
import com.wanmi.sbc.setting.api.request.MenuAndFunctionListRequest;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    @Autowired
    private StoreQueryProvider storeQueryProvider;
    @Autowired
    private CommonUtil commonUtil;


    /**
     * 查询所有的菜单,功能信息
     */
    @Operation(summary = "查询所有的菜单,功能信息")
    @RequestMapping(value = "/func", method = RequestMethod.GET)
    public BaseResponse<List<MenuInfoVO>> getFunc(){
        MenuAndFunctionListRequest request = new MenuAndFunctionListRequest();
        request.setSystemTypeCd(Platform.SUPPLIER);
        List<MenuInfoVO> menuInfoVOList =
                menuInfoQueryProvider.listMenuAndFunction(request).getContext().getMenuInfoVOList();
        final CopyOnWriteArrayList<MenuInfoVO> cowList = new CopyOnWriteArrayList<MenuInfoVO>(menuInfoVOList);
        //查询商家类型
        StoreVO storeVO =
                storeQueryProvider.getById(StoreByIdRequest.builder().storeId(commonUtil.getStoreId()).build())
                        .getContext().getStoreVO();
        if (StoreType.CROSS_BORDER.equals(storeVO.getStoreType())) {
            List<String> removeMenus =
                    Lists.newArrayList(
                            "第二件半价", "预约购买", "预售活动", "企业购", "企业购商品", "拼团活动", "新增拼团活动"
                            , "创建组合商品活动", "创建满赠活动");
            for (MenuInfoVO item : cowList) {
                if (removeMenus.contains(item.getTitle())) {
                    cowList.remove(item);
                }
            }
        }
        return BaseResponse.success(cowList);
    }
}
