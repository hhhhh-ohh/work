package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListForOrderRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerListByConditionRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerDetailListForOrderResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerListByConditionResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailJoinVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailPageVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardDetailJoinWhereCriteriaBuilder;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardDetailService;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>礼品卡详情查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@RestController
@Validated
public class GiftCardDetailQueryController implements GiftCardDetailQueryProvider {

	@Autowired private GiftCardDetailService giftCardDetailService;

	@Autowired private CustomerQueryProvider customerQueryProvider;

	@Autowired private EmployeeQueryProvider employeeQueryProvider;

	@Override
	public BaseResponse<GiftCardDetailPageResponse> page(@RequestBody @Valid GiftCardDetailPageRequest giftCardDetailPageReq) {
		GiftCardDetailQueryRequest queryReq = KsBeanUtil.convert(giftCardDetailPageReq, GiftCardDetailQueryRequest.class);
		// 封装入参
		this.wrapperRequest(queryReq);
		Page<GiftCardDetail> giftCardDetailPage = giftCardDetailService.page(queryReq);
		Page<GiftCardDetailVO> newPage = giftCardDetailPage.map(entity -> giftCardDetailService.wrapperVo(entity));
		MicroServicePage<GiftCardDetailVO> microPage = new MicroServicePage<>(newPage, giftCardDetailPageReq.getPageable());
		// 封装出参
		this.wrapperResponse(microPage.getContent());
		return BaseResponse.success(new GiftCardDetailPageResponse(microPage));
	}

	@Override
	public BaseResponse<GiftCardDetailListResponse> list(@RequestBody @Valid GiftCardDetailListRequest giftCardDetailListReq) {
		GiftCardDetailQueryRequest queryReq = KsBeanUtil.convert(giftCardDetailListReq, GiftCardDetailQueryRequest.class);
		List<GiftCardDetail> giftCardDetailList = giftCardDetailService.list(queryReq);
		List<GiftCardDetailVO> newList = giftCardDetailList.stream().map(entity -> giftCardDetailService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GiftCardDetailListResponse(newList));
	}

