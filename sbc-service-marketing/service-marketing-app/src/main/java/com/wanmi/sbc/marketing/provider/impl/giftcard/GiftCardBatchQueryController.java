package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListResponse;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchByIdResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchExportResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchListResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchPageResponse;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchPageVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBatch;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardBatchService;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>礼品卡批次查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@RestController
@Validated
public class GiftCardBatchQueryController implements GiftCardBatchQueryProvider {

	@Autowired private GiftCardBatchService giftCardBatchService;

	@Autowired private GiftCardDetailService giftCardDetailService;

	@Autowired private EmployeeQueryProvider employeeQueryProvider;

	@Override
	public BaseResponse<GiftCardBatchPageResponse> page(@RequestBody @Valid GiftCardBatchPageRequest giftCardBatchPageReq) {
		// 礼品卡卡号非空，查询卡详情，限定批次编号
		if (StringUtils.isNotBlank(giftCardBatchPageReq.getGiftCardNo())) {
			GiftCardDetail giftCardDetailByNo = giftCardDetailService.findByGiftCardNo(giftCardBatchPageReq.getGiftCardNo());
			if (Objects.nonNull(giftCardDetailByNo)) {
				giftCardBatchPageReq.setBatchNo(giftCardDetailByNo.getBatchNo());
			} else {
				return BaseResponse.success(new GiftCardBatchPageResponse(new MicroServicePage<>()));
			}
		}
		GiftCardBatchQueryRequest queryReq = KsBeanUtil.convert(giftCardBatchPageReq, GiftCardBatchQueryRequest.class);
		Page<GiftCardBatch> giftCardBatchPage = giftCardBatchService.page(queryReq);
		Page<GiftCardBatchVO> newPage = giftCardBatchPage.map(entity -> giftCardBatchService.wrapperVo(entity));
		MicroServicePage<GiftCardBatchVO> microPage = new MicroServicePage<>(newPage, giftCardBatchPageReq.getPageable());
		List<GiftCardBatchVO> content = microPage.getContent();
		// 填充制/发卡人名称和账号
		if (CollectionUtils.isNotEmpty(content)) {
			EmployeeListRequest employeeListRequest = new EmployeeListRequest();
			List<String> employeeIds = content.stream().map(GiftCardBatchVO::getGeneratePerson).distinct().collect(Collectors.toList());
			employeeListRequest.setEmployeeIds(employeeIds);
			Map<String, EmployeeListVO> employeeMap =
					Optional.ofNullable(employeeQueryProvider.list(employeeListRequest).getContext())
					.map(EmployeeListResponse::getEmployeeList)
					.orElse(Collections.emptyList())
					.stream()
					.collect(Collectors.toMap(EmployeeListVO::getEmployeeId, Function.identity()));
			content.forEach(item -> {
				EmployeeListVO employeeVO = employeeMap.get(item.getGeneratePerson());
				if (Objects.nonNull(employeeVO)) {
					item.setGeneratePersonName(employeeVO.getEmployeeName());
					item.setGeneratePersonAccount(employeeVO.getAccountName());
				}
			});
		}
		return BaseResponse.success(new GiftCardBatchPageResponse(microPage));
	}

	@Override
	public BaseResponse<GiftCardBatchListResponse> list(@RequestBody @Valid GiftCardBatchListRequest giftCardBatchListReq) {
		GiftCardBatchQueryRequest queryReq = KsBeanUtil.convert(giftCardBatchListReq, GiftCardBatchQueryRequest.class);
		List<GiftCardBatch> giftCardBatchList = giftCardBatchService.list(queryReq);
		List<GiftCardBatchVO> newList = giftCardBatchList.stream().map(entity -> giftCardBatchService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GiftCardBatchListResponse(newList));
	}

	@Override
	public BaseResponse<GiftCardBatchByIdResponse> getById(@RequestBody @Valid GiftCardBatchByIdRequest giftCardBatchByIdRequest) {
		GiftCardBatch giftCardBatch =
		giftCardBatchService.getOne(giftCardBatchByIdRequest.getGiftCardBatchId());
		return BaseResponse.success(new GiftCardBatchByIdResponse(giftCardBatchService.wrapperVo(giftCardBatch)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid GiftCardBatchExportRequest request) {
		GiftCardBatchQueryRequest queryReq = KsBeanUtil.convert(request, GiftCardBatchQueryRequest.class);
		Long total = giftCardBatchService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<GiftCardBatchExportResponse> exportGiftCardBatchRecord(@RequestBody @Valid GiftCardBatchExportRequest request) {
		GiftCardBatchQueryRequest queryReq = KsBeanUtil.convert(request, GiftCardBatchQueryRequest.class);
		List<GiftCardBatchPageVO> data = KsBeanUtil.convert(giftCardBatchService.page(queryReq).getContent(), GiftCardBatchPageVO.class);
		return BaseResponse.success(new GiftCardBatchExportResponse(data));
	}
}

