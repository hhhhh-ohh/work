package com.wanmi.sbc.marketing.api.provider.appointmentsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsByIdRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsPageRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsalegoods.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>预约抢购查询服务Provider</p>
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@FeignClient(value = "${application.marketing.name}", contextId = "AppointmentSaleGoodsQueryProvider")
public interface AppointmentSaleGoodsQueryProvider {

	/**
	 * 分页查询预约抢购API
	 *
	 * @author zxd
	 * @param appointmentSaleGoodsPageReq 分页请求参数和筛选对象 {@link AppointmentSaleGoodsPageRequest}
	 * @return 预约抢购分页列表信息 {@link AppointmentSaleGoodsPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/page")
	BaseResponse<AppointmentSaleGoodsPageResponse> page(@RequestBody @Valid AppointmentSaleGoodsPageRequest appointmentSaleGoodsPageReq);

	/**
	 * 列表查询预约抢购API
	 *
	 * @author zxd
	 * @param appointmentSaleGoodsListReq 列表请求参数和筛选对象 {@link AppointmentSaleGoodsListRequest}
	 * @return 预约抢购的列表信息 {@link AppointmentSaleGoodsListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/list")
	BaseResponse<AppointmentSaleGoodsListResponse> list(@RequestBody @Valid AppointmentSaleGoodsListRequest appointmentSaleGoodsListReq);

	/**
	 * 单个查询预约抢购API
	 *
	 * @author zxd
	 * @param appointmentSaleGoodsByIdRequest 单个查询预约抢购请求参数 {@link AppointmentSaleGoodsByIdRequest}
	 * @return 预约抢购详情 {@link AppointmentSaleGoodsByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/get-by-id")
	BaseResponse<AppointmentSaleGoodsByIdResponse> getById(@RequestBody @Valid AppointmentSaleGoodsByIdRequest appointmentSaleGoodsByIdRequest);


	/**
	 * 获取魔方预约活动商品列表分页信息
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/page-boss")
    BaseResponse<AppointmentResponse> pageBoss(@RequestBody @Valid AppointmentGoodsInfoSimplePageRequest request);


	/**
	 * 获取魔方H5预约活动商品列表分页信息
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/pageAppointmentGoodsInfo")
	BaseResponse<AppointmentGoodsResponse> pageAppointmentGoodsInfo(@RequestBody @Valid AppointmentGoodsInfoSimplePageRequest request);

	/**
	 * 预约商品验证
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/appointmentsalegoods/validate")
	BaseResponse validate(@RequestBody @Valid AppointmentSaleGoodsValidateRequest request);
}

