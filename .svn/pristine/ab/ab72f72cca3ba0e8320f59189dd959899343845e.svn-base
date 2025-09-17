package com.wanmi.sbc.order.api.provider.payingmemberpayrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordPageRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordPageResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordListRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordListResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordByIdRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordByIdResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordExportRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordExportResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费会员支付记录表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberPayRecordQueryProvider")
public interface PayingMemberPayRecordQueryProvider {

	/**
	 * 分页查询付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordPageReq 分页请求参数和筛选对象 {@link PayingMemberPayRecordPageRequest}
	 * @return 付费会员支付记录表分页列表信息 {@link PayingMemberPayRecordPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/page")
	BaseResponse<PayingMemberPayRecordPageResponse> page(@RequestBody @Valid PayingMemberPayRecordPageRequest payingMemberPayRecordPageReq);

	/**
	 * 列表查询付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordListReq 列表请求参数和筛选对象 {@link PayingMemberPayRecordListRequest}
	 * @return 付费会员支付记录表的列表信息 {@link PayingMemberPayRecordListResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/list")
	BaseResponse<PayingMemberPayRecordListResponse> list(@RequestBody @Valid PayingMemberPayRecordListRequest payingMemberPayRecordListReq);

	/**
	 * 单个查询付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordByIdRequest 单个查询付费会员支付记录表请求参数 {@link PayingMemberPayRecordByIdRequest}
	 * @return 付费会员支付记录表详情 {@link PayingMemberPayRecordByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/get-by-id")
	BaseResponse<PayingMemberPayRecordByIdResponse> getById(@RequestBody @Valid PayingMemberPayRecordByIdRequest payingMemberPayRecordByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberPayRecordExportRequest}
	 * @return 付费会员支付记录表数量 {@link Long}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberPayRecordExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberPayRecordExportRequest}
	 * @return 付费会员支付记录表列表 {@link PayingMemberPayRecordExportResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/export/page")
	BaseResponse<PayingMemberPayRecordExportResponse> exportPayingMemberPayRecordRecord(@RequestBody @Valid PayingMemberPayRecordExportRequest request);


	/**
	 * 根据业务订单/退单号查询交易记录
	 *
	 * @param recordByOrderCodeRequest 包含业务订单/退单号的查询参数 {@link TradeRecordByOrderCodeRequest}
	 * @return 交易记录 {@link PayingMemberPayRecordByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/get-trade-record-by-order-code")
	BaseResponse<PayingMemberPayRecordByIdResponse> getTradeRecordByOrderCode(@RequestBody @Valid TradeRecordByOrderCodeRequest
																		   recordByOrderCodeRequest);

}

