package com.wanmi.ares.scheduled.flow;

import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.enums.StatisticsWeekType;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.flow.dao.*;
import com.wanmi.ares.report.flow.model.reponse.FlowDataInfoResponse;
import com.wanmi.ares.report.flow.model.request.FlowDataRequest;
import com.wanmi.ares.report.flow.model.root.FlowMonth;
import com.wanmi.ares.report.flow.model.root.FlowSeven;
import com.wanmi.ares.report.flow.model.root.FlowThirty;
import com.wanmi.ares.report.flow.model.root.FlowWeek;
import com.wanmi.ares.report.flow.service.FlowDataStrategy;
import com.wanmi.ares.report.flow.service.FlowDataStrategyChooser;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.WeekDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FlowDataStatisticsService
 * @Description 流量统计统计最近7天、最近30天、最近半年按月统计及按周统计数据
 * @Author lvzhenwei
 * @Date 2019/8/22 15:10
 **/
@Service
public class FlowDataStatisticsService {

    @Resource
    private FlowReportMapper flowReportMapper;

    @Resource
    private FlowSevenMapper flowSevenMapper;

    @Resource
    private FlowThirtyMapper flowThirtyMapper;

    @Resource
    private FlowMonthMapper flowMonthMapper;

    @Resource
    private FlowWeekMapper flowWeekMapper;

