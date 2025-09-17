package com.wanmi.ares.scheduled.flow;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @ClassName ReportFlowDataGenerate
 * @Description 流量统计最近七天、最近30天以及自然月的pv、uv数据
 * @Author lvzhenwei
 * @Date 2019/8/22 10:58
 **/
@Component
public class ReportFlowDataGenerate {

    @Resource
    private FlowDataStatisticsService flowDataStatisticsService;

    @XxlJob(value = "reportFlowDataGenerate")
    public void execute() throws Exception {
        String type = XxlJobHelper.getJobParam();
        flowDataStatisticsService.generateFlowData(type);
    }
}
