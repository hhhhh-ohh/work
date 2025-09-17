package com.wanmi.sbc.elastic.api.provider.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderPageRequest;
import com.wanmi.sbc.elastic.api.response.communityleader.CommunityLeaderExportResponse;
import com.wanmi.sbc.elastic.api.response.communityleader.EsCommunityLeaderPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author 王超
 * @date 2020/12/7 10:46
 * @description 社区团长查询
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsCommunityLeaderQueryProvider")
public interface EsCommunityLeaderQueryProvider {


    /**
     * 社区团长列表分页查询
     *
     * @param communityLeaderPageRequest
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/community-leader-customer/page")
    BaseResponse<EsCommunityLeaderPageResponse> page(@RequestBody @Valid EsCommunityLeaderPageRequest communityLeaderPageRequest);



    /**
     * 导出
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/community-leader-customer/export")
    BaseResponse<CommunityLeaderExportResponse> export(@RequestBody @Valid EsCommunityLeaderPageRequest request);

    /**
     * 导出数量
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/community-leader-customer/export/count")
    BaseResponse<Long> countForCount(@RequestBody @Valid EsCommunityLeaderPageRequest request);

}
