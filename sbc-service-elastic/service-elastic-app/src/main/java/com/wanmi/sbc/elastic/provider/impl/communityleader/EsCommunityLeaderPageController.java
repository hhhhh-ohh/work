package com.wanmi.sbc.elastic.provider.impl.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.communityleader.EsCommunityLeaderQueryProvider;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderPageRequest;
import com.wanmi.sbc.elastic.api.response.communityleader.CommunityLeaderExportResponse;
import com.wanmi.sbc.elastic.api.response.communityleader.EsCommunityLeaderPageResponse;
import com.wanmi.sbc.elastic.communityleader.service.EsCommunityLeaderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author: wc
 * @date: 2020/12/7 11:26
 * @description: 社区团长查询接口
 */
@RestController
public class EsCommunityLeaderPageController implements EsCommunityLeaderQueryProvider {


    @Autowired
    private EsCommunityLeaderQueryService esCommunityLeaderQueryService;


    /**
     * 分页查询社区团长信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse<EsCommunityLeaderPageResponse> page(@RequestBody @Valid EsCommunityLeaderPageRequest request) {
        EsCommunityLeaderPageResponse page = esCommunityLeaderQueryService.page(request);
        return BaseResponse.success(page);
    }

    /**
     * 导出社区团长信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CommunityLeaderExportResponse> export(@RequestBody @Valid EsCommunityLeaderPageRequest request) {
        return esCommunityLeaderQueryService.export(request);
    }

    /**
     * 查询数量
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Long> countForCount(@RequestBody @Valid EsCommunityLeaderPageRequest request) {
        Long total = esCommunityLeaderQueryService.count(request);
        return BaseResponse.success(total);
    }
}
