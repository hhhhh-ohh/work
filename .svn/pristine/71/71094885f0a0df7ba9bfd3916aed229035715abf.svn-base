package com.wanmi.sbc.crm.tagparam;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.crm.api.provider.tagparam.TagParamQueryProvider;
import com.wanmi.sbc.crm.api.request.tagparam.TagParamListRequest;
import com.wanmi.sbc.crm.api.response.tagparam.TagParamListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Tag(name =  "标签参数管理API", description =  "TagParamController")
@RestController
@Validated
@RequestMapping(value = "/tagparam")
public class TagParamController {

    @Autowired
    private TagParamQueryProvider tagParamQueryProvider;

    @Operation(summary = "列表查询标签参数")
    @PostMapping("/list")
    public BaseResponse<TagParamListResponse> getList(@RequestBody @Valid TagParamListRequest listReq) {
        listReq.putSort("id", "desc");
        return tagParamQueryProvider.list(listReq);
    }
}
