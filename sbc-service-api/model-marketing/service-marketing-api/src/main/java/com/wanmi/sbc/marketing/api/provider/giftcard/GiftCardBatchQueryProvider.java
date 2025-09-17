package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchPageResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchListRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchListResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchByIdRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchByIdResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchExportRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>礼品卡批次查询服务Provider</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardBatchQueryProvider")
public interface GiftCardBatchQueryProvider {

	/**
	 * 分页查询礼品卡批次API
	 *
	 * @author 马连峰
	 * @param giftCardBatchPageReq 分页请求参数和筛选对象 {@link GiftCardBatchPageRequest}
	 * @return 礼品卡批次分页列表信息 {@link GiftCardBatchPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/page")
	BaseResponse<GiftCardBatchPageResponse> page(@RequestBody @Valid GiftCardBatchPageRequest giftCardBatchPageReq);

	/**
	 * 列表查询礼品卡批次API
	 *
	 * @author 马连峰
	 * @param giftCardBatchListReq 列表请求参数和筛选对象 {@link GiftCardBatchListRequest}
	 * @return 礼品卡批次的列表信息 {@link GiftCardBatchListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/list")
	BaseResponse<GiftCardBatchListResponse> list(@RequestBody @Valid GiftCardBatchListRequest giftCardBatchListReq);

	/**
	 * 单个查询礼品卡批次API
	 *
	 * @author 马连峰
	 * @param giftCardBatchByIdRequest 单个查询礼品卡批次请求参数 {@link GiftCardBatchByIdRequest}
	 * @return 礼品卡批次详情 {@link GiftCardBatchByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/get-by-id")
	BaseResponse<GiftCardBatchByIdResponse> getById(@RequestBody @Valid GiftCardBatchByIdRequest giftCardBatchByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 马连峰
	 * @param request {tableDesc}导出数量查询请求 {@link GiftCardBatchExportRequest}
	 * @return 礼品卡批次数量 {@link Long}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid GiftCardBatchExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author 马连峰
	 * @param request {tableDesc}导出列表查询请求 {@link GiftCardBatchExportRequest}
	 * @return 礼品卡批次列表 {@link GiftCardBatchExportResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/export/page")
	BaseResponse<GiftCardBatchExportResponse> exportGiftCardBatchRecord(@RequestBody @Valid GiftCardBatchExportRequest request);
}

