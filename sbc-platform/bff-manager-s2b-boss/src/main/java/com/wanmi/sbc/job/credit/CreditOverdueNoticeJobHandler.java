package com.wanmi.sbc.job.credit;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountPageResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.node.AccoutAssetsType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.notice.NoticeService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author zhengyang
 * @date 2021/04/29 09:49
 * @description <p>授信逾期提醒</p>
 */
@Slf4j
@Component
public class CreditOverdueNoticeJobHandler {

    @Resource
    private CreditAccountQueryProvider creditAccountQueryProvider;
    @Resource
    private NoticeService noticeService;

    @XxlJob(value = "CreditOverdueNoticeJobHandler")
    public void execute() throws Exception {
        log.info("CreditOverdueNoticeJobHandler start");
        // 三天后需要还款的用户
        CreditAccountPageRequest request =
                CreditAccountPageRequest.builder()
                        .endTime(LocalDateTime.now().minusDays(1L))
                        .build();
        // 每次拉100条
        request.setPageSize(100);
        // 分页查询
        BaseResponse<MicroServicePage<CreditAccountPageResponse>> response =
                creditAccountQueryProvider.findCreditAccountForPage(request);
        int currentPage = 0;
        while (true) {
            if (Objects.nonNull(response)
                    && Objects.nonNull(response.getContext())
                    && !CollectionUtils.isEmpty(response.getContext().getContent())) {
                List<CreditAccountPageResponse> accountList = response.getContext().getContent();

                log.info("CreditOverdueNoticeJobHandler accountList,currentPage = {} , size = {}",currentPage,accountList.size());

                currentPage++;
                for (CreditAccountPageResponse account : accountList) {
                    // 发送还款提醒
                    noticeService.sendMessage(
                            NodeType.ACCOUNT_ASSETS.toValue(),
                            AccoutAssetsType.CREDIT_OVERDUE_NOTICE.toValue(),
                            AccoutAssetsType.CREDIT_OVERDUE_NOTICE.getType(),
                            "",
                            account.getCustomerId(),
                            account.getCustomerAccount(),
                            Constants.no);
                }

                if (currentPage >= response.getContext().getTotal()) {
                    response = null;
                } else {
                    request.setPageNum(currentPage);
                    response = creditAccountQueryProvider.findCreditAccountForPage(request);
                }

            } else {
                log.info(
                        "CreditOverdueNoticeJobHandler run over! query page result empty,request:{}",
                        JSON.toJSONString(request));
                break;
            }
        }
    }
}
