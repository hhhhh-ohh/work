package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainCheckRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 5分钟执行一次
 * 超过活动结束时间的待审核的砍价商品，审核状态改为未通过，未通过原因：活动已结束，审核失败
 *
 * @author lipeixian
 * @date: 2022/5/25 16:49
 */
@Component
@Slf4j
public class BargainGoodsStateSyncJobHandler{

    @Autowired
    private BargainGoodsSaveProvider bargainGoodsSaveProvider;

    @Autowired
    private BargainGoodsQueryProvider bargainGoodsQueryProvider;

    private static final int PAGE_SIZE = 100;

    @XxlJob(value = "bargainGoodsStateSyncJobHandler")
    public void execute(String param) throws Exception {
        // 1、分页查询活动过期且为审核的砍价商品
        BargainGoodsQueryRequest bargainGoodsQueryRequest = BargainGoodsQueryRequest.builder().endTimeEnd(LocalDateTime.now()).auditStatus(AuditStatus.WAIT_CHECK).build();
        bargainGoodsQueryRequest.setPageNum(0);
        bargainGoodsQueryRequest.setPageSize(PAGE_SIZE);
        MicroServicePage<BargainGoodsVO> bargainGoodsVOMicroServicePage = bargainGoodsQueryProvider.page(bargainGoodsQueryRequest).getContext();
        int totalPages = bargainGoodsVOMicroServicePage.getTotalPages();
        if (totalPages > 0) {
            int pageNum = 0;
            while (pageNum < totalPages) {
                bargainGoodsQueryRequest.setPageNum(pageNum);
                bargainGoodsQueryRequest.setPageSize(PAGE_SIZE);
                MicroServicePage<BargainGoodsVO> microServicePage = bargainGoodsQueryProvider.page(bargainGoodsQueryRequest).getContext();
                List<BargainGoodsVO> bargainGoodsVOList = microServicePage.getContent();
                if (CollectionUtils.isNotEmpty(bargainGoodsVOList)) {
                    List<Long> bargainGoodsIds = bargainGoodsVOList.stream().map(BargainGoodsVO::getBargainGoodsId).collect(Collectors.toList());
                    // 2、更细审核状态为未通过，未通过原因：活动已结束，审核失败
                    bargainGoodsSaveProvider.bargainGoodsSystemCheck(
                            BargainCheckRequest.builder().bargainGoodsIds(bargainGoodsIds).auditStatus(AuditStatus.NOT_PASS).reasonForRejection("活动已结束，审核失败").build());
                    log.info("批次：{}自动审核的砍价商品为：{}", pageNum, bargainGoodsIds);
                } else {
                    log.info("批次：{}查询的数据为空", pageNum);
                }
                pageNum++;
            }
        } else {
            log.info("本次任务没有要执行的砍价商品数据");
        }
    }
}
