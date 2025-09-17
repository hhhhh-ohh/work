package com.wanmi.sbc.order.payingmemberrecord.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsAddAmountRequest;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.enums.RepeatType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.enums.RightsCouponSendType;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsCouponAnalyseProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelListRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelModifyRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCustomerRightsProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeQueryRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeRecycleByIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.RightsCouponRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordModifyRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordQueryRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordRightsRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayingLevelState;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import com.wanmi.sbc.order.payingmemberpayrecord.service.PayingMemberPayRecordService;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import com.wanmi.sbc.order.payingmemberrecord.repository.PayingMemberRecordRepository;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>付费记录表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Slf4j
@Service("PayingMemberRecordService")
public class PayingMemberRecordService {
	@Autowired
	private PayingMemberRecordRepository payingMemberRecordRepository;

	@Autowired
	private CustomerLevelRightsCouponAnalyseProvider customerLevelRightsCouponAnalyseProvider;

	@Autowired
	private CouponCustomerRightsProvider couponCustomerRightsProvider;

	@Autowired
	private PayingMemberPayRecordService payingMemberPayRecordService;

	@Autowired
	private PaySettingQueryProvider paySettingQueryProvider;

	@Autowired
	private PayProvider payProvider;

	@Autowired
	private CustomerFundsProvider customerFundsProvider;

	@Autowired
	private CreditAccountProvider accountProvider;

	@Autowired
	private CouponCodeProvider couponCodeProvider;

	@Autowired
	private CouponCodeQueryProvider couponCodeQueryProvider;

	@Autowired
	private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

	@Autowired
	private CustomerQueryProvider customerQueryProvider;

	@Autowired
	private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

	@Autowired
	private PayingMemberCustomerRelProvider payingMemberCustomerRelProvider;

	@Autowired
	private EsCustomerDetailProvider esCustomerDetailProvider;

	private static final Integer PAGE_SIZE = 1000;

