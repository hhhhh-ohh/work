package com.wanmi.sbc.order.payingmemberrecordtemp.service;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelListRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelByIdResponse;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardGainRequest;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import com.wanmi.sbc.order.payingmemberrecord.repository.PayingMemberRecordRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.order.payingmemberrecordtemp.repository.PayingMemberRecordTempRepository;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempQueryRequest;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>付费记录临时表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Service("PayingMemberRecordTempService")
public class PayingMemberRecordTempService {
	@Autowired
	private PayingMemberRecordTempRepository payingMemberRecordTempRepository;

	@Autowired
	private GeneratorService generatorService;

	@Autowired
	private PayingMemberRecordRepository payingMemberRecordRepository;

	@Autowired
	private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

	@Autowired
	private PayingMemberCustomerRelProvider payingMemberCustomerRelProvider;

	@Autowired
	private CustomerLevelRightsQueryProvider customerLevelRightsQueryProvider;

	@Autowired
	private EsCustomerDetailProvider esCustomerDetailProvider;

	@Autowired
	private GiftCardBatchProvider giftCardBatchProvider;

	@Autowired
	private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

	@Autowired
	private CustomerQueryProvider customerQueryProvider;

	/**
	 * 新增付费记录临时表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecordTemp add(PayingMemberRecordTemp entity) {
		entity.setRecordId(generatorService.generatePayingMemberRecordId());
		entity.setCreateTime(LocalDateTime.now());
		entity.setPayState(NumberUtils.INTEGER_ZERO);
		payingMemberRecordTempRepository.save(entity);
		return entity;
	}

	/**
	 * 修改付费记录临时表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecordTemp modify(PayingMemberRecordTemp entity) {
		payingMemberRecordTempRepository.save(entity);
		return entity;
	}

	/**
	 * 付费会员回调业务
	 * @param recordId
	 * @return
	 */
	@Transactional
	@GlobalTransactional
	public PayingMemberRecord addForCallBack(String recordId) {
		PayingMemberRecordTemp payingMemberRecordTemp = getOne(recordId);
		payingMemberRecordTemp.setPayState(NumberUtils.INTEGER_ONE);
		payingMemberRecordTemp.setPayTime(LocalDateTime.now());

		PayingMemberRecord payingMemberRecord = KsBeanUtil.convert(payingMemberRecordTemp,PayingMemberRecord.class);
		payingMemberRecord.setCreateTime(LocalDateTime.now());
		List<PayingMemberRecord> payingMemberRecords = payingMemberRecordRepository
				.findAllByCustomerIdAndDelFlag(payingMemberRecordTemp.getCustomerId(), DeleteFlag.NO);
		List<PayingMemberCustomerRelVO> payingMemberCustomerRelVOList = payingMemberCustomerRelQueryProvider.list(PayingMemberCustomerRelListRequest.builder()
				.customerId(payingMemberRecordTemp.getCustomerId())
				.delFlag(DeleteFlag.NO)
				.build()).getContext().getPayingMemberCustomerRelVOList();
		// 首次购买
		if (CollectionUtils.isEmpty(payingMemberCustomerRelVOList) && CollectionUtils.isEmpty(payingMemberRecords)) {
			payingMemberRecord.setFirstOpen(NumberUtils.INTEGER_ZERO);
			payingMemberRecord.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
			payingMemberRecordTemp.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
			payingMemberRecord.setLevelState(NumberUtils.INTEGER_ZERO);
			payingMemberRecord.setAlreadyDiscountAmount(BigDecimal.ZERO);
			payingMemberCustomerRelProvider.add(PayingMemberCustomerRelAddRequest.builder()
					.levelId(payingMemberRecordTemp.getLevelId())
					.customerId(payingMemberRecordTemp.getCustomerId())
					.openTime(LocalDateTime.now())
					.expirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L))
					.discountAmount(BigDecimal.ZERO)
					.delFlag(DeleteFlag.NO)
					.build());
			//首次送校服提货卡 活动取消,暂时注释
			//sendSchoolUniformPickUpCardToAccount(payingMemberRecordTemp);
		} else {
			payingMemberRecord.setFirstOpen(NumberUtils.INTEGER_ONE);
			Optional<PayingMemberRecord> optional = payingMemberRecords.parallelStream().filter(record ->
					NumberUtils.INTEGER_ZERO.equals(record.getLevelState())
			).findFirst();
			PayingMemberCustomerRelVO payingMemberCustomerRelVO = payingMemberCustomerRelVOList.get(0);
			//是否有已经生效的会员记录
			if (optional.isPresent()) {
				PayingMemberRecord payingMemberRecord1 = optional.get();
				LocalDate expirationDate = payingMemberRecord1.getExpirationDate();
				//未过期
				if (expirationDate.isAfter(LocalDate.now())) {
					//续费 未生效 不记过期时间
					payingMemberRecord.setLevelState(NumberUtils.INTEGER_ONE);
					payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
							.expirationDate(payingMemberCustomerRelVO.getExpirationDate().plusMonths(payingMemberRecordTemp.getPayNum()))
							.id(payingMemberCustomerRelVO.getId())
							.build());
				} else { // 正常业务中不会走到这里。此段代码目的是防止定时任务中会员失效执行失败，做了业务中又做了一次判断
					//实际已经过期,更新状态
					payingMemberRecordRepository.updateState(Constants.TWO, Lists.newArrayList(payingMemberRecord1.getRecordId()));
					//找出未生效，并且支付时间最前的记录
					optional = payingMemberRecords.parallelStream().filter(record ->
							NumberUtils.INTEGER_ONE.equals(record.getLevelState())).min(Comparator.comparing(PayingMemberRecord::getPayTime));
					if (optional.isPresent()) {
						PayingMemberRecord payingMemberRecord2 = optional.get();
						//设置为已生效
						payingMemberRecordRepository.updateExpirationDateAndLevelState(Constants.ZERO,LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L),payingMemberRecord2.getRecordId());
						//续费 未生效 不记过期时间
						payingMemberRecord.setLevelState(NumberUtils.INTEGER_ONE);
						payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
								.expirationDate(payingMemberCustomerRelVO.getExpirationDate().plusMonths(payingMemberRecordTemp.getPayNum()))
								.id(payingMemberCustomerRelVO.getId())
								.build());
					} else {
						// 之前购买过，但过期了。。
						// 今天买的立即生效
						payingMemberRecord.setLevelState(NumberUtils.INTEGER_ZERO);
						payingMemberRecord.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
						payingMemberRecordTemp.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
						payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
								.expirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L))
								.id(payingMemberCustomerRelVO.getId())
								.openTime(LocalDateTime.now())
								.build());
					}
				}
			} else {
				// 之前购买过，但过期了。。
				// 今天买的立即生效
				payingMemberRecord.setLevelState(NumberUtils.INTEGER_ZERO);
				payingMemberRecord.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
				payingMemberRecordTemp.setExpirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L));
				payingMemberCustomerRelProvider.updateExpirationDate(PayingMemberCustomerRelModifyRequest.builder()
						.expirationDate(LocalDate.now().plusMonths(payingMemberRecordTemp.getPayNum()).minusDays(1L))
						.id(payingMemberCustomerRelVO.getId())
						.openTime(LocalDateTime.now())
						.build());
			}
		}
		//获取权益，发放权益
		issueLevelRightsForPayingMember(payingMemberRecord);
		payingMemberRecordTempRepository.save(payingMemberRecordTemp);
		payingMemberRecordRepository.save(payingMemberRecord);

		EsCustomerDetailInitRequest esCustomerDetailInitRequest=new EsCustomerDetailInitRequest();
		esCustomerDetailInitRequest.setIdList(Lists.newArrayList(payingMemberRecord.getCustomerId()));
		esCustomerDetailProvider.init(esCustomerDetailInitRequest);
		return payingMemberRecord;
	}

	private void issueLevelRightsForPayingMember(PayingMemberRecord payingMemberRecord) {
		//获取权益，发放权益
		List<Integer> rightsIds = Arrays.stream(payingMemberRecord.getRightsIds().split(","))
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		Optional<CustomerLevelRightsVO> pointRights = customerLevelRightsQueryProvider.issueLevelRightsForPayingMember(CustomerLevelRightsQueryRequest.builder()
						.rightsIdList(rightsIds)
						.recordId(payingMemberRecord.getRecordId())
						.levelState(payingMemberRecord.getLevelState())
						.delFlag(DeleteFlag.NO)
						.customerId(payingMemberRecord.getCustomerId())
						.build()).getContext().getCustomerLevelRightsVOList()
				.parallelStream().filter(customerLevelRightsVO ->
						customerLevelRightsVO.getRightsType() == LevelRightsType.SEND_POINTS
				).findFirst();
		//更新已发放积分信息
		if (pointRights.isPresent()) {
			Long points = Long.valueOf(JSON.parseObject(pointRights.get().getRightsRule()).get("points").toString());
			payingMemberRecord.setAlreadyReceivePoint(points);
		}
	}

	/**
	 * 单个删除付费记录临时表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberRecordTemp entity) {
		payingMemberRecordTempRepository.save(entity);
	}

	/**
	 * 批量删除付费记录临时表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		payingMemberRecordTempRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询付费记录临时表
	 * @author zhanghao
	 */
	public PayingMemberRecordTemp getOne(String id){
		return payingMemberRecordTempRepository.findByRecordIdAndDelFlag(id, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "付费记录临时表不存在"));
	}

	/**
	 * 分页查询付费记录临时表
	 * @author zhanghao
	 */
	public Page<PayingMemberRecordTemp> page(PayingMemberRecordTempQueryRequest queryReq){
		return payingMemberRecordTempRepository.findAll(
				PayingMemberRecordTempWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询付费记录临时表
	 * @author zhanghao
	 */
	public List<PayingMemberRecordTemp> list(PayingMemberRecordTempQueryRequest queryReq){
		return payingMemberRecordTempRepository.findAll(PayingMemberRecordTempWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberRecordTempVO wrapperVo(PayingMemberRecordTemp payingMemberRecordTemp) {
		if (payingMemberRecordTemp != null){
			PayingMemberRecordTempVO payingMemberRecordTempVO = KsBeanUtil.convert(payingMemberRecordTemp, PayingMemberRecordTempVO.class);
			return payingMemberRecordTempVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberRecordTempQueryRequest queryReq) {
		return payingMemberRecordTempRepository.count(PayingMemberRecordTempWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * @description 送对应等级的提货卡
	 * @author lfx
	 */
	private void sendSchoolUniformPickUpCardToAccount(PayingMemberRecordTemp payingMemberRecordTemp) {
		PayingMemberLevelByIdRequest payingMemberLevelByIdRequest = PayingMemberLevelByIdRequest.builder()
				.levelId(payingMemberRecordTemp.getLevelId())
				.isCustomer(true)
				.build();

		PayingMemberLevelByIdResponse level = payingMemberLevelQueryProvider.getById(payingMemberLevelByIdRequest).getContext();
		Integer giftCardType = 1;
		if ("V1".equals(level.getPayingMemberLevelVO().getLevelName())) {
			giftCardType = 1;
		} else if ("V2".equals(level.getPayingMemberLevelVO().getLevelName())) {
			giftCardType = 2;
		} else if ("V3".equals(level.getPayingMemberLevelVO().getLevelName())) {
			giftCardType = 3;
		}
		CustomerGetByIdRequest customerGetByIdRequest = CustomerGetByIdRequest.builder()
				.customerId(payingMemberRecordTemp.getCustomerId())
				.build();
		CustomerGetByIdResponse context = customerQueryProvider.getCustomerById(customerGetByIdRequest).getContext();
		//首次购买会员赠送一张提货卡
		GiftCardGainRequest giftCardGainRequest = GiftCardGainRequest.builder()
				.giftCardType(giftCardType)
				.customerAccount(context.getCustomerAccount())
				.build();
		giftCardBatchProvider.sendPickUpCardToAccount(giftCardGainRequest);
	}
}

