package com.wanmi.sbc.searchterms.presetsearch;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.presetsearch.PresetSearchTermsQueryProvider;
import com.wanmi.sbc.setting.api.provider.presetsearch.PresetSearchTermsSaveProvider;
import com.wanmi.sbc.setting.api.request.presetsearch.PresetSearchTermsModifyRequest;
import com.wanmi.sbc.setting.api.request.presetsearch.PresetSearchTermsRequest;
import com.wanmi.sbc.setting.api.response.presetsearch.PresetSearchTermsQueryResponse;
import com.wanmi.sbc.setting.api.response.presetsearch.PresetSearchTermsResponse;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * 新增预置搜索词
 */
@Tag(name = "PresetSearchTermsSaveController", description = "预置搜索词服务API")
@RestController
@Validated
@RequestMapping(value = "/preset_search_terms")
public class PresetSearchTermsSaveController {


    @Autowired
    private PresetSearchTermsSaveProvider searchTermsSaveProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private PresetSearchTermsQueryProvider presetSearchTermsProvider;


    @Operation(summary = "新增预置搜索词")
    @PostMapping("/add")
    public BaseResponse<PresetSearchTermsResponse> add(@RequestBody @Valid PresetSearchTermsRequest request) {
        operateLogMQUtil.convertAndSend("预置搜索词","新增预置热门搜索词","新增热门搜索词："+request.getPresetSearchKeyword());
        return searchTermsSaveProvider.add(request);
    }

    /**
     * 编辑预置搜索词
     * @param request
     * @return
     */
    @Operation(summary = "编辑预置搜索词")
    @PostMapping("/modify")
    public BaseResponse<PresetSearchTermsResponse> modify(@RequestBody @Valid PresetSearchTermsModifyRequest request){
        operateLogMQUtil.convertAndSend("预置搜索词","编辑预置热门搜索词","编辑热门搜索词："+request.getPresetSearchKeyword());
        return searchTermsSaveProvider.modify(request);
    }

    /**
     * 查询预置搜索词
     * @return
     */
    @Operation(summary = "查询预置搜索词")
    @PostMapping("/list")
    public BaseResponse<PresetSearchTermsQueryResponse> listPresetSearchTerms() {
        return presetSearchTermsProvider.listPresetSearchTerms();
    }


}
