package com.wanmi.sbc.customer.api.provider.points;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailBatchAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailImportAddRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>会员积分明细保存服务Provider</p>
 */
@FeignClient(value = "${application.customer.name}", contextId = "CustomerPointsDetailSaveProvider")
public interface CustomerPointsDetailSaveProvider {

    /**
     * 新增会员积分明细API
     *
     * @param customerPointsDetailAddRequest 会员积分明细新增参数结构 {@link CustomerPointsDetailAddRequest}
     * @return 新增的会员积分明细信息 {@link CustomerPointsDetailResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/add")
    BaseResponse add(@RequestBody @Valid CustomerPointsDetailAddRequest customerPointsDetailAddRequest);

    /**
     * 批量新增会员积分明细API
     *
     * @param request 会员积分明细新增参数结构 {@link CustomerPointsDetailAddRequest}
     * @return 批量新增的会员积分明细信息 {@link CustomerPointsDetailResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/batchadd")
    BaseResponse batchAdd(@RequestBody @Valid CustomerPointsBatchAdjustRequest request);

    /**
     * 批量减少会员积分
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/batchreduce")
    BaseResponse batchReduce(@RequestBody @Valid CustomerPointsBatchAdjustRequest request);

    /**
     * 批量覆盖会员积分
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/batchcover")
    BaseResponse batchCover(@RequestBody @Valid CustomerPointsBatchAdjustRequest request);

    /**
     * 新增会员积分明细API
     *
     * @param addRequest 会员积分明细新增参数结构 {@link CustomerPointsDetailAddRequest}
     * @return 新增的会员积分明细信息 {@link CustomerPointsDetailResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/add-batch")
    BaseResponse batchAddCustomer(@RequestBody @Valid CustomerPointsDetailImportAddRequest addRequest);

    /**
     * 会员积分返还明细API
     *
     * @param customerPointsDetailAddRequest 会员积分明细新增参数结构 {@link CustomerPointsDetailAddRequest}
     * @return 会员积分返还明细信息 {@link CustomerPointsDetailResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/return-points")
    BaseResponse returnPoints(@RequestBody @Valid CustomerPointsDetailAddRequest customerPointsDetailAddRequest);

    /**
     * 新增批量会员积分明细API
     *
     * @param customerPointsDetailBatchAddRequest 会员积分明细新增参数结构 {@link CustomerPointsDetailAddRequest}
     * @return 新增的会员积分明细信息 {@link CustomerPointsDetailResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customerpointsdetail/batch_add")
    BaseResponse batchAdd(@RequestBody @Valid CustomerPointsDetailBatchAddRequest customerPointsDetailBatchAddRequest);

}

