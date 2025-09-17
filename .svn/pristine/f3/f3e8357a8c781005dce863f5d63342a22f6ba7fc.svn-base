package com.wanmi.sbc.setting.provider.impl.hovernavmobile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.hovernavmobile.HoverNavMobileProvider;
import com.wanmi.sbc.setting.api.request.hovernavmobile.BottomNavModifyRequest;
import com.wanmi.sbc.setting.api.request.hovernavmobile.HoverNavMobileModifyRequest;
import com.wanmi.sbc.setting.bean.dto.BottomNavMobileItemDTO;
import com.wanmi.sbc.setting.bean.enums.BottomNavKey;
import com.wanmi.sbc.setting.bean.enums.BottomNavLink;
import com.wanmi.sbc.setting.hovernavmobile.model.root.BottomNavConfig;
import com.wanmi.sbc.setting.hovernavmobile.model.root.HoverNavMobile;
import com.wanmi.sbc.setting.hovernavmobile.model.root.HoverNavMobileItem;
import com.wanmi.sbc.setting.hovernavmobile.service.HoverNavMobileService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>移动端悬浮导航栏保存服务接口实现</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@RestController
@Validated
public class HoverNavMobileController implements HoverNavMobileProvider {
    @Autowired
    private HoverNavMobileService hoverNavMobileService;

    @Override
    public BaseResponse modify(@RequestBody @Valid HoverNavMobileModifyRequest hoverNavMobileModifyRequest) {
        HoverNavMobile detail = hoverNavMobileService.getOne(hoverNavMobileModifyRequest.getStoreId());
        detail.setStoreId(hoverNavMobileModifyRequest.getStoreId());
        detail.setUsePages(hoverNavMobileModifyRequest.getUsePages());
        detail.setNavItems(KsBeanUtil.convert(hoverNavMobileModifyRequest.getNavItems(), HoverNavMobileItem.class));
        hoverNavMobileService.modify(detail);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyBottomNav(@RequestBody @Valid BottomNavModifyRequest bottomNavModifyRequest) {
        // 1. 数据校验
        List<BottomNavMobileItemDTO> bottomNavItems = bottomNavModifyRequest.getBottomNavItems();
        for (BottomNavMobileItemDTO itemDTO : bottomNavItems) {
            if (Objects.isNull(BottomNavKey.fromValue(itemDTO.getKey()))) {
                // 导航标识非法
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (Objects.isNull(BottomNavLink.fromValue(itemDTO.getLink()))) {
                String link = itemDTO.getLink();
                if (!(link.startsWith("{")&&link.endsWith("}"))) {
                    // 导航落地页地址非法
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
        // 1.1 导航项数量必须为5
        if (CollectionUtils.isEmpty(bottomNavItems) || bottomNavItems.size() != Constants.FIVE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 1.2 导航项标识不能重复
        long keyDistinctCount = bottomNavItems.stream().map(BottomNavMobileItemDTO::getKey).distinct().count();
        if (keyDistinctCount != Constants.FIVE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 1.3 导航项落地页地址不能重复
        long linkDistinctCount = bottomNavItems.stream().map(BottomNavMobileItemDTO::getLink).distinct().count();
        if (linkDistinctCount != Constants.FIVE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 1.4 首页main和tapIndex0的绑定关系，不能更改
        boolean mainBindingOtherKeyFlag = bottomNavItems.stream()
                .anyMatch(item -> BottomNavLink.MAIN.toValue().equals(item.getLink()) && !BottomNavKey.TAB_INDEX_0.toValue().equals(item.getKey()));
        if (mainBindingOtherKeyFlag) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 2. 保存配置
        HoverNavMobile detail = hoverNavMobileService.getOne(bottomNavModifyRequest.getStoreId());
        detail.setStoreId(bottomNavModifyRequest.getStoreId());
        detail.setBottomNavConfig(KsBeanUtil.convert(bottomNavModifyRequest, BottomNavConfig.class));
        hoverNavMobileService.modify(detail);
        return BaseResponse.SUCCESSFUL();
    }


}

