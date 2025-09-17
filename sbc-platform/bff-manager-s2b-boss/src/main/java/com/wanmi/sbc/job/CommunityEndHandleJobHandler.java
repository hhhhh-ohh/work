package com.wanmi.sbc.job;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityPageResponse;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 定时任务Handler
 * 商品定时上架
 *
 * @author dyt
 */
@Component
@Slf4j
public class CommunityEndHandleJobHandler {

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @XxlJob(value = "communityEndHandleJobHandler")
    public void execute() throws Exception {
        log.info("更新spu定时上架");
        String param = XxlJobHelper.getJobParam();
        LocalDateTime now = LocalDateTime.now();
        CommunityTabStatus status = CommunityTabStatus.ENDED;
        Integer pageSize = 10;
        List<String> activityIds = null;
        if(StringUtils.isNotBlank(param)) {
            JSONObject object = JSON.parseObject(param);
            if(StringUtils.isNotBlank(object.getString("pageSize"))) {
                pageSize = object.getInteger("pageSize");
            }
            //指定活动id
            if(StringUtils.isNotBlank(object.getString("ids"))) {
                activityIds = Arrays.asList(object.getString("ids").split(","));
                now = null;
                status = null;
            }
        }
        CommunityActivityPageRequest pageRequest = CommunityActivityPageRequest.builder()
                .tabType(status)
                .realEndTimeEnd(now)
                .activityIdList(activityIds)
                .generateFlag(Constants.no).build();
        pageRequest.setPageSize(pageSize);
        pageRequest.setPageNum(0);
        MicroServicePage<CommunityActivityVO> activityPage;
        do {
            // 因为商品的状态在不断改变，因此永远查第一页
            activityPage = BaseResUtils
                    .getResultFromRes(communityActivityQueryProvider.page(pageRequest), CommunityActivityPageResponse::getCommunityActivityPage);
            if (Objects.isNull(activityPage) || WmCollectionUtils.isEmpty(activityPage.getContent())) {
                break;
            }
            communityActivityService.generate(activityPage.getContent());
        } while (activityPage.getTotalPages() > 1);

    }
}
