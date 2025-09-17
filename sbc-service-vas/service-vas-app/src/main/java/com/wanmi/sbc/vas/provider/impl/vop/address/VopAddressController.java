package com.wanmi.sbc.vas.provider.impl.vop.address;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressMappingRequest;
import com.wanmi.sbc.vas.api.provider.vop.address.VopAddressProvider;
import com.wanmi.sbc.vas.vop.address.VopAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wur
 * @className VopAddressController
 * @description 渠道VOP地址相关服务
 * @date 2021/5/12 16:18
 */
@Slf4j
@RestController
public class VopAddressController implements VopAddressProvider {

    @Autowired private ThirdAddressProvider thirdAddressProvider;

    @Autowired private VopAddressService vopAddressService;

    /**
     * 同步VOP地址数据并初始映射
     * 处理初始化需要较长时间，会导致微服务超时，所以用异步
     * @author wur
     * @date: 2021/5/12 16:23
     * @return
     */
    @Async
    @Override
    public BaseResponse init() {
        // 同步VOP地址信息
        vopAddressService.init();
        // 地址初始映射
        thirdAddressProvider.mapping(
                ThirdAddressMappingRequest.builder()
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .build());
        log.info("VOP地址信息同步和初始映射结束");
        return BaseResponse.SUCCESSFUL();
    }
    
    /**
    *  增量处理VOP地址映射
     * @description
     * @author  wur
     * @date: 2021/5/14 10:56
     * @return 返回操作结果
     **/
    @Async
    @Override
    public BaseResponse mapping() {
        // 地址映射
        thirdAddressProvider.mapping(
                ThirdAddressMappingRequest.builder()
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .build());
        log.info("VOP地址信息映射结束");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description VOP地址映射[修正]
     * @author malianfeng
     * @date 2023/2/24 18:32
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Async
    @Override
    public BaseResponse mappingRevise() {
        // 地址映射[修正]
        thirdAddressProvider.mappingRevise(
                ThirdAddressMappingRequest.builder()
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .build());
        log.info("VOP地址信息映射[修正]结束");
        return BaseResponse.SUCCESSFUL();
    }
}
