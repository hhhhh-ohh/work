package com.wanmi.sbc.crm.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.crm.api.provider.rfmgroupstatistics.RfmGroupStatisticsQueryProvide;
import com.wanmi.sbc.crm.api.request.rfmgroupstatistics.RfmGroupStatisticsListRequest;
import com.wanmi.sbc.crm.api.request.rfmgroupstatistics.RfmGroupStatisticsPageRequest;
import com.wanmi.sbc.crm.api.response.rfmgroupstatistics.RfmGroupStatisticsListResponse;
import com.wanmi.sbc.crm.api.response.rfmgroupstatistics.RfmGroupStatisticsPageResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * @ClassName RfmGroupStatisticsController
 * @Description 系统人群分析
 * @Author lvzhenwei
 * @Date 2019/10/21 14:51
 **/
@Tag(name =  "系统人群分析", description =  "RfmGroupStatisticsController")
@RestController
@Validated
@RequestMapping(value = "/crm/rfmGroupStatistics")
public class RfmGroupStatisticsController {

    @Autowired
    private RfmGroupStatisticsQueryProvide rfmGroupStatisticsQueryProvide;

    /**
     * @Author lvzhenwei
     * @Description rfm系统人群分页查询接口
     * @Date 14:54 2019/10/21
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.crm.api.response.rfmgroupstatistics.RfmGroupStatisticsPageResponse>
     **/
    @Operation(summary = "rfm系统人群分页查询接口")
    @PostMapping("/page")
    public BaseResponse<RfmGroupStatisticsPageResponse> page(@RequestBody RfmGroupStatisticsPageRequest request){
        return rfmGroupStatisticsQueryProvide.queryRfmGroupStatisticsDataPage(request);
    }

    /**
     * @Author lvzhenwei
     * @Description rfm系统人群列表查询接口
     * @Date 14:57 2019/10/21
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.crm.api.response.rfmgroupstatistics.RfmGroupStatisticsListResponse>
     **/
    @Operation(summary = "rfm系统人群列表查询接口")
    @PostMapping("/list")
    public BaseResponse<RfmGroupStatisticsListResponse> list(@RequestBody RfmGroupStatisticsListRequest request){
        return rfmGroupStatisticsQueryProvide.queryRfmGroupStatisticsDataList(request);
    }
}
