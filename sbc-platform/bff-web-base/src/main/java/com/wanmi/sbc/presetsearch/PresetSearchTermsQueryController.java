package com.wanmi.sbc.presetsearch;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.setting.api.provider.presetsearch.PresetSearchTermsQueryProvider;
import com.wanmi.sbc.setting.api.response.presetsearch.PresetSearchTermsQueryResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 查询预置搜索词
 */
@Tag(name = "PresetSearchTermsQueryController", description = "预置搜索词服务API")
@RestController
@RequestMapping("/preset_search_terms")
@Validated
public class PresetSearchTermsQueryController {

    @Autowired
    private PresetSearchTermsQueryProvider presetSearchTermsProvider;


    /**
     * 查询预置搜索词
     * @return
     */
    @Operation(summary = "查询预置搜索词")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'presetSearchTerms'")
    @PostMapping("/list")
    public BaseResponse<PresetSearchTermsQueryResponse> listPresetSearchTerms() {
        return presetSearchTermsProvider.listPresetSearchTerms();
    }
}
