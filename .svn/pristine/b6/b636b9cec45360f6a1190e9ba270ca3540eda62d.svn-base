package com.wanmi.sbc.hovernavmobile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.hovernavmobile.HoverNavMobileQueryProvider;
import com.wanmi.sbc.setting.api.request.hovernavmobile.BottomNavMobileByIdRequest;
import com.wanmi.sbc.setting.api.request.hovernavmobile.HoverNavMobileByIdRequest;
import com.wanmi.sbc.setting.api.response.hovernavmobile.BottomNavMobileByIdResponse;
import com.wanmi.sbc.setting.api.response.hovernavmobile.HoverNavMobileByIdResponse;
import com.wanmi.sbc.setting.bean.enums.UsePageType;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;


@Tag(description= "移动端悬浮导航栏管理API", name = "HoverNavMobileController")
@RestController
@Validated
@RequestMapping(value = "/hoverNavMobile")
public class HoverNavMobileController {

    @Autowired
    private HoverNavMobileQueryProvider hoverNavMobileQueryProvider;

    @Operation(summary = "平台移动端悬浮导航栏")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'hoverNavMobile:'+#usePageType")
    @GetMapping("/{usePageType}")
    public BaseResponse<HoverNavMobileByIdResponse> getByMain(@PathVariable Integer usePageType) {
        HoverNavMobileByIdRequest idReq = new HoverNavMobileByIdRequest();
        idReq.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        HoverNavMobileByIdResponse response = hoverNavMobileQueryProvider.getById(idReq).getContext();
        if(Objects.nonNull(response.getHoverNavMobileVO())
                && CollectionUtils.isNotEmpty(response.getHoverNavMobileVO().getUsePages())
                && response.getHoverNavMobileVO().getUsePages().contains(UsePageType.fromValue(usePageType))){
            return BaseResponse.success(response);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "店铺移动端悬浮导航栏")
    @GetMapping("/{storeId}/{usePageType}")
    public BaseResponse<HoverNavMobileByIdResponse> get(@PathVariable Long storeId, @PathVariable Integer usePageType) {
        HoverNavMobileByIdRequest idReq = new HoverNavMobileByIdRequest();
        idReq.setStoreId(storeId);
        HoverNavMobileByIdResponse response = hoverNavMobileQueryProvider.getById(idReq).getContext();
        if(Objects.nonNull(response.getHoverNavMobileVO())
                && CollectionUtils.isNotEmpty(response.getHoverNavMobileVO().getUsePages())
                && response.getHoverNavMobileVO().getUsePages().contains(UsePageType.fromValue(usePageType))){
            return BaseResponse.success(response);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "平台移动端底部导航栏")
    @GetMapping("/bottomNav")
    public BaseResponse<BottomNavMobileByIdResponse> getBottomNav() {
        BottomNavMobileByIdRequest idReq = new BottomNavMobileByIdRequest();
        idReq.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        BaseResponse<BottomNavMobileByIdResponse> bottomNavById = hoverNavMobileQueryProvider.getBottomNavById(idReq);
        return bottomNavById;
    }
}
