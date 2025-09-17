package com.wanmi.sbc.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBillQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillForUserPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardPageResponse;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className GiftCardBillController
 * @description 会员礼品卡使用记录
 * @date 2022/12/12 1:51 下午
 **/
@Slf4j
@Tag(name =  "会员礼品卡使用记录业务API", description =  "GiftCardBillController")
@RestController
@Validated
@RequestMapping(value = "/giftCardBill")
public class GiftCardBillController {

    @Autowired private GiftCardBillQueryProvider giftCardBillQueryProvider;
    @Autowired private CommonUtil commonUtil;

    @RequestMapping(value = "/getGiftCardBillPage", method  = RequestMethod.POST)
    public BaseResponse<GiftCardBillPageResponse> getGiftCardBillPage(@RequestBody @Valid GiftCardBillForUserPageRequest queryReq){
        queryReq.setCustomerId(commonUtil.getOperatorId());
        queryReq.setSortColumn("tradeTime");
        queryReq.setSortType(SortType.DESC.toValue());
        return giftCardBillQueryProvider.getGiftCardBillPageForUser(queryReq);
    }
}
