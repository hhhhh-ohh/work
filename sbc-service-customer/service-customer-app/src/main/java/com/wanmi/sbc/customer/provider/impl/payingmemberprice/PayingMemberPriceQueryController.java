package com.wanmi.sbc.customer.provider.impl.payingmemberprice;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.enums.RightsCouponSendType;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import com.wanmi.sbc.customer.level.model.root.CustomerLevel;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRights;
import com.wanmi.sbc.customer.levelrights.service.CustomerLevelRightsService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import com.wanmi.sbc.customer.payingmemberrightsrel.service.PayingMemberRightsRelService;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberprice.PayingMemberPriceQueryProvider;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceExportRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceListRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPricePageRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceByIdResponse;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceExportResponse;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceListResponse;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPricePageResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberPricePageVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberPriceVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRights;
import com.wanmi.sbc.customer.levelrights.service.CustomerLevelRightsService;
import com.wanmi.sbc.customer.payingmemberprice.model.root.PayingMemberPrice;
import com.wanmi.sbc.customer.payingmemberprice.service.PayingMemberPriceService;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import com.wanmi.sbc.customer.payingmemberrightsrel.service.PayingMemberRightsRelService;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>付费设置表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@RestController
@Validated
public class PayingMemberPriceQueryController implements PayingMemberPriceQueryProvider {
	@Autowired
	private PayingMemberPriceService payingMemberPriceService;
	@Autowired
	private PayingMemberRightsRelService payingMemberRightsRelService;
	@Autowired
	private CustomerLevelRightsService customerLevelRightsService;

	@Override
	public BaseResponse<PayingMemberPricePageResponse> page(@RequestBody @Valid PayingMemberPricePageRequest payingMemberPricePageReq) {
		PayingMemberPriceQueryRequest queryReq = KsBeanUtil.convert(payingMemberPricePageReq, PayingMemberPriceQueryRequest.class);
		Page<PayingMemberPrice> payingMemberPricePage = payingMemberPriceService.page(queryReq);
		Page<PayingMemberPriceVO> newPage = payingMemberPricePage.map(entity -> payingMemberPriceService.wrapperVo(entity));
		MicroServicePage<PayingMemberPriceVO> microPage = new MicroServicePage<>(newPage, payingMemberPricePageReq.getPageable());
		PayingMemberPricePageResponse finalRes = new PayingMemberPricePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberPriceListResponse> list(@RequestBody @Valid PayingMemberPriceListRequest payingMemberPriceListReq) {
		PayingMemberPriceQueryRequest queryReq = KsBeanUtil.convert(payingMemberPriceListReq, PayingMemberPriceQueryRequest.class);
		return BaseResponse.success(new PayingMemberPriceListResponse(payingMemberPriceService.list(queryReq)));
	}

	@Override
	public BaseResponse<PayingMemberPriceByIdResponse> getById(@RequestBody @Valid PayingMemberPriceByIdRequest payingMemberPriceByIdRequest) {
		PayingMemberPrice payingMemberPrice =
		payingMemberPriceService.getOne(payingMemberPriceByIdRequest.getPriceId());
		List<PayingMemberRightsRel> payingMemberRightsRelList = payingMemberRightsRelService.list(PayingMemberRightsRelQueryRequest.builder()
				.priceId(payingMemberPrice.getPriceId())
				.delFlag(DeleteFlag.NO)
				.build());
		List<Integer> rightsIdList = payingMemberRightsRelList.parallelStream().map(PayingMemberRightsRel::getRightsId)
				.collect(Collectors.toList());
		List<CustomerLevelRights> customerLevelRightsList = customerLevelRightsService.list(CustomerLevelRightsQueryRequest.builder()
				.rightsIdList(rightsIdList)
				.delFlag(DeleteFlag.NO)
				.build());
		PayingMemberPriceVO payingMemberPriceVO = payingMemberPriceService.wrapperVo(payingMemberPrice);
		payingMemberPriceVO.setPayingMemberRightsRelVOS(KsBeanUtil.convert(payingMemberRightsRelList, PayingMemberRightsRelVO.class));
		payingMemberPriceVO.setCustomerLevelRightsVOS(KsBeanUtil.convert(customerLevelRightsList, CustomerLevelRightsVO.class));
		return BaseResponse.success(new PayingMemberPriceByIdResponse(payingMemberPriceVO));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberPriceExportRequest request) {
		PayingMemberPriceQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberPriceQueryRequest.class);
		Long total = payingMemberPriceService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberPriceExportResponse> exportPayingMemberPriceRecord(@RequestBody @Valid PayingMemberPriceExportRequest request) {
		PayingMemberPriceQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberPriceQueryRequest.class);
		List<PayingMemberPricePageVO> data = KsBeanUtil.convert(payingMemberPriceService.page(queryReq).getContent(),PayingMemberPricePageVO.class);
		return BaseResponse.success(new PayingMemberPriceExportResponse(data));
	}
}

