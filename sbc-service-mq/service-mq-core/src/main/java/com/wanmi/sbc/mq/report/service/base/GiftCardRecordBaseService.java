package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBillQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchPageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailPageResponse;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @description 礼品卡记录服务
 * @author malianfeng
 * @date 2022/12/22 15:55
 */
@Service
@Slf4j
public class GiftCardRecordBaseService {

    @Autowired private GiftCardBatchQueryProvider giftCardBatchQueryProvider;

    @Autowired private GiftCardDetailQueryProvider giftCardDetailQueryProvider;

    @Autowired private GiftCardBillQueryProvider giftCardBillQueryProvider;

    /**
     * @description 批量制卡/发卡记录导出
     * @author malianfeng
     * @date 2022/12/22 16:07
     * @param operator
     * @param batchNoList
     * @return java.util.List<com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO>
     */
    @ReturnSensitiveWords(functionName = "f_gift_card_batch_list_sign_word")
    public List<GiftCardBatchVO> getGiftCardBatchList(Operator operator, List<String> batchNoList) {
        GiftCardBatchPageRequest pageRequest = new GiftCardBatchPageRequest();
        pageRequest.setPageSize(batchNoList.size());
        pageRequest.setBatchNoList(batchNoList);
        GiftCardBatchPageResponse response = giftCardBatchQueryProvider.page(pageRequest).getContext();
        if (Objects.nonNull(response) && Objects.nonNull(response.getGiftCardBatchVOPage())) {
            return response.getGiftCardBatchVOPage().getContent();
        }
        return Collections.emptyList();
    }

    /**
     * @description 批量制卡/发卡查看记录导出
     * @author malianfeng
     * @date 2022/12/22 16:07
     * @param operator
     * @param pageRequest
     * @return java.util.List<com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO>
     */
    @ReturnSensitiveWords(functionName = "f_gift_card_detail_export_sign_word")
    public List<GiftCardDetailVO> getGiftCardDetailList(Operator operator, GiftCardDetailPageRequest pageRequest) {
        GiftCardDetailPageResponse response = giftCardDetailQueryProvider.page(pageRequest).getContext();
        if (Objects.nonNull(response) && Objects.nonNull(response.getGiftCardDetailVOPage())) {
            return response.getGiftCardDetailVOPage().getContent();
        }
        return Collections.emptyList();
    }

    /**
     * @description 使用记录导出
     * @author malianfeng
     * @date 2022/12/23 13:52
     * @param operator
     * @param pageRequest
     * @return java.util.List<com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO>
     */
    @ReturnSensitiveWords(functionName = "f_gift_card_bill_export_sign_word")
    public List<GiftCardBillVO> getGiftCardBillList(Operator operator, GiftCardBillPageRequest pageRequest) {
        GiftCardBillPageResponse response = giftCardBillQueryProvider.page(pageRequest).getContext();
        if (Objects.nonNull(response) && Objects.nonNull(response.getGiftCardBillVOPage())) {
            return response.getGiftCardBillVOPage().getContent();
        }
        return Collections.emptyList();
    }

}
