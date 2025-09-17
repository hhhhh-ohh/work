package com.wanmi.sbc.order.api.provider.distribution;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempAddRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempLedgerRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTemListResponse;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempDelResponse;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 分销任务临时表
 * @author  lvzhenwei
 * @date 2021/8/16 4:17 下午
 **/
@FeignClient(value = "${application.order.name}", contextId = "DistributionTaskTempProvider")
public interface DistributionTaskTempProvider {

    /**
     * @description 分销任务临时表数据分页查询接口
     * @author  lvzhenwei
     * @date 2021/8/16 4:53 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempGetListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/page-by-param")
    BaseResponse<DistributionTaskTempPageResponse> pageByParam(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 分销任务临时表数据查询list接口
     * @author  lvzhenwei
     * @date 2021/8/16 7:11 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempGetListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/find-by-order-id")
    BaseResponse<DistributionTaskTemListResponse> findByOrderId(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 增加退单数量
     * @author  lvzhenwei
     * @date 2021/8/16 7:48 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTemListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/add-return-order-num")
    BaseResponse addReturnOrderNum(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 减少退单数量
     * @author  lvzhenwei
     * @date 2021/8/16 7:49 下午
     * @param request 
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTemListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/minus-return-order-num")
    BaseResponse minusReturnOrderNum(@RequestBody @Valid DistributionTaskTempRequest request);


    /**
     * @description 根据id删除数据
     * @author  lvzhenwei
     * @date 2021/8/16 4:54 下午
     * @param request 
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempGetListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 根据ids删除数据
     * @author  lvzhenwei
     * @date 2021/8/16 7:25 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/delete-by-ids")
    BaseResponse<DistributionTaskTempDelResponse> deleteByIds(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 新增分销任务临时表数据
     * @author  lvzhenwei
     * @date 2021/8/16 7:06 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/save")
    BaseResponse save(@RequestBody @Valid DistributionTaskTempAddRequest request);

    /**
     * @description 分账入账数据分页查询接口
     * @author  xuyunpeng
     * @date 2022/7/26 4:53 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempGetListResponse>
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/page-by-ledger-task")
    BaseResponse<DistributionTaskTempPageResponse> pageByLedgerTask(@RequestBody @Valid DistributionTaskTempRequest request);

    /**
     * @description 分账入账数据分页查询接口
     * @author  xuyunpeng
     * @date 2022/7/26 4:53 下午
     * @param request  {@link DistributionTaskTempLedgerRequest}
     * @return
     **/
    @PostMapping("/order/${application.order.version}/distribution/tast/temp/udpate-for-ledger")
    BaseResponse updateForLedger(@RequestBody @Valid DistributionTaskTempLedgerRequest request);

}
