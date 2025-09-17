package com.wanmi.sbc.customer.provider.impl.payingmembercustomerrel;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.*;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.*;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelPageVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import com.wanmi.sbc.customer.payingmembercustomerrel.service.PayingMemberCustomerRelService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>客户与付费会员等级关联表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@RestController
@Validated
public class PayingMemberCustomerRelQueryController implements PayingMemberCustomerRelQueryProvider {
	@Autowired
	private PayingMemberCustomerRelService payingMemberCustomerRelService;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;

	@Override
	public BaseResponse<PayingMemberCustomerRelPageResponse> page(@RequestBody @Valid PayingMemberCustomerRelPageRequest payingMemberCustomerRelPageReq) {
		PayingMemberCustomerRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberCustomerRelPageReq, PayingMemberCustomerRelQueryRequest.class);
		Page<PayingMemberCustomerRel> payingMemberCustomerRelPage = payingMemberCustomerRelService.page(queryReq);
		Page<PayingMemberCustomerRelVO> newPage = payingMemberCustomerRelPage.map(entity -> payingMemberCustomerRelService.wrapperVo(entity));
		MicroServicePage<PayingMemberCustomerRelVO> microPage = new MicroServicePage<>(newPage, payingMemberCustomerRelPageReq.getPageable());
		PayingMemberCustomerRelPageResponse finalRes = new PayingMemberCustomerRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelListResponse> list(@RequestBody @Valid PayingMemberCustomerRelListRequest payingMemberCustomerRelListReq) {
		PayingMemberCustomerRelQueryRequest queryReq = KsBeanUtil.convert(payingMemberCustomerRelListReq, PayingMemberCustomerRelQueryRequest.class);
		List<PayingMemberCustomerRel> payingMemberCustomerRelList = payingMemberCustomerRelService.list(queryReq);
		List<PayingMemberCustomerRelVO> newList = payingMemberCustomerRelList.stream().map(entity -> payingMemberCustomerRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberCustomerRelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelByIdResponse> getById(@RequestBody @Valid PayingMemberCustomerRelByIdRequest payingMemberCustomerRelByIdRequest) {
		PayingMemberCustomerRel payingMemberCustomerRel =
		payingMemberCustomerRelService.getOne(payingMemberCustomerRelByIdRequest.getId());
		return BaseResponse.success(new PayingMemberCustomerRelByIdResponse(payingMemberCustomerRelService.wrapperVo(payingMemberCustomerRel)));
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelByIdResponse> findByCustomerId(@RequestBody @Valid PayingMemberCustomerRelQueryRequest payingMemberCustomerRelByIdRequest) {
		PayingMemberCustomerRel payingMemberCustomerRel =
				payingMemberCustomerRelService.findByCustomerId(payingMemberCustomerRelByIdRequest.getCustomerId());
		return BaseResponse.success(new PayingMemberCustomerRelByIdResponse(payingMemberCustomerRelService.wrapperVo(payingMemberCustomerRel)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberCustomerRelExportRequest request) {
		PayingMemberCustomerRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberCustomerRelQueryRequest.class);
		Long total = payingMemberCustomerRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelExportResponse> exportPayingMemberCustomerRelRecord(@RequestBody @Valid PayingMemberCustomerRelExportRequest request) {
		PayingMemberCustomerRelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberCustomerRelQueryRequest.class);
		List<PayingMemberCustomerRelPageVO> data = KsBeanUtil.convert(payingMemberCustomerRelService.page(queryReq).getContent(),PayingMemberCustomerRelPageVO.class);
		return BaseResponse.success(new PayingMemberCustomerRelExportResponse(data));
	}

	@Override
	public BaseResponse<PayingMemberCustomerResponse> getPayingMemberCustomerSetting(PayingMemberCustomerByCustomerIdRequest payingMemberCustomerByCustomerIdRequest) {
		// 查询付费会员配置信息
		ConfigQueryRequest request = new ConfigQueryRequest();
		request.setConfigType(ConfigType.PAYING_MEMBER.toString());
		request.setDelFlag(DeleteFlag.NO.toValue());
		ConfigVO config = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
		PayingMemberCustomerResponse response = JSONObject.parseObject(config.getContext(), PayingMemberCustomerResponse.class);
		// 付费会员之前是否开启过
		if (!response.getOpenFlag()) {
			return BaseResponse.success(null);
		}
		// 查询当前会员未过期的付费会员等级信息，未查到数据可能是未开通过或会员已过期
		List<PayingMemberCustomerRel> payingMemberCustomerRels = payingMemberCustomerRelService.list(
				PayingMemberCustomerRelQueryRequest.builder()
						.customerId(payingMemberCustomerByCustomerIdRequest.getCustomerId())
						.expirationDateBegin(LocalDate.now())
						.build());
		// 有数据说明会员已开通且有效
		if (CollectionUtils.isNotEmpty(payingMemberCustomerRels)) {
			response.setHasOpen(Boolean.TRUE);
		}
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<PayingMemberQueryDiscountResponse> findDiscountByLevelId(@RequestBody @Valid PayingMemberQueryDiscountRequest payingMemberQueryDiscountRequest) {
		BigDecimal totalDiscount = payingMemberCustomerRelService.findDiscountByLevelId(payingMemberQueryDiscountRequest.getCustomerId(), payingMemberQueryDiscountRequest.getLevelId());
		return BaseResponse.success(new PayingMemberQueryDiscountResponse(totalDiscount));
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelByIdResponse> findMostByCustomerId(@RequestBody @Valid PayingMemberCustomerRelQueryRequest payingMemberCustomerRelByIdRequest) {
		//查询付费会员等级最大等级的Id
		Integer payingMemberMostLevelId=
				payingMemberCustomerRelService.findMostLevelId(payingMemberCustomerRelByIdRequest.getCustomerId());

		if (payingMemberMostLevelId == null) {
			return BaseResponse.success(null);
		}
		//查询付费会员等级最大等级的会员到期日
		LocalDate expirationDate =
				payingMemberCustomerRelService.findMostExpirationDate(payingMemberCustomerRelByIdRequest.getCustomerId(),
						payingMemberMostLevelId);
		//查询付费会员等级最大等级的名称
		String levelName=
				payingMemberCustomerRelService.findMostLevelName(
						payingMemberMostLevelId);

		PayingMemberCustomerRelVO payingMemberCustomerRelVO = new PayingMemberCustomerRelVO();
		payingMemberCustomerRelVO.setLevelName(levelName);
		payingMemberCustomerRelVO.setExpirationDate(expirationDate);


		return BaseResponse.success(new PayingMemberCustomerRelByIdResponse(payingMemberCustomerRelVO));
	}

	@Override
	public BaseResponse<PayingMemberCustomerIdByWeekResponse> getActiveCustomerIdByWeek(@RequestBody @Valid PayingMemberCustomerIdByWeekRequest request) {
		List<String> customerIds = payingMemberCustomerRelService.findActiveCustomerIdByWeek(request.getCustomerIds(), request.getDate());
		return BaseResponse.success(new PayingMemberCustomerIdByWeekResponse(customerIds));
	}

	@Override
	public BaseResponse<PayingMemberCheckResponse> checkPayMember(@RequestBody @Valid PayingMemberCheckRequest request) {
		Boolean payMemberFlag = payingMemberCustomerRelService.checkPayMember(request.getCustomerId());
		return BaseResponse.success(new PayingMemberCheckResponse(payMemberFlag));
	}

    @Override
    public BaseResponse<PayingMemberCustomerRelExpiraResponse> findExpiration() {
		List<PayingMemberCustomerRel> payingMemberCustomerRelList = payingMemberCustomerRelService.findExpirationList();
		List<PayingMemberCustomerRelVO> collect = payingMemberCustomerRelList.stream().map(payingMemberCustomerRel -> {
			PayingMemberCustomerRelVO payMemberCustomerRelVO = new PayingMemberCustomerRelVO();
			payMemberCustomerRelVO.setCustomerId(payingMemberCustomerRel.getCustomerId());
			return payMemberCustomerRelVO;
		}).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberCustomerRelExpiraResponse(collect));
    }

}

