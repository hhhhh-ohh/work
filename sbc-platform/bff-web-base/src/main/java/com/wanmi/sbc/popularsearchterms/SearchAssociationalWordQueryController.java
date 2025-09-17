package com.wanmi.sbc.popularsearchterms;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.searchterms.SearchAssociationalWordQueryProvider;
import com.wanmi.sbc.setting.api.request.searchterms.AssociationLongTailWordLikeRequest;
import com.wanmi.sbc.setting.api.response.searchterms.AssociationLongTailWordLikeResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * <p>热门搜索词</p>
 * @author weiwenhao
 * @date 2019-04-20
 */
@RestController
@Validated
@Schema
@Tag(name =  "SearchAssociationalWordQueryController",description = "搜索词模糊查询服务API")
@RequestMapping("/search_associational_word")
public class SearchAssociationalWordQueryController {

    @Autowired
    private SearchAssociationalWordQueryProvider searchAssociationalWordQueryProvider;

    /**
     * 模糊搜索词查询
     * @param request
     * @return
     */
    @Operation(summary = "模糊搜索词查询")
    @PostMapping("/like_associational_word")
    public BaseResponse<AssociationLongTailWordLikeResponse> likeAssociationalWord(@RequestBody @Valid AssociationLongTailWordLikeRequest request){
        return searchAssociationalWordQueryProvider.likeAssociationalWord(request);
    }

}
