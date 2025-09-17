package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CreditRepayDetailResponse;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountCheckResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountForRepayResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.account.credit.service.CreditAccountQueryService;
import com.wanmi.sbc.account.credit.service.CreditOrderQueryService;
import com.wanmi.sbc.account.credit.service.CreditRepayQueryService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/3/2 17:23
 * @description <p> </p>
 */
@RestController
public class CreditRepayQueryController implements CreditRepayQueryProvider {

    @Autowired
    private CreditRepayQueryService creditRepayQueryService;

    @Autowired
    private CreditAccountQueryService creditAccountQueryService;

    @Autowired
    private CreditOrderQueryService creditOrderQueryService;

    @Override
    public BaseResponse<MicroServicePage<CreditRepayPageResponse>> findCreditHasRepaidPage(@RequestBody @Valid CreditRepayPageRequest request) {
        MicroServicePage<CreditRepayPageResponse> response = creditRepayQueryService.findCreditHasRepaidPage(request);
        return BaseResponse.success(response);
    }

    /***
     * 根据订单查询还款单号和还款状态
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CreditRepayPageResponse> findRepayOrderByOrderId(@RequestBody CreditOrderQueryRequest request) {
        return BaseResponse.success(creditRepayQueryService.findRepayOrderByOrderId(request));
    }

    @Override
    public BaseResponse<MicroServicePage<CreditRepayDetailResponse>> getCreditRepay(@RequestBody @Valid CreditRepayDetailRequest request) {
        MicroServicePage<CreditRepayDetailResponse> response = creditRepayQueryService.getCreditRepay(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CreditAccountCheckResponse> checkCreditAccountHasRepaid(@RequestBody @Valid CreditAccountForRepayRequest request) {
        CreditAccountCheckResponse checkResponse = new CreditAccountCheckResponse();
        // 1、根据客户id查询授信账户
        CustomerCreditAccount creditAccount = creditAccountQueryService.getAccountInfoByCustomerId(request.getCustomerId());
        // 2、授信账户不存在
        if (Objects.isNull(creditAccount)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 3、待还款为空 或 待还款为0，当前账号授信期额度全部还款完成，toast：当前暂无待还金额
        if (Objects.isNull(creditAccount.getRepayAmount())
                || creditAccount.getRepayAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020016);
        }
        checkResponse.setWaitRepay(Boolean.FALSE);
        // 4、查询该授信账户是否有正在还款的记录
        CustomerCreditRepay creditRepay = creditRepayQueryService.findCreditRepayByCustomerId(request.getCustomerId());
        if (Objects.nonNull(creditRepay)) {
            checkResponse.setWaitRepay(Boolean.TRUE);
        }

        return BaseResponse.success(checkResponse);
    }

    /**
     * 在线还款查询授信账户信息和还款信息
     *
     * @param request {@link CreditAccountForRepayRequest}
     * @return
     */
    @Override
    public BaseResponse<CreditAccountForRepayResponse> getCreditAccountByCustomerIdForRepay(@RequestBody @Valid CreditAccountForRepayRequest request) {
        CreditAccountForRepayResponse repayResponse = new CreditAccountForRepayResponse();
        // 1、根据客户id查询授信账户
        CustomerCreditAccount creditAccount = creditAccountQueryService.getAccountInfoByCustomerId(request.getCustomerId());
        // 2、授信账户不存在
        if (Objects.isNull(creditAccount)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 3、待还款为空 或 待还款为0，当前账号授信期额度全部还款完成，toast：当前暂无待还金额
        if (Objects.isNull(creditAccount.getRepayAmount())
                || creditAccount.getRepayAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020016);
        }

        // 返回对象
        CustomerCreditRepayVO creditRepayVO = new CustomerCreditRepayVO();
        List<CustomerCreditOrder> creditOrderList = new ArrayList<>();
        // 4、赋值授信额度和剩余待还
        creditRepayVO.setCustomerId(creditAccount.getCustomerId());
        creditRepayVO.setCreditAmount(creditAccount.getCreditAmount());
        creditRepayVO.setTotalRepayAmount(creditAccount.getRepayAmount());

        repayResponse.setWaitRepay(Boolean.FALSE);

        // 5、查询该授信账户是否有正在还款的记录
        CustomerCreditRepay creditRepay = creditRepayQueryService.findCreditRepayByCustomerId(request.getCustomerId());
        if (Objects.nonNull(creditRepay)) {
            creditRepayVO.setRepayAmount(creditRepay.getRepayAmount());
            creditRepayVO.setRepayNotes(creditRepay.getRepayNotes());
            creditRepayVO.setRepayOrderCode(creditRepay.getRepayOrderCode());
            creditRepayVO.setRepayStatus(creditRepay.getRepayStatus());
            creditRepayVO.setRepayWay(creditRepay.getRepayWay());
            creditRepayVO.setRepayFile(creditRepay.getRepayFile());
            creditRepayVO.setAuditRemark(creditRepay.getAuditRemark());
            creditRepayVO.setRepayTime(creditRepay.getRepayTime());

            repayResponse.setWaitRepay(Boolean.TRUE);

            // 6、根据还款单号来查询关联订单
            creditOrderList = this.getOrderListByRepayOrderCode(request.getCustomerId(), creditRepay.getRepayOrderCode());
        }

        repayResponse.setCustomerCreditRepayVO(creditRepayVO);
        repayResponse.setCustomerCreditOrderVOList(KsBeanUtil.convertList(creditOrderList, CustomerCreditOrderVO.class));

        return BaseResponse.success(repayResponse);
    }

