package com.wanmi.sbc.job;

import com.wanmi.sbc.customer.api.provider.wechatvideo.VideoUserProvider;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserAddRequest;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserBatchSaveRequest;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformPromoterProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.promoter.SellPlatformPromoterListRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformPromoterInfoVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 全量同步微信推广员视频号昵称
 */
@Component
public class WechatVideoUserSyncHandler {

    private static final int MAX_SIZE = 100;

    @Autowired private SellPlatformPromoterProvider sellPlatformPromoterProvider;

    @Autowired private VideoUserProvider videoUserProvider;

    @XxlJob(value = "WechatVideoUserSyncHandler")
    public void execute() throws Exception {
        SellPlatformPromoterListRequest listRequest = SellPlatformPromoterListRequest.builder()
                .page(1).page_size(MAX_SIZE).build();
        List<SellPlatformPromoterInfoVO> promoters;
        List<VideoUserAddRequest> addRequestList = new ArrayList<>();
        do {
            promoters = sellPlatformPromoterProvider.getPromoterList(listRequest).getContext().getPromoters();
            promoters.forEach(item -> {
                VideoUserAddRequest addRequest = new VideoUserAddRequest();
                addRequest.setPromoterId(item.getPromoter_id());
                addRequest.setFinderNickname(item.getFinder_nickname());
                addRequest.setPromoterOpenId(item.getPromoter_openid());
                addRequestList.add(addRequest);
            });
            listRequest.setPage(listRequest.getPage() + 1);
        } while (promoters.size() == MAX_SIZE);
        // 执行批量保存
        videoUserProvider.batchSave(VideoUserBatchSaveRequest.builder().addRequestList(addRequestList).build());
    }
}
