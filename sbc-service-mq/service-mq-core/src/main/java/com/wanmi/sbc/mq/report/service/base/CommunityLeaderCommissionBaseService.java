package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @return
 * @description 社团团长佣金
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CommunityLeaderCommissionBaseService {

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_community_commission_export_sign_word")
    public CommunityLeaderPageResponse queryExport(Operator operator, CommunityLeaderPageRequest pageRequest){
        CommunityLeaderPageResponse dataRecords = communityLeaderQueryProvider.page(pageRequest).getContext();
        return dataRecords;
    }
}
