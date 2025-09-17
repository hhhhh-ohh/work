package com.wanmi.ares.provider;

import com.wanmi.ares.view.community.CommunityOverviewView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${application.ares.name}", contextId="CommunityQueryProvider")
public interface CommunityQueryProvider {

    @PostMapping("/ares/${application.ares.version}/community/boss/overview")
    CommunityOverviewView bossOverview();
}
