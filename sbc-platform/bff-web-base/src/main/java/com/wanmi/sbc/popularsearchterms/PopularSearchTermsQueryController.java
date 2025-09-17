package com.wanmi.sbc.popularsearchterms;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.popularsearchterms.PopularSearchTermsQueryProvider;
import com.wanmi.sbc.setting.api.response.popularsearchterms.PopularSearchTermsListResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;


/**
 * <p>热门搜索词</p>
 * @author weiwenhao
 * @date 2019-04-20
 */
@RestController
@Validated
@Schema
@Tag(name =  "PopularSearchTermsQueryController",description = "热门搜索词查询服务API")
@RequestMapping("/popular_search_terms")
public class PopularSearchTermsQueryController {

    @Autowired
    private PopularSearchTermsQueryProvider popularSearchTermsQueryProvider;


    @Operation(summary = "热门搜索词列表查询")
    @RequestMapping(value ="/list",method = RequestMethod.POST)
    public BaseResponse<PopularSearchTermsListResponse> listPopularSearchTerms() {
        return popularSearchTermsQueryProvider.listPopularSearchTerms();
    }

}
