package com.wanmi.sbc.searchterms.searchterms;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.searchterms.EsSearchAssociationalWordQueryProvider;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordPageRequest;
import com.wanmi.sbc.elastic.api.response.searchterms.EsSearchAssociationalWordPageResponse;
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
 * <p>搜索词APIProvider</p>
 *
 * @author weiwenhao
 * @date 2020-04-20
 */
@RestController
@Validated
@Tag(name = "SearchAssociationalWordQueryController", description = "搜索词查询服务API")
@RequestMapping("/search_associational_word")
public class SearchAssociationalWordQueryController {

    @Autowired
    private EsSearchAssociationalWordQueryProvider esSearchAssociationalWordQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 搜索词分页查询
     *
     * @param request
     * @return
     */
    @Operation(summary = "搜索词分页查询")
    @PostMapping("/page")
    public BaseResponse<EsSearchAssociationalWordPageResponse> page(@RequestBody @Valid EsSearchAssociationalWordPageRequest request) {
        operateLogMQUtil.convertAndSend("搜索词", "搜索词&联想词列表查询", "热门搜索词列表查询");
        return esSearchAssociationalWordQueryProvider.page(request);
    }
}
