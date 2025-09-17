package com.wanmi.sbc.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting.FilterRulesSettingProvider;
import com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting.FilterRulesSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.*;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingListResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "过滤规则设置API", description =  "FilterRulesSettingController")
@RestController
@Validated
@RequestMapping(value = "/filterrulessetting")
public class FilterRulesSettingController {

    @Autowired
    private FilterRulesSettingQueryProvider filterRulesSettingQueryProvider;

    @Autowired
    private FilterRulesSettingProvider filterRulesSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public BaseResponse<FilterRulesSettingPageResponse> getPage(@RequestBody @Valid FilterRulesSettingPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return filterRulesSettingQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<FilterRulesSettingListResponse> getList() {
        return filterRulesSettingQueryProvider.list();
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public BaseResponse<FilterRulesSettingByIdResponse> getById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FilterRulesSettingByIdRequest idReq = new FilterRulesSettingByIdRequest();
        idReq.setId(id);
        return filterRulesSettingQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public BaseResponse<FilterRulesSettingAddResponse> add(@RequestBody @Valid FilterRulesSettingAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return filterRulesSettingProvider.add(addReq);
    }

    @Operation(summary = "修改")
    @PutMapping("/modify")
    public BaseResponse<FilterRulesSettingListResponse> modify(@RequestBody @Valid FilterRulesSettingModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return filterRulesSettingProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FilterRulesSettingDelByIdRequest delByIdReq = new FilterRulesSettingDelByIdRequest();
        delByIdReq.setId(id);
        return filterRulesSettingProvider.deleteById(delByIdReq);
    }

}
