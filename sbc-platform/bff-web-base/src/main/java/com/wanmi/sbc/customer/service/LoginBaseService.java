package com.wanmi.sbc.customer.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.bean.RedisHsetBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerSaveProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewPageRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailWithNotDeleteByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerAddRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByInviteCodeRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountRequest;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewPageResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerAddResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerByInviteCodeResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByAccountResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 会员
 * Created by Daiyitian on 2017/4/19.
 */
@Slf4j
@Service
public class LoginBaseService {

	@Autowired
	private DistributionCacheService distributionCacheService;

	@Autowired
	private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

	@Autowired
	private DistributionCustomerSaveProvider distributionCustomerSaveProvider;

	@Autowired
	private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

	@Autowired
	private RedisUtil redisService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CustomerCacheService customerCacheService;

	@Autowired
	private CustomerSiteQueryProvider customerSiteQueryProvider;

	@Autowired
	private CustomerDetailQueryProvider customerDetailQueryProvider;

	public static final String CUSTOMER_ACCOUNT = "customerAccount";

	public static final String VERIFY_CODE = "verifyCode";

	/**
	 * 验证邀请ID、邀请码
	 *
	 * @param inviteeId
	 * @param inviteCode
	 * @return
	 */
	public DistributionCustomerVO checkInviteIdAndInviteCode(String inviteeId, String inviteCode) {
		DistributionCustomerVO distributionCustomerVO = new DistributionCustomerVO();
		RegisterLimitType registerLimitType = distributionCacheService.getRegisterLimitType();
		DefaultFlag defaultFlag = distributionCacheService.queryOpenFlag();
		DefaultFlag inviteOpenFlag = distributionCacheService.queryInviteOpenFlag();

		if (defaultFlag == DefaultFlag.YES &&
				registerLimitType == RegisterLimitType.INVITE &&
				StringUtils.isBlank(inviteCode) &&
				StringUtils.isBlank(inviteeId)) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010025);
		}

		if (StringUtils.isNotBlank(inviteeId)) {
			DistributionInviteNewPageRequest inviteNewPageRequest = new DistributionInviteNewPageRequest();
			inviteNewPageRequest.setInvitedNewCustomerId(inviteeId);
			DistributionInviteNewPageResponse inviteNewPage =
					distributionInviteNewQueryProvider.findDistributionInviteNewRecord(inviteNewPageRequest).getContext();
			distributionCustomerVO.setCustomerId(inviteeId);
			if (inviteNewPage.getTotal() == 0 || CollectionUtils.isEmpty(inviteNewPage.getRecordList())) {
				return distributionCustomerVO;
			}
			String customerId = inviteNewPage.getRecordList().get(0).getRequestCustomerId();
			distributionCustomerVO.setInviteCustomerIds(customerId);
			return distributionCustomerVO;
		} else {
			if (StringUtils.isNotBlank(inviteCode)) {
				BaseResponse<DistributionCustomerByInviteCodeResponse> baseResponse = distributionCustomerQueryProvider.getByInviteCode(new DistributionCustomerByInviteCodeRequest(inviteCode));
				DistributionCustomerByInviteCodeResponse response = baseResponse.getContext();
				distributionCustomerVO = response.getDistributionCustomerVO();
				if (Objects.isNull(distributionCustomerVO)) {
					throw new SbcRuntimeException(CustomerErrorCodeEnum.K010026);
				}
				return distributionCustomerVO;
			}
		}
		return distributionCustomerVO;
	}

	/**
	 * 新增分销员
	 * @param customerId
	 * @param customerAccount
	 * @param customerName
	 */
	public void addDistributionCustomer(String customerId, String customerAccount, String customerName,String inviteCustomerIds) {
		DistributionCustomerAddRequest request = new DistributionCustomerAddRequest();
		request.setCustomerId(customerId);
		request.setCustomerAccount(customerAccount);
		request.setCustomerName(StringUtils.isBlank(customerName) ? customerAccount : customerName);
		request.setCreateTime(LocalDateTime.now());
		request.setInviteCount(NumberUtils.INTEGER_ZERO);
		// 邀新奖励
		request.setRewardCash(BigDecimal.ZERO);
		// 未入账邀新奖励
		request.setRewardCashNotRecorded(BigDecimal.ZERO);
		request.setInviteCustomerIds(inviteCustomerIds);
		BaseResponse<DistributionCustomerAddResponse> customerAddRespons = distributionCustomerSaveProvider.add(request);
		if (Objects.nonNull(customerAddRespons) && Objects.nonNull(customerAddRespons.getContext())
				&& Objects.nonNull(customerAddRespons.getContext().getDistributionCustomerVO())) {
			log.info("新增分销员信息成功");
		} else {
			log.error("新增分销员信息失败");
		}
	}

	public String getTemporaryCode(){
		String customerAccount = commonUtil.getCustomer().getCustomerAccount();
		//验证输入的手机号码格式
		if (!ValidateUtil.isPhone(customerAccount)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//是否可以发送
		if (!customerCacheService.validateSendMobileCode(customerAccount)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
		}

		//账号是否注册
		CustomerByAccountRequest request = new CustomerByAccountRequest();
		request.setCustomerAccount(customerAccount);
		BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
		CustomerByAccountResponse response = responseBaseResponse.getContext();
		if (Objects.isNull(response)) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
		}
		validateAccountStatus(response);
		CustomerDetailVO customerDetail = this.findCustomerDetailByCustomerId(response.getCustomerId());
		if (customerDetail == null) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
		}

		//是否禁用
		if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
					.getForbidReason()});
		}

		//删除验证错误次数
		redisService.delete(CacheKeyConstant.YZM_CUSTOMER_LOGIN_NUM.concat(customerAccount));
		String verifyCode = customerCacheService.sendMobileCode(CacheKeyConstant.YZM_CUSTOMER_LOGIN, customerAccount);
		List<RedisHsetBean> fieldValues = new ArrayList<>();
		RedisHsetBean redisHsetBean1 = new RedisHsetBean();
		redisHsetBean1.setField(CUSTOMER_ACCOUNT);
		redisHsetBean1.setValue(customerAccount);
		fieldValues.add(redisHsetBean1);
		RedisHsetBean redisHsetBean2 = new RedisHsetBean();
		redisHsetBean2.setField(VERIFY_CODE);
		redisHsetBean2.setValue(verifyCode);
		fieldValues.add(redisHsetBean2);
		String key = MD5Util.md5Hex(customerAccount);
		redisService.hsetPipeline(key, fieldValues);
		redisService.expireByMinutes(key, 5L);
		return key;
	}

	public CustomerDetailVO findCustomerDetailByCustomerId(String customerId) {
		return customerDetailQueryProvider.getCustomerDetailWithNotDeleteByCustomerId(
				CustomerDetailWithNotDeleteByCustomerIdRequest.builder().customerId(customerId).build()).getContext();
	}

	/**
	 *校验用户是否锁定
	 * @param
	 */
	public void validateAccountStatus(CustomerByAccountResponse customer){
		//锁定时间
		LocalDateTime now = LocalDateTime.now();
		if (customer.getLoginLockTime() != null) {
			if (now.isBefore(customer.getLoginLockTime().plus(Constants.NUM_30, ChronoUnit.MINUTES))) {
				long minutes = ChronoUnit.MINUTES.between(customer.getLoginLockTime().toLocalTime(), now.toLocalTime());
				minutes = 30 - minutes;
				if (minutes < 1) {
					minutes = 1;
				}
				throw new SbcRuntimeException(CustomerErrorCodeEnum.K010007, new Object[]{minutes});
			}
		}
	}


}
