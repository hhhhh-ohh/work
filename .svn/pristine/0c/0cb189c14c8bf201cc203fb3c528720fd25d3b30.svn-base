package com.wanmi.sbc.platformaddress;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressSaveProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressAddRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressDelByIdRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressModifyRequest;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressAddResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressListResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressModifyResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "平台地址信息管理API", description =  "BossPlatformAddressController")
@RestController
@Validated
@RequestMapping(value = "/addressmanage")
public class BossPlatformAddressController {

    @Autowired
    private PlatformAddressSaveProvider platformAddressSaveProvider;

    @Operation(summary = "新增积分商品表")
    @PostMapping("/add")
    public BaseResponse<PlatformAddressAddResponse> add(@RequestBody @Valid PlatformAddressAddRequest addReq) {
        addReq.setSortNo(NumberUtils.toInt(addReq.getAddrId()));
        return platformAddressSaveProvider.add(addReq);
    }

    @Operation(summary = "修改积分商品表")
    @PutMapping("/modify")
    public BaseResponse<PlatformAddressModifyResponse> modify(@RequestBody @Valid PlatformAddressModifyRequest modifyReq) {
        return platformAddressSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "单个删除平台地址信息")
    @Parameter(name = "id", description = "地址id", required = true)
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PlatformAddressDelByIdRequest delByIdReq = new PlatformAddressDelByIdRequest();
        delByIdReq.setId(id);
        return platformAddressSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "初始化拼音值")
    @PostMapping("/init-pinyin")
    public BaseResponse<PlatformAddressListResponse> initPinYin() {
        return platformAddressSaveProvider.initPinYin();
    }
}
