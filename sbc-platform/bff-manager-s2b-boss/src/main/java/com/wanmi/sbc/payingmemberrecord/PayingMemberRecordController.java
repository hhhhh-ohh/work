package com.wanmi.sbc.payingmemberrecord;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.order.api.response.payingmemberrecord.*;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayingLevelState;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.wanmi.sbc.payingmemberrecord.request.PayingMemberRefundRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "付费会员记录表管理API", description =  "PayingMemberRecordController")
@RestController
@Validated
@RequestMapping(value = "/payingmemberrecord")
public class PayingMemberRecordController {

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    @Autowired
    private PayingMemberRecordProvider payingMemberRecordProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Operation(summary = "分页查询付费会员记录表")
    @PostMapping("/page")
    @ReturnSensitiveWords(functionName = "f_boss_paying_member_record_page_sign_word")
    public BaseResponse<PayingMemberRecordPageResponse> getPage(@RequestBody @Valid PayingMemberRecordPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("recordId", "desc");
        return payingMemberRecordQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询付费会员记录表")
    @PostMapping("/list")
    public BaseResponse<PayingMemberRecordListResponse> getList(@RequestBody @Valid PayingMemberRecordListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return payingMemberRecordQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询付费会员记录表")
    @GetMapping("/{recordId}")
    @ReturnSensitiveWords(functionName = "f_paying_member_sign_word")
    public BaseResponse<PayingMemberRecordByIdResponse> getById(@PathVariable String recordId) {
        if (recordId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayingMemberRecordByIdRequest idReq = new PayingMemberRecordByIdRequest();
        idReq.setRecordId(recordId);
        PayingMemberRecordVO payingMemberRecordVO = payingMemberRecordQueryProvider.getById(idReq)
                .getContext().getPayingMemberRecordVO();
        String customerId = payingMemberRecordVO.getCustomerId();
        Long pointsAvailable = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder()
                .customerId(customerId)
                .build()).getContext().getPointsAvailable();
        payingMemberRecordVO.setPointsAvailable(pointsAvailable);
        return BaseResponse.success(PayingMemberRecordByIdResponse.builder()
                .payingMemberRecordVO(payingMemberRecordVO)
                .build());
    }

    @Operation(summary = "新增付费会员记录表")
    @PostMapping("/add")
    public BaseResponse<PayingMemberRecordAddResponse> add(@RequestBody @Valid PayingMemberRecordAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return payingMemberRecordProvider.add(addReq);
    }

    @Operation(summary = "修改付费会员记录表")
    @PutMapping("/modify")
    public BaseResponse<PayingMemberRecordModifyResponse> modify(@RequestBody @Valid PayingMemberRecordModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return payingMemberRecordProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除付费会员记录表")
    @DeleteMapping("/{recordId}")
    public BaseResponse deleteById(@PathVariable String recordId) {
        if (recordId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayingMemberRecordDelByIdRequest delByIdReq = new PayingMemberRecordDelByIdRequest();
        delByIdReq.setRecordId(recordId);
        return payingMemberRecordProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除付费会员记录表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecordDelByIdListRequest delByIdListReq) {
        return payingMemberRecordProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出付费会员记录表列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        PayingMemberRecordExportRequest payingMemberRecordListRequest = JSON.parseObject(decrypted, PayingMemberRecordExportRequest.class);
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(payingMemberRecordListRequest));
        exportDataRequest.setTypeCd(ReportType.PAYING_MEMBER_RECORD);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "退款")
    @PostMapping("/refund")
    @MultiSubmitWithToken
    @ReturnSensitiveWords(functionName = "f_boss_return_paying_member_sign_word")
    public BaseResponse<PayingMemberRecordPageResponse> refund(@RequestBody @Valid PayingMemberRefundRequest refundRequest) {
        String recordId = refundRequest.getRecordId();
        RLock rLock = redissonClient.getFairLock(recordId);
        try {
            rLock.lock();
            PayingMemberRecordModifyRequest payingMemberRecordModifyRequest = KsBeanUtil
                    .convert(refundRequest, PayingMemberRecordModifyRequest.class);
            BigDecimal returnAmount = payingMemberRecordModifyRequest.getReturnAmount();
            PayingMemberRecordVO payingMemberRecord = payingMemberRecordQueryProvider.getById(PayingMemberRecordByIdRequest
                    .builder()
                    .recordId(recordId)
                    .build()).getContext().getPayingMemberRecordVO();
            //获取付费金额
            BigDecimal payAmount = payingMemberRecord.getPayAmount();
            if (payAmount.compareTo(returnAmount) < 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050042, new Object[]{payAmount});
            }


            if (PayingLevelState.EXPIRED.toValue() == payingMemberRecord.getLevelState()
                    || PayingLevelState.REFUND.toValue() == payingMemberRecord.getLevelState()) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020028);
            }
            payingMemberRecordProvider.refundPayingMember(payingMemberRecordModifyRequest);
        } finally{
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

}
