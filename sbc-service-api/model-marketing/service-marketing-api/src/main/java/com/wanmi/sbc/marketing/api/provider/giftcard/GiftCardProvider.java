package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDeleteRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardNewRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className GiftCardQueryProvider
 * @description 礼品卡查询业务
 * @date 2022/12/8 16:29
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardProvider")
public interface GiftCardProvider {

    /**
     * @description    新增礼品卡
     * @author  wur
     * @date: 2022/12/9 10:26
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card/add")
    BaseResponse add(@RequestBody @Valid GiftCardNewRequest request);

    /**
     * @description    编辑礼品卡
     * @author  wur
     * @date: 2022/12/9 10:25
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card/save")
    BaseResponse save(@RequestBody @Valid GiftCardSaveRequest request);

    /**
     * @description     删除礼品卡
     * @author  wur
     * @date: 2022/12/9 10:26
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card/delete")
    BaseResponse delete(@RequestBody @Valid GiftCardDeleteRequest request);

}
