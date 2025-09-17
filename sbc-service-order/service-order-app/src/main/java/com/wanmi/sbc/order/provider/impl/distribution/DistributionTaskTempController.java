package com.wanmi.sbc.order.provider.impl.distribution;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.api.provider.distribution.DistributionTaskTempProvider;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempAddRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempLedgerRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTemListResponse;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempDelResponse;
import com.wanmi.sbc.order.api.response.distribution.DistributionTaskTempPageResponse;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import com.wanmi.sbc.order.distribution.model.root.DistributionTaskTemp;
import com.wanmi.sbc.order.distribution.service.DistributionTaskTempService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvzhenwei
 * @className DistributionTaskTempController
 * @description 分销任务临时表查询接口实现
 * @date 2021/8/16 4:22 下午
 **/
@RestController
public class DistributionTaskTempController implements DistributionTaskTempProvider {

    @Autowired
    private DistributionTaskTempService distributionTaskTempService;

    @Override
    public BaseResponse<DistributionTaskTempPageResponse> pageByParam(@RequestBody @Valid DistributionTaskTempRequest request) {
        DistributionTaskTempPageResponse response = new DistributionTaskTempPageResponse();
        Page<DistributionTaskTemp> distributionTaskTempPage = distributionTaskTempService.pageByParam(request);
        Page<DistributionTaskTempVO> newPage = distributionTaskTempPage.map(entity -> distributionTaskTempService.wrapperVo(entity));
        MicroServicePage<DistributionTaskTempVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        response.setDistributionTaskTempVOPage(microPage);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<DistributionTaskTemListResponse> findByOrderId(@Valid DistributionTaskTempRequest request) {
        List<DistributionTaskTemp> distributionTaskTempList = distributionTaskTempService.findByOrderId(request.getOrderId());
        List<DistributionTaskTempVO> distributionTaskTempVOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(distributionTaskTempList)){
            distributionTaskTempList.forEach(distributionTaskTemp -> {
                distributionTaskTempVOList.add(distributionTaskTempService.wrapperVo(distributionTaskTemp));
            });
        }
        DistributionTaskTemListResponse distributionTaskTemListResponse = new DistributionTaskTemListResponse();
        distributionTaskTemListResponse.setDistributionTaskTempVOList(distributionTaskTempVOList);
        return BaseResponse.success(distributionTaskTemListResponse);
    }

    @Override
    public BaseResponse addReturnOrderNum(@Valid DistributionTaskTempRequest request) {
        distributionTaskTempService.addReturnOrderNum(request.getOrderId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DistributionTaskTemListResponse> minusReturnOrderNum(@Valid DistributionTaskTempRequest request) {
        distributionTaskTempService.minusReturnOrderNum(request.getOrderId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DistributionTaskTempPageResponse> deleteById(@Valid DistributionTaskTempRequest request) {
        distributionTaskTempService.deleteById(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DistributionTaskTempDelResponse> deleteByIds(@Valid DistributionTaskTempRequest request) {
        int num = distributionTaskTempService.deleteByIds(request.getIds());
        DistributionTaskTempDelResponse distributionTaskTempDelResponse = new DistributionTaskTempDelResponse();
        distributionTaskTempDelResponse.setNum(num);
        return BaseResponse.success(distributionTaskTempDelResponse);
    }

    @Override
    public BaseResponse save(@Valid DistributionTaskTempAddRequest request) {
        distributionTaskTempService.save(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DistributionTaskTempPageResponse> pageByLedgerTask(@RequestBody @Valid DistributionTaskTempRequest request) {
        DistributionTaskTempPageResponse response = new DistributionTaskTempPageResponse();
        Page<DistributionTaskTemp> distributionTaskTempPage = distributionTaskTempService.pageByLedgerTask(request);
        Page<DistributionTaskTempVO> newPage = distributionTaskTempPage.map(entity -> distributionTaskTempService.wrapperVo(entity));
        MicroServicePage<DistributionTaskTempVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        response.setDistributionTaskTempVOPage(microPage);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse updateForLedger(@RequestBody @Valid DistributionTaskTempLedgerRequest request) {
        distributionTaskTempService.updateForLedger(request.getOrderId(), request.getParams(), request.getLedgerTime());
        return BaseResponse.SUCCESSFUL();
    }
}
