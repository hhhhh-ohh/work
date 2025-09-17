package com.wanmi.sbc.customer.provider.impl.distribution;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCommissionQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionPageRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCommissionExportResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCommissionPageResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCommissionForPageVO;
import com.wanmi.sbc.customer.distribution.model.root.DistributorLevel;
import com.wanmi.sbc.customer.distribution.service.DistributionCommissionService;
import com.wanmi.sbc.customer.distribution.service.DistributorLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by feitingting on 2019/2/21.
 */
@RestController
public class DistributionCommissionController implements DistributionCommissionQueryProvider {

    @Autowired
    private DistributionCommissionService distributionCommissionService;

    @Autowired
    private DistributorLevelService distributorLevelService;


    @Override
    public BaseResponse<DistributionCommissionPageResponse> findDistributionCommissionPage(@RequestBody @Valid DistributionCommissionPageRequest request) {
        DistributionCommissionPageResponse response = distributionCommissionService.page(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<DistributionCommissionExportResponse> findDistributionCommissionExport(@RequestBody @Valid DistributionCommissionExportRequest request) {
        DistributionCommissionPageRequest queryReq = new DistributionCommissionPageRequest();
        KsBeanUtil.copyPropertiesThird(request, queryReq);

        DistributionCommissionPageResponse response = distributionCommissionService.page(queryReq);
        List<DistributionCommissionForPageVO> dataRecords = response.getRecordList();
        List<DistributorLevel> distributorLevelList =  distributorLevelService.findAll();
        Map<String,String> map = distributorLevelList.stream().collect(Collectors.toMap(DistributorLevel::getDistributorLevelId,DistributorLevel::getDistributorLevelName));
        dataRecords = dataRecords.stream().map(distributionCommissionForPageVO -> {
            distributionCommissionForPageVO.setDistributorLevelName(map.get(distributionCommissionForPageVO.getDistributorLevelId()));
            return distributionCommissionForPageVO;
        }).collect(Collectors.toList());
        return BaseResponse.success(new DistributionCommissionExportResponse(dataRecords));
    }

    @Override
    public BaseResponse<Long> countForExport(@RequestBody @Valid DistributionCommissionExportRequest request) {
        DistributionCommissionPageRequest queryReq = new DistributionCommissionPageRequest();
        KsBeanUtil.copyPropertiesThird(request, queryReq);
        Long total = distributionCommissionService.count(queryReq);
        return BaseResponse.success(total);
    }

    @Override
    public BaseResponse initStatisticsCache(){
       Boolean map = distributionCommissionService.initStatisticsCache();
        return BaseResponse.success(map);
    }
}
