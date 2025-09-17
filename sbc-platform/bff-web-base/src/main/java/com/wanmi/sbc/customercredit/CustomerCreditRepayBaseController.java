package com.wanmi.sbc.customercredit;

import com.wanmi.sbc.account.api.constant.AccountConstants;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.api.response.credit.CustomerCreditRepayAddResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountCheckResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountForRepayResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customercredit.response.CustomerCreditRepayDetailResponse;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.api.request.trade.TradePageCriteriaRequest;
import com.wanmi.sbc.order.api.response.trade.TradeListAllResponse;
import com.wanmi.sbc.order.api.response.trade.TradePageCriteriaResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.trade.PayServiceHelper;
import com.wanmi.sbc.util.CommonUtil;
import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author chenli
 * @ClassName CustomerCreditRepayBaseController
 * @Description 客户授信账户在线还款bff
 * @date 2021/3/3 13:59
 */
@Tag(description= "客户授信账户在线还款API", name = "CustomerCreditRepayBaseController")
@RestController
@Validated
@RequestMapping(value = "/credit/repay")
public class CustomerCreditRepayBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private RedissonClient redissonClient;


    @Operation(summary = "在线还款校验当前账号授信期额度是否全部还款完成")
    @GetMapping("/check")
    public BaseResponse<CreditAccountCheckResponse> checkCreditAccountHasRepaid() {
        // 校验授信账户信息和授信还款信息
        return creditRepayQueryProvider.checkCreditAccountHasRepaid(
                CreditAccountForRepayRequest.builder().customerId(commonUtil.getOperatorId()).build());
    }

    @Operation(summary = "在线还款查询客户授信账户")
    @GetMapping("/account")
    public BaseResponse<CustomerCreditRepayDetailResponse> getCreditAccountByCustomerIdForRepay() {
        // 查询授信账户信息和授信还款信息
        CreditAccountForRepayResponse repayResponse = creditRepayQueryProvider.getCreditAccountByCustomerIdForRepay(
                CreditAccountForRepayRequest.builder().customerId(commonUtil.getOperatorId()).build()).getContext();

        if (Objects.nonNull(repayResponse)) {
            // 根据授信还款单信息查询还款关联的订单
            List<TradeVO> tradeVOList = wrapperOrderList(repayResponse.getCustomerCreditOrderVOList());
            if (CollectionUtils.isNotEmpty(tradeVOList)) {
                //扭转预售商品支付尾款状态为已作废
                tradeVOList.forEach(tradeVO -> payServiceHelper.fillTradeBookingTimeOut(tradeVO));
                payServiceHelper.wrapperCreditTrade(tradeVOList);
            }
            // 组装返回值
            CustomerCreditRepayDetailResponse customerCreditRepayDetailResponse = new CustomerCreditRepayDetailResponse();
            customerCreditRepayDetailResponse.setTradeVOList(tradeVOList);
            customerCreditRepayDetailResponse.setWaitRepay(repayResponse.getWaitRepay());
            customerCreditRepayDetailResponse.setCustomerCreditRepayVO(repayResponse.getCustomerCreditRepayVO());

            return BaseResponse.success(customerCreditRepayDetailResponse);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询可以授信还款订单")
    @PostMapping("/order-list")
    public BaseResponse<TradePageCriteriaResponse> getCanRepayOrderList(@RequestBody @Valid TradeQueryDTO tradeQueryDTO) {
        TradePageCriteriaRequest request = new TradePageCriteriaRequest();
        // 买家是我自己
        tradeQueryDTO.setBuyerId(commonUtil.getOperatorId());
        // 需要授信还款
        tradeQueryDTO.setNeedCreditRepayFlag(Boolean.TRUE);
        // 授信账号信息
        CreditPayInfoVO creditPayInfoVO = new CreditPayInfoVO();
        // 额度有效期未还款的
        creditPayInfoVO.setHasRepaid(Boolean.FALSE);
        tradeQueryDTO.setCreditPayInfo(creditPayInfoVO);

        // 查询参数
        request.setTradePageDTO(tradeQueryDTO);
        MicroServicePage<TradeVO> tradePage = tradeQueryProvider.pageCriteria(request).getContext().getTradePage();
        if (tradePage.getSize() != 0) {
            //扭转预售商品支付尾款状态为已作废
            tradePage.getContent().forEach(tradeVO -> payServiceHelper.fillTradeBookingTimeOut(tradeVO));
            payServiceHelper.wrapperCreditTrade(tradePage.getContent());
        }
        return BaseResponse.success(new TradePageCriteriaResponse(tradePage));
    }

    @Operation(summary = "新增在线还款记录")
    @PostMapping("/add")
    public BaseResponse<CustomerCreditRepayAddResponse> add(@RequestBody @Valid CustomerCreditRepayAddRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        //redis锁，防止同一用户有多条还款记录
        RLock rLock = redissonClient.getFairLock(AccountConstants.CUSTOMER_CREDIT_REPAY + request.getCustomerId());
        rLock.lock();
        try {
            CreditPayInfoVO creditPayInfoVO = new CreditPayInfoVO();
            creditPayInfoVO.setHasRepaid(Boolean.FALSE);
            List<String> orderIds = request.getOrderIds();
            List<TradeVO> trades = tradeQueryProvider.listAll(TradeListAllRequest.builder()
                    .tradeQueryDTO(TradeQueryDTO.builder()
                            .ids(orderIds.toArray(new String[0]))
                            .creditPayInfo(creditPayInfoVO)
                            .build())
                    .build()).getContext().getTradeVOList();
            if (trades.size() != orderIds.size()) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020029);
            }
            payServiceHelper.wrapperCreditTrade(trades);
            //关联订单是否在退款中
            if (trades.stream().anyMatch(TradeVO::getReturningFlag)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020030);
            }
            //判断订单是否可以还款
            if(trades.stream().anyMatch(tradeVO -> !tradeVO.getCanCheckFlag())){
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020031);
            }
            // 订单是否属于当前用户
            trades.forEach(tradeVO -> {
                if (!StringUtils.equals(tradeVO.getBuyer().getId(), request.getCustomerId())){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000015);
                }
            });
            request.setRepayAmount(trades.stream()
                    .map(TradeVO::getCanRepayPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return customerCreditRepayProvider.add(request);
        } finally {
            rLock.unlock();
        }
    }

    @Operation(summary = "取消还款记录")
    @GetMapping("/cancel")
    public BaseResponse cancel() {
        String customerId = commonUtil.getOperatorId();
        //判断用户是否有还款中的还款记录
        RLock rLock = redissonClient.getFairLock(AccountConstants.CUSTOMER_CREDIT_REPAY + customerId);
        rLock.lock();
        try {
            CustomerCreditRepayCancelRequest request = new CustomerCreditRepayCancelRequest();
            request.setUserId(customerId);
            customerCreditRepayProvider.cancel(request);
            return BaseResponse.SUCCESSFUL();
        } finally {
            rLock.unlock();
        }
    }


    /**
     * 查询订单信息
     *
     * @param orderList
     * @return
     */
    private List<TradeVO> wrapperOrderList(List<CustomerCreditOrderVO> orderList) {
        if (Collections.isEmpty(orderList)) {
            return new ArrayList<>();
        }
        String[] orderIds = orderList.stream()
                .map(CustomerCreditOrderVO::getOrderId)
                .distinct()
                .toArray(String[]::new);
        BaseResponse<TradeListAllResponse> trades = tradeQueryProvider.listAll(TradeListAllRequest.builder()
                .tradeQueryDTO(TradeQueryDTO.builder().ids(orderIds).build())
                .build());
        return trades.getContext().getTradeVOList();
    }


    /**
     * 分页查询已还款授信订单列表
     *
     * @return
     */
    @PostMapping("/has-repaid-list")
    @Operation(summary = "分页查询还款记录")
    public BaseResponse<MicroServicePage<CreditRepayPageResponse>> findHasRepaidPage(@RequestBody CreditRepayPageRequest request) {
        //排序
        String operatorId = commonUtil.getOperatorId();
        request.setCustomerId(operatorId);
        return creditRepayQueryProvider.findCreditHasRepaidPage(request);
    }

    /**
     * 还款记录详情
     *
     * @return
     */
    @GetMapping("/has-repaid-detail/{id}")
    @Operation(summary = "还款记录详情")
    public BaseResponse<CreditRepayPageResponse> getCreditHasRepaidDetail(@PathVariable("id") String id) {
        CreditHasRepaidDetailRequest request = CreditHasRepaidDetailRequest.builder()
                .id(id)
                .customerId(commonUtil.getOperatorId())
                .build();
        return creditRepayQueryProvider.getCreditHasRepaidDetail(request);
    }


    @Operation(summary = "校验授信还款单订单状态")
    @GetMapping("/checkRepay")
    public BaseResponse checkRepayOrder() {
        // 查询授信账户信息和授信还款信息
        CreditAccountForRepayResponse repayResponse = creditRepayQueryProvider.getCreditAccountByCustomerIdForRepay(
                CreditAccountForRepayRequest.builder().customerId(commonUtil.getOperatorId()).build()).getContext();

        if (Objects.nonNull(repayResponse)) {
            // 根据授信还款单信息查询还款关联的订单
            List<TradeVO> tradeVOList = wrapperOrderList(repayResponse.getCustomerCreditOrderVOList());
            if (CollectionUtils.isEmpty(tradeVOList)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020029);
            }
            payServiceHelper.wrapperCreditTrade(tradeVOList);
            //订单是否已还款
            boolean repaid = tradeVOList.stream().anyMatch(tradeVO -> Objects.nonNull(tradeVO.getCreditPayInfo()) && tradeVO.getCreditPayInfo().getHasRepaid());
            if (repaid) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020029);
            }
            //订单退单中
            boolean returning = tradeVOList.stream().anyMatch(TradeVO::getReturningFlag);
            if (returning) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020030);
            }
            //还款金额变更
            boolean correctAmount =
                    tradeVOList.stream().map(TradeVO::getCanRepayPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(repayResponse.getCustomerCreditRepayVO().getRepayAmount()) == 0;
            if (!correctAmount) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020029);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据授信还款单号查询授信还款具体信息")
    @GetMapping("/by-repay-code/{repayCode}")
    public BaseResponse<CustomerCreditRepayByRepayCodeResponse> getDetailByRepayCode(@PathVariable String repayCode) {
        CustomerCreditRepayByRepayCodeRequest request = new CustomerCreditRepayByRepayCodeRequest();
        request.setRepayCode(repayCode);
        BaseResponse<CustomerCreditRepayByRepayCodeResponse> codeResponseBaseResponse =
                creditRepayQueryProvider.getCreditRepayByRepayCode(request);
        CustomerCreditRepayVO customerCreditRepayVO =
                codeResponseBaseResponse.getContext().getCustomerCreditRepayVO();
        if (Objects.nonNull(customerCreditRepayVO) &&
                !StringUtils.equals(commonUtil.getOperatorId(), customerCreditRepayVO.getCustomerId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000015);
        }
        return codeResponseBaseResponse;
    }
}
