package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillExportRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillForUserPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className GiftCardBillQueryProvider
 * @description 礼品卡使用记录查询接口
 * @date 2022/12/12 11:32 上午
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardBillQueryProvider")
public interface GiftCardBillQueryProvider {

    /**
     * @description 会员礼品卡使用记录分页查询
     * @author  lvzhenwei
     * @date 2022/12/12 11:33 上午
     * @param queryReq
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse>
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card-bill/get-gift-card-bill-page-for-user")
    BaseResponse<GiftCardBillPageResponse> getGiftCardBillPageForUser(@RequestBody @Valid GiftCardBillForUserPageRequest queryReq);

    /**
     * 分页查询礼品卡交易流水API
     *
     * @author 吴瑞
     * @param giftCardBillPageReq 分页请求参数和筛选对象 {@link GiftCardBillPageRequest}
     * @return 礼品卡交易流水分页列表信息 {@link GiftCardBillPageResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/giftcardbill/page")
    BaseResponse<GiftCardBillPageResponse> page(@RequestBody @Valid GiftCardBillPageRequest giftCardBillPageReq);

    /**
     * 礼品卡交易导出数量查询API
     *
     * @author 吴瑞
     * @param request {tableDesc}导出数量查询请求 {@link GiftCardBillExportRequest}
     * @return 礼品卡交易流水数量 {@link Long}
     */
    @PostMapping("/marketing/${application.marketing.version}/giftcardbill/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid GiftCardBillExportRequest request);
}