    /***
     * 分页查询授信还款概览列表
     * @param request
     * @return
     */
    @Override
    public BaseResponse<MicroServicePage<CreditRepayOverviewPageResponse>> findRepayOrderPage(CreditRepayOverviewPageRequest request) {
        return BaseResponse.success(this.creditRepayQueryService.findRepayOrderPage(request));
    }

    @Override
    public BaseResponse<CustomerCreditRepayByRepayCodeResponse> getCreditRepayByRepayCode(@RequestBody @Valid CustomerCreditRepayByRepayCodeRequest request) {
        CustomerCreditRepay creditRepay = creditRepayQueryService.getCreditRepayByRepayCode(request.getRepayCode());
        CustomerCreditRepayByRepayCodeResponse response =
                CustomerCreditRepayByRepayCodeResponse.builder().customerCreditRepayVO(creditRepayQueryService.wrapperVo(creditRepay)).build();
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CreditRepayPageResponse> getCreditHasRepaidDetail(CreditHasRepaidDetailRequest request) {
        CreditRepayPageResponse response = creditRepayQueryService.getCreditHasRepaidDetail(request.getId(),
                request.getCustomerId());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CustomerCreditRepayAndOrdersByRepayCodeResponse> getCreditRepayAndOrdersByRepayCode(@RequestBody @Valid  CustomerCreditRepayByRepayCodeRequest request) {
        CustomerCreditRepay customerCreditRepay = creditRepayQueryService.getCreditRepayByRepayCode(request.getRepayCode());
        CustomerCreditRepayAndOrdersByRepayCodeResponse response = new CustomerCreditRepayAndOrdersByRepayCodeResponse();
        response.setCustomerCreditRepayVO(creditRepayQueryService.wrapperVo(customerCreditRepay));
        RepayOrderPageRequest queryReq = new RepayOrderPageRequest();
        queryReq.setRepayOrderCode(customerCreditRepay.getRepayOrderCode());
        List<CustomerCreditOrderVO> creditOrderVOList = KsBeanUtil.convertList(creditOrderQueryService.getCreditOrderList(queryReq), CustomerCreditOrderVO.class);
        response.setCreditOrderVOList(creditOrderVOList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CreditRepayPageResponse> findFinishRepayOrderByOrderId(@RequestBody @Valid CreditOrderQueryRequest request) {
        CreditRepayPageResponse response = creditRepayQueryService.findFinishRepayByOrderId(request.getOrderId());
        return BaseResponse.success(response);
    }


    /**
     * 根据还款单号来查询关联订单
     *
     * @param repayOrderCode
     * @return
     */
    private List<CustomerCreditOrder> getOrderListByRepayOrderCode(String customerId, String repayOrderCode) {
        return creditOrderQueryService.getCreditOrderList(RepayOrderPageRequest.builder().customerId(customerId).repayOrderCode(repayOrderCode).build());
    }
}