	@Override
	public BaseResponse<GiftCardDetailByIdResponse> getById(@RequestBody @Valid GiftCardDetailByIdRequest giftCardDetailByIdRequest) {
		GiftCardDetail giftCardDetail =
		giftCardDetailService.getOne(giftCardDetailByIdRequest.getGiftCardNo());
		return BaseResponse.success(new GiftCardDetailByIdResponse(giftCardDetailService.wrapperVo(giftCardDetail)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid GiftCardDetailExportRequest request) {
		GiftCardDetailQueryRequest queryReq = KsBeanUtil.convert(request, GiftCardDetailQueryRequest.class);
		// 封装入参
		this.wrapperRequest(queryReq);
		Long total = giftCardDetailService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<GiftCardDetailExportResponse> exportGiftCardDetailRecord(@RequestBody @Valid GiftCardDetailExportRequest request) {
		GiftCardDetailQueryRequest queryReq = KsBeanUtil.convert(request, GiftCardDetailQueryRequest.class);
		List<GiftCardDetailPageVO> data = KsBeanUtil.convert(giftCardDetailService.page(queryReq).getContent(), GiftCardDetailPageVO.class);
		return BaseResponse.success(new GiftCardDetailExportResponse(data));
	}

	@Override
	public BaseResponse<GiftCardDetailJoinPageResponse> getGiftCardDetailPage(GiftCardDetailJoinPageRequest request) {
		GiftCardDetailJoinWhereCriteriaBuilder query = KsBeanUtil.convert(request, GiftCardDetailJoinWhereCriteriaBuilder.class);
		Page<GiftCardDetailJoinVO> pageInfo = giftCardDetailService.getGiftCardDetailPage(query);
		// 组装数据
		this.buildResponse(pageInfo.getContent());
		GiftCardDetailJoinPageResponse response = GiftCardDetailJoinPageResponse.builder()
				.GiftCardDetailJoinVOPage(new MicroServicePage<>(pageInfo.getContent(), request.getPageable(), pageInfo.getTotalElements()))
				.build();

		return BaseResponse.success(response);
	}


	/**
	 * 查询单卡明细，组装数据
	 * @param content
	 */
	private void buildResponse(List<GiftCardDetailJoinVO> content) {
		// 填充制/发卡人名称和账号
		if (CollectionUtils.isNotEmpty(content)) {
			CustomerDetailListForOrderRequest customerDetailListForOrderRequest = new CustomerDetailListForOrderRequest();
			Set<String> customerIds = new HashSet<>();
			Set<String> employeeIds = new HashSet<>();
			content.forEach(item -> {
				customerIds.add(item.getBelongPerson());
				customerIds.add(item.getActivationPerson());
				employeeIds.add(item.getGeneratePerson());
			});
			customerDetailListForOrderRequest.setCustomerIds(new ArrayList<>(customerIds));
			Map<String, CustomerDetailVO> customerMap =
					Optional.ofNullable(customerQueryProvider.listCustomerDetailForOrder(customerDetailListForOrderRequest).getContext())
							.map(CustomerDetailListForOrderResponse::getDetailResponseList)
							.orElse(Collections.emptyList())
							.stream()
							.collect(Collectors.toMap(CustomerDetailVO::getCustomerId, Function.identity()));
			Map<String, EmployeeListByIdsVO> employeeListByIdsVOHashMap = Optional.ofNullable(employeeQueryProvider.listByIds(EmployeeListByIdsRequest.builder().employeeIds(new ArrayList<>(employeeIds)).build()).getContext())
					.map(EmployeeListByIdsResponse::getEmployeeList)
					.orElse(Collections.emptyList())
					.stream()
					.collect(Collectors.toMap(EmployeeListByIdsVO::getEmployeeId, Function.identity()));
			content.forEach(item -> {
				// 填充归属会员名称和账号
				CustomerDetailVO belongCustomer = customerMap.get(item.getBelongPerson());
				if(Objects.nonNull(belongCustomer)){
					CustomerVO customerVO = belongCustomer.getCustomerVO();
					item.setBelongPersonAccount(customerVO.getCustomerAccount());
					item.setBelongPersonName(belongCustomer.getCustomerName());
				}
				// 填充激活会员名称和账号
				CustomerDetailVO activationCustomer = customerMap.get(item.getActivationPerson());
				if(Objects.nonNull(activationCustomer)){
					CustomerVO activationCustomerVO = activationCustomer.getCustomerVO();
					item.setActivationPersonAccount(activationCustomerVO.getCustomerAccount());
					item.setActivationPersonName(activationCustomer.getCustomerName());
				}
				EmployeeListByIdsVO employeeListByIdsVO = employeeListByIdsVOHashMap.get(item.getGeneratePerson());
				if(Objects.nonNull(employeeListByIdsVO)){
					item.setGeneratePersonName(employeeListByIdsVO.getEmployeeName());
				}
				if(Objects.nonNull(item.getExpirationStartTime()) && Objects.nonNull(item.getExpirationTime())){
					item.setExpirationType(ExpirationType.SPECIFIC_TIME.toValue());
				}
				// 填充状态
				//已过期
				boolean expired = false;
				GiftCardDetailStatus detailStatus = GiftCardDetailStatus.fromValue(item.getCardDetailStatus());
				LocalDateTime expirationTime = item.getExpirationTime();
				if (Objects.nonNull(expirationTime) && LocalDateTime.now().isAfter(expirationTime)) {
					// now > 过期时间，即为过期
					expired = true;
				}
				if (expired && GiftCardDetailStatus.CANCELED != detailStatus) {
					// 卡已过期，非销卡，状态：已过期
					item.setCardDetailStatus(GiftCardDetailStatus.EXPIRED.toValue());
				}
			});
		}
	}

	/**
	 * @description 入参封装
	 * @author malianfeng
	 * @date 2022/12/20 10:56
	 * @param queryReq 入参
	 * @return void
	 */
	private void wrapperRequest(GiftCardDetailQueryRequest queryReq) {
		GiftCardDetailStatus detailStatus = queryReq.getCardDetailStatus();
		if (Objects.nonNull(detailStatus)) {
			if (GiftCardDetailStatus.EXPIRED == detailStatus) {
				// 状态：已过期，条件：now >= 过期时间 && not in (已销卡)
				queryReq.setExpiredFlag(Boolean.TRUE);
				queryReq.setCardDetailStatus(null);
				queryReq.setNotCardDetailStatusList(Collections.singletonList(GiftCardDetailStatus.CANCELED));
			} else {
				if (GiftCardDetailStatus.CANCELED != detailStatus) {
					// 状态：非已过期、非已销卡，条件 过期时间 is null || now < 过期时间
					queryReq.setExpiredFlag(Boolean.FALSE);
				}
			}
		}
	}


	/**
	 * @description 出参封装
	 * @author malianfeng
	 * @date 2022/12/20 10:57
	 * @param content 出参
	 * @return void
	 */
	private void wrapperResponse(List<GiftCardDetailVO> content) {
		// 填充制/发卡人名称和账号
		if (CollectionUtils.isNotEmpty(content)) {
			CustomerListByConditionRequest customerListByConditionRequest = new CustomerListByConditionRequest();
			Set<String> customerIds = new HashSet<>();
			content.forEach(item -> {
				if (Objects.nonNull(item.getBelongPerson())){
					customerIds.add(item.getBelongPerson());
				}
				if (Objects.nonNull(item.getActivationPerson())){
					customerIds.add(item.getActivationPerson());
				}
			});
			Map<String, CustomerVO> customerMap = new HashMap<>();
			if (CollectionUtils.isNotEmpty(customerIds)){
				customerListByConditionRequest.setCustomerIds(new ArrayList<>(customerIds));
				customerMap =
						Optional.ofNullable(customerQueryProvider.listCustomerByCondition(customerListByConditionRequest).getContext())
								.map(CustomerListByConditionResponse::getCustomerVOList)
								.orElse(Collections.emptyList())
								.stream()
								.collect(Collectors.toMap(CustomerVO::getCustomerId, Function.identity()));
			}
			for (GiftCardDetailVO item : content) {
				// 填充归属会员名称和账号
				CustomerVO belongCustomer = customerMap.get(item.getBelongPerson());
				if (Objects.nonNull(belongCustomer)) {
					item.setBelongPersonAccount(belongCustomer.getCustomerAccount());
					if (Objects.nonNull(belongCustomer.getCustomerDetail())) {
						item.setBelongPersonName(belongCustomer.getCustomerDetail().getCustomerName());
					}
				}
				// 填充激活会员名称和账号
				CustomerVO activationCustomer = customerMap.get(item.getActivationPerson());
				if (Objects.nonNull(activationCustomer)) {
					item.setActivationPersonAccount(activationCustomer.getCustomerAccount());
					if (Objects.nonNull(activationCustomer.getCustomerDetail())) {
						item.setActivationPersonName(activationCustomer.getCustomerDetail().getCustomerName());
					}
				}
				// 填充状态
				boolean expired = false;
				GiftCardDetailStatus detailStatus = item.getCardDetailStatus();
				LocalDateTime expirationTime = item.getExpirationTime();
				if (Objects.nonNull(expirationTime) && LocalDateTime.now().isAfter(expirationTime)) {
					// now > 过期时间，即为过期
					expired = true;
				}
				if (expired && GiftCardDetailStatus.CANCELED != detailStatus) {
					// 卡已过期，非销卡，状态：已过期
					item.setCardDetailStatus(GiftCardDetailStatus.EXPIRED);
				}
			}
		}
	}
}