    @Resource
    private FlowDataStrategyChooser saveFlowDataStrategyChooser;
    @Resource
    private  FlowDataStatisticsService dataStatisticsService;
    /**
     * @return void
     * @Author lvzhenwei
     * @Description 流量统计最近七天、最近30天以及最近半年的自然月数据汇总
     * @Date 15:52 2019/8/22
     * @Param []
     **/
    public void generateFlowData(String type) {
        if(StringUtils.isNotBlank(type)){
            String[] typeArr = type.split(",");
            for(String generateType:typeArr){
                if(generateType.equals(StatisticsDataType.SEVEN.toValue()+"")){
                    dataStatisticsService.queryFlowDataInfo(StatisticsDataType.SEVEN);
                } else if(generateType.equals(StatisticsDataType.THIRTY.toValue()+"")){
                    dataStatisticsService.queryFlowDataInfo(StatisticsDataType.THIRTY);
                } else if(generateType.equals(StatisticsDataType.MONTH.toValue()+"")){
                    dataStatisticsService. queryFlowDataInfoForMonth();
                }
            }
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 流量统计最近七天、最近30天数据汇总
     * @Date 14:10 2019/8/22
     * @Param [flowDataType]
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void queryFlowDataInfo(StatisticsDataType flowDataType) {
        FlowDataRequest flowDataRequest = new FlowDataRequest();
        if (StatisticsDataType.SEVEN == flowDataType) {
            //删除最近7天历史数据
            flowSevenMapper.deleteByPrimary(new FlowSeven());
            flowDataRequest.setFlowDataType(StatisticsDataType.SEVEN);
            flowDataRequest.setBeginDate(LocalDate.now().minusDays(7));
        } else if (StatisticsDataType.THIRTY == flowDataType) {
            //删除最近30天历史数据
            flowThirtyMapper.deleteByPrimary(new FlowThirty());
            FlowWeek flowWeek = new FlowWeek();
            flowWeek.setType(StatisticsWeekType.THIRTY_WEEK.toValue());
            //删除最近30天周历史数据
            flowWeekMapper.deleteByPrimary(flowWeek);
            flowDataRequest.setFlowDataType(StatisticsDataType.THIRTY);
            flowDataRequest.setBeginDate(LocalDate.now().minusDays(30));
        }
        flowDataRequest.setEndDate(LocalDate.now().minusDays(1));
        queryFlowDataForBoss(flowDataRequest);
        queryFlowDataForSupplier(flowDataRequest);
        if (StatisticsDataType.THIRTY == flowDataType) {
            flowDataRequest.setFlowDataType(StatisticsDataType.THIRTY_WEEK);
            queryFlowDataInfoForWeek(flowDataRequest);
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 流量统计最近六个月，自然月数据统计,每个月的1号统计上个月的数据
     * @Date 14:43 2019/8/22
     * @Param []
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void queryFlowDataInfoForMonth() {
        FlowWeek flowWeek = new FlowWeek();
        flowWeek.setType(StatisticsWeekType.MONTH_WEEK.toValue());
        flowWeek.setMonth(DateUtil.formatLocalDate(LocalDate.now().minusMonths(7), DateUtil.FMT_MONTH_2));
        flowWeekMapper.deleteByPrimary(flowWeek);
        FlowMonth flowMonth = new FlowMonth();
        flowMonth.setMonth(DateUtil.formatLocalDate(LocalDate.now().minusMonths(7), DateUtil.FMT_MONTH_2));
        flowMonthMapper.deleteByPrimary(flowMonth);
        FlowDataRequest flowDataRequest = new FlowDataRequest();
        LocalDate beginDate = DateUtil.getMonthFirstAndLastDayDate(1, 0);
        LocalDate endDate = DateUtil.getMonthFirstAndLastDayDate(1, 1);
        flowDataRequest.setBeginDate(beginDate);
        flowDataRequest.setEndDate(endDate);
        flowDataRequest.setFlowDataType(StatisticsDataType.MONTH);
        flowDataRequest.setMonth(DateUtil.formatLocalDate(DateUtil.getMonthFirstAndLastDayDate(1, 0), DateUtil.FMT_MONTH_2));
        queryFlowDataForBoss(flowDataRequest);
        queryFlowDataForSupplier(flowDataRequest);
        flowDataRequest.setFlowDataType(StatisticsDataType.MONTH_WEEK);
        queryFlowDataInfoForWeek(flowDataRequest);
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 按周统计数据
     * @Date 18:54 2019/8/22
     * @Param [flowDataRequest]
     **/
    private void queryFlowDataInfoForWeek(FlowDataRequest flowDataRequest) {
        List<WeekDate> weekDateList = DateUtil.getWeekLastDay(flowDataRequest.getBeginDate(), flowDataRequest.getEndDate());
        weekDateList.forEach(weekDate -> {
            FlowDataRequest request = new FlowDataRequest();
            request.setBeginDate(weekDate.getBeginDate());
            request.setEndDate(weekDate.getEndDate());
            request.setFlowDataType(flowDataRequest.getFlowDataType());
            request.setMonth(DateUtil.formatLocalDate(flowDataRequest.getBeginDate(), DateUtil.FMT_MONTH_2));
            request.setWeekStartDate(weekDate.getBeginDate());
            request.setWeekEndDate(weekDate.getEndDate());
            queryFlowDataForBoss(request);
            queryFlowDataForSupplier(request);
        });
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 对应统计周期流量统计--boss运营后台
     * @Date 14:47 2019/8/22
     * @Param [flowReportRequest]
     **/
    private void queryFlowDataForBoss(FlowDataRequest flowDataRequest) {
        flowDataRequest.setCompanyId("0");
        queryFlowData(flowDataRequest);
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 对应统计周期流量统计--supplier商家后台
     * @Date 14:47 2019/8/22
     * @Param [flowReportRequest]
     **/
    private void queryFlowDataForSupplier(FlowDataRequest flowDataRequest) {
        flowDataRequest.setCompanyId("-1");
        queryFlowData(flowDataRequest);
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 对应统计周期流量统计
     * @Date 14:44 2019/8/22
     * @Param [flowReportRequest]
     **/
    private void queryFlowData(FlowDataRequest flowDataRequest) {
        flowDataRequest.setPageSize(500);
        int num = flowReportMapper.queryFlowDataNum(flowDataRequest);
        if (num > 0) {
            //进行分页处理
            int pageNum = (int) Math.ceil((double) num / 500);
            String cacheCompanyId = flowDataRequest.getCompanyId();
            for (int i = 0; i < pageNum; i++) {
                flowDataRequest.setPageNum(i*500);
                List<FlowDataInfoResponse> flowDataResponseList = flowReportMapper.queryFlowDataInfo(flowDataRequest);
                if (i == 0 && "-1".equals(cacheCompanyId)){
                    flowDataRequest.setCompanyId(StoreSelectType.SUPPLIER.getMockCompanyInfoId());
                    flowDataResponseList.addAll(flowReportMapper.queryFlowDataInfo(flowDataRequest));
                    flowDataRequest.setCompanyId(StoreSelectType.O2O.getMockCompanyInfoId());
                    flowDataResponseList.addAll(flowReportMapper.queryFlowDataInfo(flowDataRequest));
                }
                flowDataRequest.setCompanyId(cacheCompanyId);
                FlowDataStrategy flowDataInspectionSolver = saveFlowDataStrategyChooser.choose(flowDataRequest.getFlowDataType());
                flowDataInspectionSolver.saveFlowData(flowDataResponseList, flowDataRequest);
            }
        }
    }

}
