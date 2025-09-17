package com.wanmi.sbc.vas.provider.impl.linkedmall.address;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressMappingRequest;
import com.wanmi.sbc.vas.api.provider.linkedmall.address.LinkedMallAddressProvider;
import com.wanmi.sbc.vas.linkedmall.address.LinkedMallAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkedMallAddressController implements LinkedMallAddressProvider {

    @Autowired
    private LinkedMallAddressService linkedMallAddressService;

    @Autowired
    private ThirdAddressProvider thirdAddressProvider;

    @Override
    public BaseResponse init(){
        linkedMallAddressService.init();
        thirdAddressProvider.mapping(ThirdAddressMappingRequest.builder().thirdPlatformType(ThirdPlatformType.LINKED_MALL).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     *  LinkedMall地址映射
     * @return 返回操作结果
     **/
    @Override
    public BaseResponse mapping() {
        // 地址映射
        thirdAddressProvider.mapping(
                ThirdAddressMappingRequest.builder()
                        .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                        .build());
        return BaseResponse.SUCCESSFUL();
    }
}
