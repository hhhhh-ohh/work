package com.wanmi.sbc.marketing.api.provider.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponAddRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponModifyRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponAddBatchResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponAddResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>电子卡券表保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "ElectronicCouponProvider")
public interface ElectronicCouponProvider {

	/**
	 * 新增电子卡券表API
	 *
	 * @author 许云鹏
	 * @param electronicCouponAddRequest 电子卡券表新增参数结构 {@link ElectronicCouponAddRequest}
	 * @return 新增的电子卡券表信息 {@link ElectronicCouponAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/add")
	BaseResponse<ElectronicCouponAddResponse> add(@RequestBody @Valid ElectronicCouponAddRequest electronicCouponAddRequest);

	/**
	 * 批量新增电子卡券表API
	 *
	 * @param addBatchRequest 电子卡券表新增参数结构 {@link ElectronicCouponAddBatchRequest}
	 * @return 新增的电子卡券表信息 {@link ElectronicCouponAddBatchResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/add-batch")
	BaseResponse<ElectronicCouponAddBatchResponse> addBatch(@RequestBody @Valid ElectronicCouponAddBatchRequest addBatchRequest);

	/**
	 * 修改电子卡券表API
	 *
	 * @author 许云鹏
	 * @param request 电子卡券表修改参数结构 {@link ElectronicCouponModifyRequest}
	 * @return 修改的电子卡券表信息 {@link ElectronicCouponModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/modify")
	BaseResponse<ElectronicCouponModifyResponse> modify(@RequestBody @Valid ElectronicCouponModifyRequest request);

	/**
	 * 卡券各状态数量统计API
	 * @return 数量统计 {@link BaseResponse}
	 * @author 许云鹏
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/statistical")
	BaseResponse cardStateStatistical();

	/**
	 * 更新冻结库存
	 * @return  {@link BaseResponse}
	 * @author zhanghao
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/update—freeze-stock")
	BaseResponse updateFreezeStock(@RequestBody @Valid ElectronicCouponUpdateFreezeStockRequest electronicCouponUpdateFreezeStockRequest);

	/**
	 * 更新卡券绑定关系
	 * @return  {@link BaseResponse}
	 * @author zhanghao
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/update—binding-flag")
	BaseResponse updateBindingFlag(@RequestBody @Valid ElectronicCouponUpdateBindRequest request);
}
