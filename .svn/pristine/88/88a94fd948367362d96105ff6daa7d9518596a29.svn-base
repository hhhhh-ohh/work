package com.wanmi.sbc.marketing.api.provider.appointmentsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.appointmentsale.*;
import com.wanmi.sbc.marketing.api.response.appointmentsale.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>预约抢购查询服务Provider</p>
 *
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@FeignClient(value = "${application.marketing.name}", contextId = "AppointmentSaleQueryProvider")
public interface AppointmentSaleQueryProvider {

    /**
     * 分页查询预约抢购API
     *
     * @param appointmentSalePageReq 分页请求参数和筛选对象 {@link AppointmentSalePageRequest}
     * @return 预约抢购分页列表信息 {@link AppointmentSalePageResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/page")
    BaseResponse<AppointmentSalePageResponse> page(@RequestBody @Valid AppointmentSalePageRequest appointmentSalePageReq);

    /**
     * 列表查询预约抢购API
     *
     * @param appointmentSaleListReq 列表请求参数和筛选对象 {@link AppointmentSaleListRequest}
     * @return 预约抢购的列表信息 {@link AppointmentSaleListResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/list")
    BaseResponse<AppointmentSaleListResponse> list(@RequestBody @Valid AppointmentSaleListRequest appointmentSaleListReq);

    /**
     * 单个查询预约抢购API
     *
     * @param appointmentSaleByIdRequest 单个查询预约抢购请求参数 {@link AppointmentSaleByIdRequest}
     * @return 预约抢购详情 {@link AppointmentSaleByIdResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/get-by-id")
    BaseResponse<AppointmentSaleByIdResponse> getById(@RequestBody @Valid AppointmentSaleByIdRequest appointmentSaleByIdRequest);


    /**
     * 查询商品是否正在进行预购活动
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleIsInProgressRequest}
     * @return 预购详情 {@link AppointmentSaleIsInProcessResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/is-in-progress")
    BaseResponse<AppointmentSaleIsInProcessResponse> isInProgress(@RequestBody @Valid AppointmentSaleIsInProgressRequest request);


    /**
     * 查询商品列表是否正在进行预购活动
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleIsInProgressRequest}
     * @return 预购详情 {@link AppointmentSaleIsInProcessResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/in-progress")
    BaseResponse<AppointmentSaleInProcessResponse> inProgressAppointmentSaleInfoByGoodsInfoIdList(@RequestBody @Valid AppointmentSaleInProgressRequest request);

    /**
     * 预购相关信息
     *
     * @param request 单个查询预购相关信息请求参数 {@link RushToAppointmentSaleGoodsRequest}
     * @return 预购相关信息 {@link AppointmentSaleByIdResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/get-appointment-sale-rela-info")
    BaseResponse<AppointmentSaleByIdResponse> getAppointmentSaleRelaInfo(@RequestBody @Valid RushToAppointmentSaleGoodsRequest request);


    /**
     * 预购相关信息(立即购买)
     *
     * @param request 单个查询预购相关信息请求参数 {@link RushToAppointmentSaleGoodsRequest}
     * @return 预购相关信息 {@link AppointmentSaleByIdResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/get-appointment-sale-rela-info-immediate-buy")
    BaseResponse<AppointmentSaleListResponse> getAppointmentSaleRelaInfoForImmediateBuy(@RequestBody @Valid AppointmentForImmediateBuyQueryRequest request);

    /**
     * 查询商品SPU维度是否存在预约活动中的商品（未开始，进行中的）
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleByGoodsIdRequest}
     * @return 预购详情 {@link AppointmentSaleNotEndResponse}
     * @author dany
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/get-not-end-activity")
    BaseResponse<AppointmentSaleNotEndResponse> getNotEndActivity(@RequestBody @Valid AppointmentSaleByGoodsIdRequest request);

    /**
     * 查询商品列表是否正在进行预购活动
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleIsInProgressRequest}
     * @return 预购详情 {@link AppointmentSaleIsInProcessResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/contain-appointmentsale-and-bookingsale")
    BaseResponse<AppointmentSaleInProcessResponse> containAppointmentSaleAndBookingSale(@RequestBody @Valid AppointmentSaleInProgressRequest request);

    /**
     * 合并校验
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleMergeInProgressRequest}
     * @return 预购详情 {@link AppointmentSaleMergeInProcessResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/merge-vaild-appointmentsale-and-bookingsale")
    BaseResponse<AppointmentSaleMergeInProcessResponse> mergeVaildAppointmentSaleAndBookingSale(@RequestBody @Valid AppointmentSaleMergeInProgressRequest request);

    /**
     * 合并获取预约预售信息
     *
     * @param request 查询商品是否正在进行预购活动请求参数 {@link AppointmentSaleAndBookingSaleRequest}
     * @return 预购详情 {@link AppointmentSaleAndBookingSaleResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/merge-appointmentsale-and-bookingsale")
    BaseResponse<AppointmentSaleAndBookingSaleResponse> mergeAppointmentSaleAndBookingSale(@RequestBody @Valid AppointmentSaleAndBookingSaleRequest request);

    /**
     * 分页查询预约抢购API
     *
     * @param appointmentSalePageReq 分页请求参数和筛选对象 {@link AppointmentSalePageRequest}
     * @return 预约抢购分页列表信息 {@link AppointmentSalePageResponse}
     * @author zxd
     */
    @PostMapping("/marketing/${application.marketing.version}/appointmentsale/page-new")
    BaseResponse<AppointmentSalePageResponse> pageNew(@RequestBody @Valid AppointmentSalePageRequest appointmentSalePageReq);

}

