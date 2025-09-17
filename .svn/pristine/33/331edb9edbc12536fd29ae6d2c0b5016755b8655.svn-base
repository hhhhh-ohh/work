package com.wanmi.sbc.third.address;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressProvider;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressListRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressMappingRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressModifyRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressPageRequest;
import com.wanmi.sbc.setting.api.response.thirdaddress.ThirdAddressListResponse;
import com.wanmi.sbc.setting.api.response.thirdaddress.ThirdAddressPageResponse;
import io.jsonwebtoken.lang.Collections;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name =  "第三方地址映射表管理API", description =  "ThirdAddressController")
@RestController
@Validated
@RequestMapping(value = "/third/address")
public class ThirdAddressController {

    @Autowired private ThirdAddressQueryProvider thirdAddressQueryProvider;

    @Autowired private ThirdAddressProvider thirdAddressProvider;

    @Operation(summary = "分页查询第三方地址表")
    @PostMapping("/linkedMall/page")
    public BaseResponse<ThirdAddressPageResponse> pageLinkedMall(
            @RequestBody @Valid ThirdAddressPageRequest pageReq) {
        pageReq.setThirdFlag(ThirdPlatformType.LINKED_MALL);
        return thirdAddressQueryProvider.page(pageReq);
    }

    @Operation(summary = "修改第三方")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid ThirdAddressModifyRequest request) {
        return thirdAddressProvider.modify(request);
    }

    @Operation(summary = "linkedMall地址全局更新")
    @PutMapping("/linkedMall/mapping")
    public BaseResponse mappingLinkedMall() {
        return thirdAddressProvider.mapping(
                ThirdAddressMappingRequest.builder()
                        .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                        .build());
    }

    /**
     * 分页查询
     * @author wur
     * @date: 2021/5/10 14:15
     * @param pageReq 分页查询请求参数
     * @return 第三放地址信息
     */
    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public BaseResponse page(@RequestBody @Valid ThirdAddressPageRequest pageReq) {
        return thirdAddressQueryProvider.page(pageReq);
    }

    /**
     * 全量映射第三方地址信息
     * @author wur
     * @date: 2021/5/10 13:58
     * @param pageReq 分页查询请求参数
     * @return 操作结果
     */
    @Operation(summary = "VOP地址全局更新")
    @PutMapping("/mapping")
    public BaseResponse mappingVOP(@RequestBody @Valid ThirdAddressMappingRequest pageReq) {
        return thirdAddressProvider.mapping(pageReq);
    }

    /**
     * 根据平台地址ID查询地址映射关系
     * @author wur
     * @date: 2021/5/12 11:58
     * @param listRequest 查询请求参数
     * @return   返回结果
     */
    @Operation(summary = "根据平台地址ID查询地址映射关系")
    @PostMapping("/validate")
    public BaseResponse validatePlatformAddress(@RequestBody @Valid ThirdAddressListRequest listRequest) {
        listRequest.setDelFlag(DeleteFlag.NO);
        BaseResponse<ThirdAddressListResponse> baseResponse = thirdAddressQueryProvider.list(listRequest);
        if(Collections.isEmpty(baseResponse.getContext().getThirdAddressList())) {
            return BaseResponse.FAILED();
        }
        return BaseResponse.SUCCESSFUL();
    }
}
