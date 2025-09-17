package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>礼品卡详情查询服务Provider</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardDetailQueryProvider")
public interface GiftCardDetailQueryProvider {

	/**
	 * 分页查询礼品卡详情API
	 *
	 * @author 马连峰
	 * @param giftCardDetailPageReq 分页请求参数和筛选对象 {@link GiftCardDetailPageRequest}
	 * @return 礼品卡详情分页列表信息 {@link GiftCardDetailPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/page")
	BaseResponse<GiftCardDetailPageResponse> page(@RequestBody @Valid GiftCardDetailPageRequest giftCardDetailPageReq);

	/**
	 * 列表查询礼品卡详情API
	 *
	 * @author 马连峰
	 * @param giftCardDetailListReq 列表请求参数和筛选对象 {@link GiftCardDetailListRequest}
	 * @return 礼品卡详情的列表信息 {@link GiftCardDetailListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/list")
	BaseResponse<GiftCardDetailListResponse> list(@RequestBody @Valid GiftCardDetailListRequest giftCardDetailListReq);

	/**
	 * 单个查询礼品卡详情API
	 *
	 * @author 马连峰
	 * @param giftCardDetailByIdRequest 单个查询礼品卡详情请求参数 {@link GiftCardDetailByIdRequest}
	 * @return 礼品卡详情详情 {@link GiftCardDetailByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/get-by-id")
	BaseResponse<GiftCardDetailByIdResponse> getById(@RequestBody @Valid GiftCardDetailByIdRequest giftCardDetailByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 马连峰
	 * @param request {tableDesc}导出数量查询请求 {@link GiftCardDetailExportRequest}
	 * @return 礼品卡详情数量 {@link Long}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid GiftCardDetailExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author 马连峰
	 * @param request {tableDesc}导出列表查询请求 {@link GiftCardDetailExportRequest}
	 * @return 礼品卡详情列表 {@link GiftCardDetailExportResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/export/page")
	BaseResponse<GiftCardDetailExportResponse> exportGiftCardDetailRecord(@RequestBody @Valid GiftCardDetailExportRequest request);

	/***
	 * @description
	 * @author  查询礼品卡导出携带一卡一码数据
	 * @date  
	 * @param request
	 * @return 
	 **/
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetailjoin/page")
	BaseResponse<GiftCardDetailJoinPageResponse> getGiftCardDetailPage(@RequestBody @Valid GiftCardDetailJoinPageRequest request);

}

