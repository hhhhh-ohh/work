package com.wanmi.sbc.vas.iep;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingProvider;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingModifyResponse;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingTopOneResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 20:31 2020/3/2
 * @Description: 企业购设置
 */
@Tag(name = "IepSettingController", description = "增值服务-企业购设置 API")
@RestController("IepSettingController")
@Validated
@RequestMapping("/vas/iep/setting")
public class IepSettingController {

    @Autowired
    private IepSettingProvider iepSettingProvider;

    @Autowired
    private IepSettingQueryProvider iepSettingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "查询企业购设置信息")
    @GetMapping
    public BaseResponse<IepSettingTopOneResponse> findTopOne() {
        return iepSettingQueryProvider.findTopOne();
    }

    @Operation(summary = "修改企业购设置信息")
    @PutMapping
    public BaseResponse<IepSettingModifyResponse> modify(@RequestBody @Valid IepSettingModifyRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        return iepSettingProvider.modify(request);
    }
}
