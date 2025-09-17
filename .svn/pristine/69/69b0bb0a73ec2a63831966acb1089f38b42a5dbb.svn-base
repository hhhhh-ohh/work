package com.wanmi.sbc.empower.api.provider.deliveryrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.deliveryrecord.*;
import com.wanmi.sbc.empower.api.response.deliveryrecord.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>达达配送记录查询服务Provider</p>
 *
 * @author zhangwenchang
 */
@FeignClient(value = "${application.empower.name}", contextId = "DeliveryRecordDadaQueryProvider")
public interface DeliveryRecordDadaQueryProvider {

    /**
     * 分页查询达达配送记录API
     *
     * @param request 分页请求参数和筛选对象 {@link DeliveryRecordDadaPageRequest}
     * @return 达达配送记录分页列表信息 {@link DeliveryRecordDadaPageResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/page")
    BaseResponse<DeliveryRecordDadaPageResponse> page(@RequestBody @Valid DeliveryRecordDadaPageRequest request);

    /**
     * 列表查询达达配送记录API
     *
     * @param request 列表请求参数和筛选对象 {@link DeliveryRecordDadaListRequest}
     * @return 达达配送记录的列表信息 {@link DeliveryRecordDadaListResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/list")
    BaseResponse<DeliveryRecordDadaListResponse> list(@RequestBody @Valid DeliveryRecordDadaListRequest request);

    /**
     * 单个查询达达配送记录API
     *
     * @param request 单个查询达达配送记录请求参数 {@link DeliveryRecordDadaByIdRequest}
     * @return 达达配送记录详情 {@link DeliveryRecordDadaByIdResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/get-by-id")
    BaseResponse<DeliveryRecordDadaByIdResponse> getById(@RequestBody @Valid DeliveryRecordDadaByIdRequest request);

    /**
     * 查询达达配送费
     *
     * @param request 请求对象 {@link DeliveryRecordDadaDeliverFeeQueryRequest}
     * @return 达达配送费信息 {@link DeliveryRecordDadaDeliverFeeQueryResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/query-deliver-fee")
    BaseResponse<DeliveryRecordDadaDeliverFeeQueryResponse> queryDeliverFee(@RequestBody @Valid
                                                                                    DeliveryRecordDadaDeliverFeeQueryRequest
                                                                                    request);

    /**
     * 取消原因列表
     *
     * @param request 请求对象 {@link DeliveryRecordDadaReasonListRequest}
     * @return 达达配送记录的列表信息 {@link DeliveryRecordDadaListResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/reason-list")
    BaseResponse<DeliveryRecordDadaReasonListResponse> reasonList(@RequestBody @Valid
                                                                          DeliveryRecordDadaReasonListRequest request);

    /**
     * 城市列表
     *
     * @param request 请求对象 {@link DeliveryRecordDadaCityListRequest}
     * @return 达达配送城市列表信息 {@link DeliveryRecordDadaCityListResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/city-list")
    BaseResponse<DeliveryRecordDadaCityListResponse> cityList(@RequestBody @Valid
                                                                      DeliveryRecordDadaCityListRequest request);

    /**
     * 查询配送进度
     *
     * @param request 请求对象 {@link DeliveryRecordDadaProgressQueryRequest}
     * @return 达达配送进度信息 {@link DeliveryRecordDadaProgressQueryResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/query-progress")
    BaseResponse<DeliveryRecordDadaProgressQueryResponse> queryProgress(@RequestBody @Valid
                                                                                DeliveryRecordDadaProgressQueryRequest request);

}