	/**
	 * 新增付费记录表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecord add(PayingMemberRecord entity) {
		payingMemberRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改付费记录表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecord modify(PayingMemberRecord entity) {
		payingMemberRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除付费记录表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberRecord entity) {
		payingMemberRecordRepository.save(entity);
	}

	/**
	 * 批量删除付费记录表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		payingMemberRecordRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询付费记录表
	 * @author zhanghao
	 */
	public PayingMemberRecord getOne(String id){
		return payingMemberRecordRepository.findByRecordIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "付费记录表不存在"));
	}

	/**
	 * 单个查询付费记录表
	 * @author zhanghao
	 */
	public PayingMemberRecord findById(String id){
		return payingMemberRecordRepository.findByRecordIdAndDelFlag(id, DeleteFlag.NO).orElse(null);
	}

	/**
	 * 分页查询付费记录表
	 * @author zhanghao
	 */
	public Page<PayingMemberRecord> page(PayingMemberRecordQueryRequest queryReq){
		return payingMemberRecordRepository.findAll(
				PayingMemberRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询付费记录表
	 * @author zhanghao
	 */
	public List<PayingMemberRecord> list(PayingMemberRecordQueryRequest queryReq){
		return payingMemberRecordRepository.findAll(PayingMemberRecordWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberRecordVO wrapperVo(PayingMemberRecord payingMemberRecord) {
		if (payingMemberRecord != null){
			PayingMemberRecordVO payingMemberRecordVO = KsBeanUtil.convert(payingMemberRecord, PayingMemberRecordVO.class);
			return payingMemberRecordVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberRecordQueryRequest queryReq) {
		return payingMemberRecordRepository.count(PayingMemberRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根据权益、会员开通时间查询记录
	 * @param request
	 * @return
	 */
	public Map<String, String> findByRights(PayingMemberRecordRightsRequest request) {
		if (RepeatType.WEEK == request.getRepeatType()) {
			List<PayingMemberRecord> records = payingMemberRecordRepository.findByRightsWeek(request.getRightsId(), request.getDate(), request.getPageable());
			return records.stream().collect(Collectors.toMap(PayingMemberRecord::getCustomerId, PayingMemberRecord::getRecordId));
		} else {
			LocalDate lastDayOfMonth = request.getDate().with(TemporalAdjusters.lastDayOfMonth());
			List<PayingMemberRecord> records = payingMemberRecordRepository.findByRightsRepeatMonth(request.getRightsId(), request.getDate(), lastDayOfMonth, request.getPageable());
			return records.stream().collect(Collectors.toMap(PayingMemberRecord::getCustomerId, PayingMemberRecord::getRecordId));
		}
	}

	/**
	 * 权益-券礼包，每月X号
	 */
	public void rightsCoupon() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime checkTime = DateUtil.getCheckTime(now, RepeatType.MONTH);
		// 查询需要发放优惠券的权益
		List<CustomerLevelRightsVO> rightsList = customerLevelRightsCouponAnalyseProvider.queryIssueCouponsData()
				.getContext().getCustomerLevelRightsVOList();
		rightsList.forEach(right -> {
			Integer pageNum = NumberUtils.INTEGER_ZERO;
			Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.unsorted());
			log.info("付费会员权益券礼包发放开始，发放类型：{}，发放权益id：{}", RightsCouponSendType.ISSUE_MONTHLY.getStateId(), right.getRightsId());
			while(true) {
				log.info("普通会员权益券礼包发放，发放类型：{}，权益id：{}，当前页码：{}，每页{}条",  RightsCouponSendType.ISSUE_MONTHLY.getStateId(), right.getRightsId(), pageNum, PAGE_SIZE);
				List<PayingMemberRecord> records = payingMemberRecordRepository.findByRightsIssueMonth(right.getRightsId(), now.toLocalDate(), pageable);
				Map<String, String> recordMap = records.stream().collect(Collectors.toMap(PayingMemberRecord::getCustomerId, PayingMemberRecord::getRecordId));
				if (recordMap.isEmpty()) {
					log.info("付费会员权益券礼包发放，发放类型：{}，发放权益id：{}，发放完成",  RightsCouponSendType.ISSUE_MONTHLY.getStateId(), right.getRightsId());
					break;
				}
				// 批量发放优惠券
				couponCustomerRightsProvider.customerRightsRepeatCoupons(RightsCouponRequest.builder()
						.customerType(MarketingCustomerType.PAYING_MEMBER)
						.checkTime(checkTime)
						.payingMemberInfo(recordMap)
						.activityId(right.getActivityId()).build());
				pageable = pageable.next();
			}
		});
	}

	/**
	 * 查询生效的记录
	 * @param customerId
	 * @param levelId
	 * @return
	 */
	public PayingMemberRecord findActiveRecord(String customerId, Integer levelId) {
		return payingMemberRecordRepository.findActiveRecord(customerId, levelId);
	}

	@Transactional
	public void updateRecordDiscount(String recordId, BigDecimal discount) {
		PayingMemberRecord record = findById(recordId);
		if (record != null) {
			BigDecimal amount = record.getAlreadyDiscountAmount() != null ? record.getAlreadyDiscountAmount() : BigDecimal.ZERO;
			record.setAlreadyDiscountAmount(amount.add(discount));
		}
	}

	/**
	 * 是否回收券
	 * @param recordId
	 * @return
	 */
	public Boolean isRecycleCoupon(String recordId) {
		PayingMemberRecord record = payingMemberRecordRepository.getOne(recordId);
		//已退款且回收了券
		if (Objects.isNull(record) ||
				PayingLevelState.REFUND.toValue() == record.getLevelState()
				&& NumberUtils.INTEGER_ZERO.equals(record.getReturnCoupon())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 根据会员id及会员状态查询记录
	 * @param customerId
	 * @param levelState
	 * @return
	 */
	public List<PayingMemberRecord> findAllByCustomerIdAndLevelState(String customerId, Integer levelState) {
		return payingMemberRecordRepository
				.findAllByCustomerIdAndLevelStateAndDelFlag(customerId, levelState, DeleteFlag.NO);
	}

	/**
	 * 定时任务自动更新状态
	 * @param recordIds
	 */
	@Transactional
	public void autoUpdateState(List<String> recordIds) {
		List<PayingMemberRecord> payingMemberRecordList = list(PayingMemberRecordQueryRequest.builder()
				.recordIdList(recordIds)
				.delFlag(DeleteFlag.NO)
				.build());
		payingMemberRecordList.parallelStream().forEach(payingMemberRecord -> {
			//找出该会员下所有的未生效的记录
			List<PayingMemberRecord> payingMemberRecords = findAllByCustomerIdAndLevelState(payingMemberRecord.getCustomerId(),Constants.ONE);
			if (CollectionUtils.isNotEmpty(payingMemberRecords)) {
				//找出支付时间最前的记录
				PayingMemberRecord record = payingMemberRecords.parallelStream().min(Comparator.comparing(PayingMemberRecord::getPayTime)).orElse(null);
				//更新为已生效
				payingMemberRecordRepository.updateExpirationDateAndLevelState(Constants.ZERO,LocalDate.now().plusMonths(record.getPayNum()),record.getRecordId());
			}
		});
		//更新状态为过期
		payingMemberRecordRepository.updateState(Constants.TWO,recordIds);
	}


	/**
	 * 付费会员退款
	 */
	@GlobalTransactional
	@Transactional
	public void refundPayingMember(PayingMemberRecordModifyRequest payingMemberRecordModifyRequest) {
		String recordId = payingMemberRecordModifyRequest.getRecordId();
		//支付宝需要returnId，这里将支付id的开头换掉，方便追溯
		String returnId = recordId.replace(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID,"");
		returnId = "PMR".concat(returnId);
		BigDecimal returnAmount = payingMemberRecordModifyRequest.getReturnAmount();

		//获取付费金额
		PayingMemberRecord payingMemberRecord = getOne(recordId);
		BigDecimal payAmount = payingMemberRecord.getPayAmount();
		String returnCause = payingMemberRecordModifyRequest.getReturnCause();
		Integer returnCoupon = payingMemberRecordModifyRequest.getReturnCoupon();
		Integer returnPoint = payingMemberRecordModifyRequest.getReturnPoint();
		//获取付费金额
		if (payAmount.compareTo(returnAmount) < 0) {
			throw new SbcRuntimeException(OrderErrorCodeEnum.K050042, new Object[]{payAmount});
		}
		//保存付费记录
		payingMemberRecordRepository.refundPayingMember(returnAmount, returnCause, returnCoupon, returnPoint,  LocalDate.now(),recordId);
		List<PayingMemberCustomerRelVO> payingMemberCustomerRelVOList = payingMemberCustomerRelQueryProvider.list(PayingMemberCustomerRelListRequest.builder()
				.customerId(payingMemberRecord.getCustomerId())
				.delFlag(DeleteFlag.NO)
				.build()).getContext().getPayingMemberCustomerRelVOList();
		PayingMemberCustomerRelVO payingMemberCustomerRelVO = payingMemberCustomerRelVOList.get(0);
		//退款的记录 未生效
		if (Constants.ONE == payingMemberRecord.getLevelState()) {
			payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
					.expirationDate(payingMemberCustomerRelVO.getExpirationDate().minusMonths(payingMemberRecord.getPayNum()))
					.id(payingMemberCustomerRelVO.getId())
					.build());
		} else {
			List<PayingMemberRecord> payingMemberRecords = findAllByCustomerIdAndLevelState(payingMemberRecord.getCustomerId(),NumberUtils.INTEGER_ONE);
			//该会员没有未生效的记录,则付费会员更新过期时间为昨天
			if (CollectionUtils.isEmpty(payingMemberRecords)) {
				payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
						.expirationDate(LocalDate.now().minusDays(1L))
						.id(payingMemberCustomerRelVO.getId())
						.build());
			} else {
				//找出支付时间最前的记录
				Optional<PayingMemberRecord> optional = payingMemberRecords.parallelStream().min(Comparator.comparing(PayingMemberRecord::getPayTime));
				Integer months = payingMemberRecords.parallelStream().map(PayingMemberRecord::getPayNum).reduce(0, Integer::sum);
				if (optional.isPresent()) {
						PayingMemberRecord record = optional.get();
						payingMemberRecordRepository.updateExpirationDateAndLevelState(Constants.ZERO,LocalDate.now().plusMonths(record.getPayNum()).minusDays(Constants.ONE),record.getRecordId());
						//总的会员过期时间重新算
						payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
								.expirationDate(LocalDate.now().plusMonths(months).minusDays(Constants.ONE))
								.id(payingMemberCustomerRelVO.getId())
								.build());
					}
				}
			}

		//根据付费记录id查询支付记录
		PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordService.findByBusinessId(recordId);
		Integer channelItemId = payingMemberPayRecord.getChannelItemId();
		PayChannelItemResponse response = paySettingQueryProvider.getChannelItemById(ChannelItemByIdRequest.builder()
				.channelItemId(channelItemId.longValue())
				.build()).getContext();
		if (response.getIsOpen().equals(IsOpen.NO))  {
			throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
		}
		String channel = response.getChannel().toUpperCase(Locale.ROOT);
		PayWay payWay = PayWay.valueOf(channel);
		switch (payWay)	{
			case ALIPAY:
				AliPayRefundRequest aliPayRefundRequest = AliPayRefundRequest.builder()
						.businessId(recordId)
						.amount(returnAmount)
						.description(returnCause)
						.build();
				PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new
						GatewayConfigByGatewayRequest(PayGatewayEnum.ALIPAY, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
				aliPayRefundRequest.setAppid(payGatewayConfig.getAppId());
				aliPayRefundRequest.setAppPrivateKey(payGatewayConfig.getPrivateKey());
				aliPayRefundRequest.setAliPayPublicKey(payGatewayConfig.getPublicKey());
				PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
				payRefundBaseRequest.setAliPayRefundRequest(aliPayRefundRequest);
				payRefundBaseRequest.setPayType(PayType.ALIPAY);
				//调用退款接口。直接退款。不走退款流程，没有交易对账，只记了操作日志
				payProvider.payRefund(payRefundBaseRequest);
				break;
			case WECHAT:
				WxPayRefundRequest refundInfoRequest = new WxPayRefundRequest();
				refundInfoRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
				refundInfoRequest.setOut_refund_no(returnId);
				refundInfoRequest.setOut_trade_no(recordId);
				refundInfoRequest.setTotal_fee(payingMemberRecord.getPayAmount().multiply(new BigDecimal(100)).
						setScale(0, RoundingMode.DOWN).toString());
				refundInfoRequest.setRefund_fee(returnAmount.multiply(new BigDecimal(100)).
						setScale(0, RoundingMode.DOWN).toString());
				TerminalType terminal = response.getTerminal();
				String tradeType = "APP";
				if (!terminal.equals(TerminalType.APP)) {
					tradeType = "PC/H5/JSAPI";
				}
				refundInfoRequest.setPay_type(tradeType);
				payRefundBaseRequest = new PayRefundBaseRequest();
				//不需要回调
				refundInfoRequest.setRefund_type("REPEATPAY");
				payRefundBaseRequest.setWxPayRefundRequest(refundInfoRequest);
				payRefundBaseRequest.setPayType(PayType.WXPAY);
				payProvider.payRefund(payRefundBaseRequest);
				break;
			case UNIONPAY:
				UnionCloudPayRefundRequest unionPayRequest = new UnionCloudPayRefundRequest();
				unionPayRequest.setBusinessId(recordId);
				unionPayRequest.setApplyPrice(returnAmount);
				unionPayRequest.setNeedCallback(false);
				payRefundBaseRequest = new PayRefundBaseRequest();
				payRefundBaseRequest.setUnionCloudPayRefundRequest(unionPayRequest);
				payRefundBaseRequest.setPayType(PayType.UNIONCLONDPAY);
				payProvider.payRefund(payRefundBaseRequest);
				break;
			case BALANCE:
				customerFundsProvider.addAmount(CustomerFundsAddAmountRequest.builder()
						.amount(returnAmount)
						.customerId(payingMemberRecord.getCustomerId())
						.businessId(recordId)
						.build());
				break;
			case CREDIT:
				accountProvider.restoreCreditAmount(CreditAmountRestoreRequest.builder()
						.amount(returnAmount)
						.customerId(payingMemberRecord.getCustomerId())
						.build());
				break;
			default:
				break;
		}
		//退优惠券
		if (returnCoupon.equals(NumberUtils.INTEGER_ZERO)) {
			//查询客户下所有的的有效的付费会员的优惠券
			List<CouponCodeDTO> couponCodeList = couponCodeQueryProvider.listCouponCodeByCondition(CouponCodeQueryRequest.builder()
					.customerId(payingMemberRecord.getCustomerId())
					.notExpire(Boolean.TRUE)
					.delFlag(DeleteFlag.NO)
					.useStatus(DefaultFlag.NO)
					.payingMemberRecordId(recordId)
					.build()).getContext().getCouponCodeList();
			if (CollectionUtils.isNotEmpty(couponCodeList)) {
				couponCodeList.parallelStream().forEach(couponCodeDTO ->
					//回收券
					couponCodeProvider.recycleCoupon(CouponCodeRecycleByIdRequest.builder()
							.couponCodeId(couponCodeDTO.getCouponCodeId())
							.customerId(payingMemberRecord.getCustomerId())
							.build())
				);
			}
		}
		// 退积分
		if (returnPoint.equals(NumberUtils.INTEGER_ZERO)) {
			Long alreadyReceivePoint = ObjectUtils.defaultIfNull(payingMemberRecord.getAlreadyReceivePoint(),BigDecimal.ZERO.longValue());
			//查询有用积分
			Long pointsAvailable = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder()
					.customerId(payingMemberRecord.getCustomerId())
					.build()).getContext().getPointsAvailable();
			pointsAvailable = ObjectUtils.defaultIfNull(pointsAvailable,BigDecimal.ZERO.longValue());
			//如果已领大于可用积分，则扣除全部可用积分
			if (pointsAvailable < alreadyReceivePoint) {
				alreadyReceivePoint = pointsAvailable;
			}
			if (alreadyReceivePoint > 0) {
				//回收积分
				customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
						.customerId(payingMemberRecord.getCustomerId())
						.type(OperateType.DEDUCT)
						.serviceType(PointsServiceType.RETURN_ORDER_BACK)
						.points(alreadyReceivePoint)
						.content(JSONObject.toJSONString(Collections.singletonMap("returnOrderNo", returnId)))
						.build());
			}
		}
		EsCustomerDetailInitRequest esCustomerDetailInitRequest=new EsCustomerDetailInitRequest();
		esCustomerDetailInitRequest.setIdList(Lists.newArrayList(payingMemberRecord.getCustomerId()));
		esCustomerDetailProvider.init(esCustomerDetailInitRequest);
	}
}

