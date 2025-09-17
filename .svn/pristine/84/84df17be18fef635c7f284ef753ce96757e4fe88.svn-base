package com.wanmi.sbc.customer.service;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsStatisticsResponse;
import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewListRequest;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewListResponse;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.ledgeraccount.MobileLedgerAccountBaseService;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordListRequest;
import com.wanmi.sbc.marketing.api.response.distributionrecord.DistributionRecordListResponse;
import com.wanmi.sbc.marketing.bean.enums.CommissionReceived;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CustomerAccountBaseService {

	@Autowired
	private CustomerFundsQueryProvider customerFundsQueryProvider;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private DistributionRecordQueryProvider distributionRecordQueryProvider;

	@Autowired
	private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

	@Autowired
	private LedgerFundsQueryProvider ledgerFundsQueryProvider;

	@Autowired
	private MobileLedgerAccountBaseService ledgerAccountBaseService;

	public BaseResponse<CustomerFundsStatisticsResponse> getCustomerFundsStatisticsResponseBaseResponse() {
		CustomerFundsByCustomerIdRequest request = new CustomerFundsByCustomerIdRequest();
		request.setCustomerId(commonUtil.getOperatorId());
		CustomerFundsByCustomerIdResponse idResponse = customerFundsQueryProvider.getByCustomerId(request).getContext();

		//获取当前用户的分销员id
		//待入账佣金金额 排除入账失败数据
		DistributionRecordListResponse distributionRecordListResponse = distributionRecordQueryProvider.list(
				DistributionRecordListRequest
						.builder()
						.distributorCustomerId(commonUtil.getOperatorId())
						.commissionState(CommissionReceived.UNRECEIVE)
						.deleteFlag(DeleteFlag.NO)
						.build()).getContext();
		// 获得待入账得分销佣金
		BigDecimal commissionTotal = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(distributionRecordListResponse.getDistributionRecordVOList())){
			commissionTotal = distributionRecordListResponse.getDistributionRecordVOList().stream()
					.map(DistributionRecordVO::getCommissionGoods).reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		DistributionInviteNewListResponse distributionInviteNewListResponse = distributionInviteNewQueryProvider.listDistributionInviteNewRecord(
				DistributionInviteNewListRequest
						.builder()
						.requestCustomerId(commonUtil.getOperatorId())
						.isRewardRecorded(InvalidFlag.NO)
						.build()
		).getContext();
		// 获得待入账得邀新奖励金额
		BigDecimal rewardCashTotal = new BigDecimal(0);
		if(CollectionUtils.isNotEmpty(distributionInviteNewListResponse.getInviteNewListVOList())){
			rewardCashTotal = distributionInviteNewListResponse.getInviteNewListVOList().stream()
					.map(DistributionInviteNewForPageVO::getRewardCash).reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		BigDecimal blockedBalanceTotal = commissionTotal.add(rewardCashTotal);

		BigDecimal accountBalance = idResponse.getAccountBalance() == null ? BigDecimal.ZERO : idResponse.getAccountBalance();
		BigDecimal ledgerWithdrawAmount = BigDecimal.ZERO;
		BigDecimal ledgerAlreadyDrawAmount = BigDecimal.ZERO;
		if (ledgerAccountBaseService.getGatewayOpen()) {
			LedgerFundsVO ledgerFundsVO = ledgerFundsQueryProvider.getByCustomerId(LedgerFundsByCustomerIdRequest.builder()
					.customerId(commonUtil.getOperatorId()).build()).getContext().getLedgerFundsVO();
			if (ledgerFundsVO != null) {
				accountBalance = accountBalance.add(ledgerFundsVO.getWithdrawnAmount());
				ledgerWithdrawAmount = ledgerFundsVO.getWithdrawnAmount();
				ledgerAlreadyDrawAmount = ledgerFundsVO.getAlreadyDrawAmount();
			}
		}

		return BaseResponse.success(new CustomerFundsStatisticsResponse(accountBalance, blockedBalanceTotal, idResponse.getWithdrawAmount() == null ?
				BigDecimal.ZERO : idResponse.getWithdrawAmount(), idResponse.getAlreadyDrawAmount() == null ?
				BigDecimal.ZERO : idResponse.getAlreadyDrawAmount(), ledgerWithdrawAmount, ledgerAlreadyDrawAmount));
	}


}
