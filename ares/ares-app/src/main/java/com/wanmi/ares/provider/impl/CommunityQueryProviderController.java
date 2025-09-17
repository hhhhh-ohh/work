package com.wanmi.ares.provider.impl;

import com.wanmi.ares.community.model.CommunityOverviewReport;
import com.wanmi.ares.community.service.CommunityService;
import com.wanmi.ares.provider.CommunityQueryProvider;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.community.CommunityOverviewView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author edz
 * @className CommunityQueryProvider
 * @description TODO
 * @date 2023/8/18 10:38
 **/
@RestController
public class CommunityQueryProviderController implements CommunityQueryProvider {
    @Autowired
    CommunityService communityService;
    @Override
    public CommunityOverviewView bossOverview() {
        CommunityOverviewReport report = communityService.queryByBoss();
        return KsBeanUtil.convert(report,CommunityOverviewView.class);
    }
}
