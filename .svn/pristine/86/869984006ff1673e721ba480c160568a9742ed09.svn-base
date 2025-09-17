package com.wanmi.sbc.drawrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.drawrecord.request.RedeemPrizeRequest;
import com.wanmi.sbc.drawrecord.response.RedeemPrizeResponse;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordSaveProvider;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageByCustomerRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordRedeemPrizeRequest;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordPageResponse;
import com.wanmi.sbc.util.CommonUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;


@Tag(name =  "抽奖活动记录表管理API", description =  "DrawRecordController")
@RestController
@Validated
@RequestMapping(value = "/drawrecord")
@Slf4j
public class DrawRecordController {

    @Autowired
    private DrawRecordQueryProvider drawRecordQueryProvider;

    @Autowired
    private DrawRecordSaveProvider drawRecordSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;



    /**
     * 获取中奖列表 => 奖品图片、奖品名称、中奖时间
     * @param
     * @return
     */
    @Operation(summary = "获取中奖列表")
    @RequestMapping(value = "/getRedeemPrizeList", method = RequestMethod.POST)
    public BaseResponse<DrawRecordPageResponse> getPrizeList(@RequestBody DrawRecordPageByCustomerRequest drawRecordPageByCustomerRequest) {
        CustomerVO customerVO = commonUtil.getCustomerNotValidCustomer();
        DrawRecordPageRequest drawRecordPageRequest = new DrawRecordPageRequest();
        drawRecordPageRequest.setCustomerAccount(customerVO.getCustomerAccount());
        drawRecordPageRequest.setCustomerId(customerVO.getCustomerId());
        drawRecordPageRequest.setDrawStatus(NumberUtils.INTEGER_ONE);
        drawRecordPageRequest.setPageNum(drawRecordPageByCustomerRequest.getPageNum());
        drawRecordPageRequest.setPageSize(drawRecordPageByCustomerRequest.getPageSize());
        drawRecordPageRequest.setActivityId(drawRecordPageByCustomerRequest.getActivityId());
        drawRecordPageRequest.putSort("id", SortType.DESC.toValue());
        return drawRecordQueryProvider.page(drawRecordPageRequest);
    }

    /**
     * 领取奖品
     * @param
     * @return
     */
    @Operation(summary = "领取奖品")
    @RequestMapping(value = "/redeemPrize", method = RequestMethod.POST)
    public BaseResponse<RedeemPrizeResponse> redeemPrize(@RequestBody @Valid RedeemPrizeRequest redeemPrizeRequest) {
        return BaseResponse.success(RedeemPrizeResponse.builder().drawRecordVO(drawRecordSaveProvider.modifyRedeemPrizeStatus(DrawRecordRedeemPrizeRequest.builder()
                .id(redeemPrizeRequest.getId())
                .redeemPrizeStatus(NumberUtils.INTEGER_ONE)
                .consigneeName(redeemPrizeRequest.getConsigneeName())
                .consigneeNumber(redeemPrizeRequest.getConsigneeNumber())
                .detailAddress(redeemPrizeRequest.getDeliveryAddress())
                .redeemPrizeTime(LocalDateTime.now())
                .customerId(commonUtil.getOperatorId())
                .build()).getContext().getDrawRecordVO()).build());
    }


    /**
     * 获取中奖记录
     * @param
     * @return
     */
    @Operation(summary = "获取中奖记录根据记录id")
    @GetMapping("/{id}")
    public BaseResponse<DrawRecordByIdResponse> getDrawrecordById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DrawRecordByIdRequest idReq = new DrawRecordByIdRequest();
        idReq.setId(id);
        BaseResponse<DrawRecordByIdResponse> response = drawRecordQueryProvider.getById(idReq);
        if (!Objects.equals(response.getContext().getDrawRecordVO().getCustomerId(), commonUtil.getOperatorId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return response;
    }
}
