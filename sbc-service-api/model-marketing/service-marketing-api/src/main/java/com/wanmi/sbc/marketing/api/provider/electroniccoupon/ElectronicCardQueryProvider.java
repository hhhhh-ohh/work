package com.wanmi.sbc.marketing.api.provider.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>电子卡密表查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@FeignClient(value = "${application.marketing.name}", contextId = "ElectronicCardQueryProvider")
public interface ElectronicCardQueryProvider {

	/**
	 * 分页查询卡密导入记录表API
	 *
	 * @author 许云鹏
	 * @param electronicImportRecordPageReq 分页请求参数和筛选对象 {@link ElectronicImportRecordPageRequest}
	 * @return 卡密导入记录表分页列表信息 {@link ElectronicImportRecordPageRequest}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/import/record/page")
	BaseResponse<ElectronicImportRecordPageResponse> getImportRecordPage(@RequestBody @Valid ElectronicImportRecordPageRequest electronicImportRecordPageReq);

	/**
	 * 查询重复数据的卡密API
	 *
	 * @author 许云鹏
	 * @param request 请求参数和筛选对象 {@link ElectronicImportCheckRequest}
	 * @return 重复数据信息 {@link ElectronicImportCheckResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/import/exists")
	BaseResponse<ElectronicImportCheckResponse> getExistsData(@RequestBody @Valid ElectronicImportCheckRequest request);

	/**
	 * 分页查询电子卡密表API
	 *
	 * @author 许云鹏
	 * @param electronicCardPageReq 分页请求参数和筛选对象 {@link ElectronicCardPageRequest}
	 * @return 电子卡密表分页列表信息 {@link ElectronicCardPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/page")
	BaseResponse<ElectronicCardPageResponse> page(@RequestBody @Valid ElectronicCardPageRequest electronicCardPageReq);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 许云鹏
	 * @param request {tableDesc}导出数量查询请求 {@link ElectronicCardExportRequest}
	 * @return 电子卡密表数量 {@link Long}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/card/export/count")
	BaseResponse<Long> countForExport(@RequestBody @Valid ElectronicCardExportRequest request);

	/**
	 * 分页查询卡密发放记录表API
	 *
	 * @author 许云鹏
	 * @param electronicSendRecordPageReq 分页请求参数和筛选对象 {@link ElectronicSendRecordPageRequest}
	 * @return 卡密发放记录表分页列表信息 {@link ElectronicSendRecordPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/send-record/page")
	BaseResponse<ElectronicSendRecordPageResponse> sendRecordPage(@RequestBody @Valid ElectronicSendRecordPageRequest electronicSendRecordPageReq);


	/**
	 * 根据卡券id批量失效卡密API
	 *
	 * @author 许云鹏
	 * @param electronicCardInvalidRequest 批量失效参数结构 {@link ElectronicCardInvalidRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/count-by-coupon-id")
	BaseResponse<ElectronicCardModifyCountResponse> countCardInvalidByCouponId(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest);




	/**
	 * 根据卡券id统计有效卡密API
	 *
	 * @author 许云鹏
	 * @param electronicCardInvalidRequest 批量失效参数结构 {@link ElectronicCardInvalidRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/count-effective-coupon")
	BaseResponse<ElectronicCardModifyCountResponse> countEffectiveCoupon(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest);

	/**
	 * 查询卡密API
	 *
	 * @author 许云鹏
	 * @param electronicCardByIdRequest 电子卡密表查询参数结构 {@link ElectronicCardByIdRequest}
	 * @return 查询结果 {@link ElectronicCardByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electronic/cards/find-by-id")
	BaseResponse<ElectronicCardByIdResponse> findById(@RequestBody @Valid ElectronicCardByIdRequest electronicCardByIdRequest);

    /**
     * 根据订单号查询卡密API
     *
     * @author 许云鹏
     * @param request 电子卡密表查询参数结构 {@link ElectronicCardNumByOrderNoRequest}
     * @return 查询结果 {@link ElectronicCardNumByOrderNoResponse}
     */
    @PostMapping("/goods/${application.goods.version}/electronic/cards/count-by-order-no-and-coupon-id")
    BaseResponse<ElectronicCardNumByOrderNoResponse> countByOrderNoAndCouponId(@RequestBody @Valid ElectronicCardNumByOrderNoRequest request);
}

