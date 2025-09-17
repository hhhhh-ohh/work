package com.wanmi.ares.report.flow.service;

import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.enums.UserType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.flow.dao.FlowReportMapper;
import com.wanmi.ares.report.flow.dao.ReplayFlowDayUserInfoMapper;
import com.wanmi.ares.report.flow.dao.ReplaySkuFlowMapper;
import com.wanmi.ares.report.flow.dao.ReplaySkuFlowUserInfoMapper;
import com.wanmi.ares.report.flow.model.reponse.FlowReponse;
import com.wanmi.ares.report.flow.model.reponse.FlowReportReponse;
import com.wanmi.ares.report.flow.model.reponse.FlowStoreReportResponse;
import com.wanmi.ares.report.flow.model.request.FlowReportRequest;
import com.wanmi.ares.report.flow.model.request.FlowStoreReportRequest;
import com.wanmi.ares.report.flow.model.request.ReplayFlowDayUserInfoRequest;
import com.wanmi.ares.report.flow.model.root.FlowReport;
import com.wanmi.ares.report.flow.model.root.ReplayFlowDayUserInfo;
import com.wanmi.ares.report.flow.model.root.ReplaySkuFlow;
import com.wanmi.ares.report.flow.model.root.ReplaySkuFlowUserInfo;
import com.wanmi.ares.report.goods.dao.GoodsTotalMapper;
import com.wanmi.ares.request.mq.FlowRequest;
import com.wanmi.ares.request.mq.GoodsInfoFlow;
import com.wanmi.ares.request.mq.TerminalStatistics;
import com.wanmi.ares.source.model.root.Store;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 流量报表service
 * Created by sunkun on 2017/9/25.
 */
@Slf4j
@Service
public class FlowReportService {

    @Resource
    private FlowReportMapper flowReportMapper;

    @Resource
    private ReplayStoreMapper replayStoreMapper;

    @Resource
    private ReplayFlowDayUserInfoMapper replayFlowDayUserInfoMapper;

    @Resource
    private ReplaySkuFlowMapper replaySkuFlowMapper;

    @Resource
    private ReplaySkuFlowUserInfoMapper replaySkuFlowUserInfoMapper;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowReportService.class);

    private static final int SIZE = 1000;


    /**
     * 更新
     *
     * @param flowRequest
     * @return
     */
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public boolean update(FlowRequest flowRequest) {
        FlowReport flowReport = this.getFlow(flowRequest.getPv(), flowRequest.getUv());
        flowReport.setId(flowRequest.getId());
        flowReport.setDate(flowRequest.getTime());
        flowReport.setSkuTotalPv(flowRequest.getSkuTotalPv().getAll());
        flowReport.setSkuTotalUvUserIds(flowRequest.getSkuTotalUv().getTotalUserIds());
        flowReport.setSkuTotalUv((long) flowReport.getSkuTotalUvUserIds().size());
        boolean flag = saveFlow(flowReport);
        if (flag&&CollectionUtils.isNotEmpty(flowRequest.getSkus())) {
            saveSkuFlow(flowRequest.getSkus(),flowRequest.getTime());
        }
        flowRequest.getCompanyFlows().forEach(companyFlow -> {
            try {
                FlowReport companyFlowReport = this.getFlow(companyFlow.getPv(), companyFlow.getUv());
                companyFlowReport.setId(companyFlow.getCompanyId());
                companyFlowReport.setDate(flowRequest.getTime());
                companyFlowReport.setSkuTotalPv(companyFlow.getSkuTotalPv().getAll());
                companyFlowReport.setSkuTotalUv((long) companyFlow.getSkuTotalUv().getTotalUserIds().size());
                companyFlowReport.setSkuTotalUvUserIds(companyFlow.getSkuTotalUv().getTotalUserIds());
                saveFlow(companyFlowReport);
            } catch (Exception e) {
                LOGGER.error("Store traffic error=>{}" , e.getStackTrace());
            }
        });

        /** 2021-09-23 add by zhengyang
            统计完成后，重新计算店铺、门店的统计数据
            判断当存在O2O配置开关并且打开时才生成记录 **/
        if (aresSystemConfigQueryService.queryO2oOpening()){
            generateFlowByStoreType(flowReport);
        }
        return flag;
    }

    /**
     * 获取流量列表
     *
     * @param request
     * @return
     */
    public FlowReportReponse getList(FlowReportRequest request) {
        List<FlowReport> list = getFlowList(request);
        FlowReportReponse flowReportReponse = new FlowReportReponse();
        if (CollectionUtils.isEmpty(list)) {
            return flowReportReponse;
        }
        FlowReponse flowReponse = countFlowData(list, request);
        BeanUtils.copyProperties(flowReponse, flowReportReponse);
        if (request.getIsWeek()) {
            flowReportReponse.setFlowList(getWeekList(list));
        } else {
            list.forEach(info -> {
                FlowReponse flowReponse1 = new FlowReponse();
                BeanUtils.copyProperties(info, flowReponse1);
                flowReponse1.setTitle(info.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + DateUtil.getWeekStr(info.getDate()));
                flowReportReponse.getFlowList().add(flowReponse1);
            });
        }
        return flowReportReponse;
    }

    /**
     * 店铺列表
     *
     * @param request
     * @return
     */
    public Page<FlowReponse> getStoreList(FlowReportRequest request) {
        if (request.getSortName().equals("date")) {
            request.setSortName("PV");
        }
        int total = flowReportMapper.countFlowPageByStore(request);
        request.setPageNum(request.getPageNum() * request.getPageSize() - request.getPageSize());
        //根据开始结束日期按shop_id分组top10查流量统计
        List<FlowReport> storeFlowReports = flowReportMapper.queryFlowPageByStore(request);
        //此处id为shop_id
        List<String> companyIds = storeFlowReports.stream().map(FlowReport::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(companyIds)) {
            return new PageImpl(new ArrayList<>(), request.getPageable(), total);
        }
        //根据shop_id查店铺
        List<Store> stores = replayStoreMapper.queryByCompanyIds(companyIds);
        request.setCompanyIds(companyIds);
        //根据shop_id查所有流量统计(含开始和结束时间)
        List<FlowReport> flowReportList = flowReportMapper.queryFlowByIds(request);
        List<FlowReponse> flowReponses = companyIds.stream().map(id -> {
            //此处的getId为shop_id
            List<FlowReport> flowReports = flowReportList.stream().filter(flowReport -> flowReport.getId().equals(id))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(flowReports)) {
                return null;
            }
            FlowReponse flowReponse = this.countFlow(flowReports);
            Store store = stores.stream().filter(s -> id.equals(s.getCompanyInfoId())).findFirst().orElse(null);
            flowReponse.setTitle(Objects.nonNull(store) ? store.getStoreName() : "");
            return flowReponse;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        request.setPageNum(request.getPageNum() / request.getPageSize());
        return new PageImpl(flowReponses, request.getPageable(), total);
    }

    /**
     * 获取流量列表
     *
     * @return
     */
    public List<FlowReport> getFlowList(FlowReportRequest request) {
        return flowReportMapper.queryFlow(request);
    }

    /**
     * 获取流量周报表
     *
     * @param list
     * @return
     */
    public List<FlowReponse> getWeekList(List<FlowReport> list) {
        List<FlowReponse> flowReponses = new ArrayList<>();
        List<FlowReport> rlist = new ArrayList<>();
        List<LocalDate> dataList = DateUtil.getDateDiff(list.get(0).getDate(), list.get(list.size() - 1).getDate());
        dataList.forEach(date -> {
            List<FlowReport> newFlowReports = list.stream().filter(info -> info.getDate().equals(date)).collect(Collectors.toList());
            if (7 - date.getDayOfWeek().getValue() > 0) {
                if (newFlowReports.size() > 0) {
                    rlist.add(newFlowReports.get(0));
                }
            } else {
                if (newFlowReports.size() > 0) {
                    rlist.add(newFlowReports.get(0));
                }
                if (rlist.size() > 0) {
                    FlowReponse flowReponse = countFlow(rlist);
                    flowReponses.add(flowReponse);
                    rlist.clear();
                }
            }
        });

        if (rlist.size() > 0) {
            FlowReponse flowReponse = countFlow(rlist);
            flowReponses.add(flowReponse);
        }
        return flowReponses;
    }


    private FlowReponse countFlow(List<FlowReport> list) {
        FlowReponse flowReponse = new FlowReponse();
        Set<String> userIds = new HashSet<>();
        Set<String> skuTotalUvUserIds = new HashSet<>();
        //流量统计的list
        list.forEach(info -> {
            //id为shop_id
            String flowId = info.getId() + "-" + DateUtil.format(info.getDate(), DateUtil.FMT_DATE_2);
            List<String> replayFlowDayUserInfoList = queryUserIdList(ReplayFlowDayUserInfoRequest.builder().flowDayId(flowId).userType(UserType.ALL.toValue()).build());
            if (replayFlowDayUserInfoList.size() > 0) {
                userIds.addAll(replayFlowDayUserInfoList);
            }
            List<String> replayFlowDaySkuUserInfoList = queryUserIdList(ReplayFlowDayUserInfoRequest.builder().flowDayId(flowId).userType(UserType.SKU.toValue()).build());
            if (replayFlowDaySkuUserInfoList.size() > 0) {
                skuTotalUvUserIds.addAll(replayFlowDaySkuUserInfoList);
            }
        });
        flowReponse.setId(list.get(0).getId());
        flowReponse.setSkuTotalPv(list.stream().mapToLong(info -> info.getSkuTotalPv()).sum());
        flowReponse.setTotalPv(list.stream().mapToLong(info -> info.getTotalPv()).sum());
        flowReponse.setTotalUv((long) userIds.size());
        flowReponse.setSkuTotalUv((long) skuTotalUvUserIds.size());
        flowReponse.setTitle(list.get(0).getDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_3)) + "-" + list.get(list.size() > 1 ? list.size() - 1 : 0).getDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_3)));
        return flowReponse;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author lvzhenwei
     * @Description 流量统计获取对应uv的userIdList数据
     * @Date 11:13 2019/8/21
     * @Param [replayFlowDayUserInfoRequest]
     **/
    public List<String> queryUserIdList(ReplayFlowDayUserInfoRequest replayFlowDayUserInfoRequest) {
        return replayFlowDayUserInfoMapper.queryUserIdList(replayFlowDayUserInfoRequest);
    }

    /**
     * @return com.wanmi.ares.report.flow.model.reponse.FlowReponse
     * @Author lvzhenwei
     * @Description 流量统计--流量概况汇总数据
     * @Date 11:00 2019/8/21
     * @Param [list, request]
     **/
    private FlowReponse countFlowData(List<FlowReport> list, FlowReportRequest request) {
        FlowReponse flowReponse = new FlowReponse();
        flowReponse.setId(list.get(0).getId());
        flowReponse.setSkuTotalPv(list.stream().mapToLong(info -> info.getSkuTotalPv()).sum());
        flowReponse.setTotalPv(list.stream().mapToLong(info -> info.getTotalPv()).sum());
        flowReponse.setTotalUv(countUv(request, UserType.ALL));
        flowReponse.setSkuTotalUv(countUv(request, UserType.SKU));
        flowReponse.setTitle(list.get(0).getDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_3)) + "-" + list.get(list.size() > 1 ? list.size() - 1 : 0).getDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_3)));
        return flowReponse;
    }

    /**
     * @return long
     * @Author lvzhenwei
     * @Description 获取对应日期内的访客数和商品访客数
     * @Date 11:01 2019/8/21
     * @Param [request, userType]
     **/
    public long countUv(FlowReportRequest request, UserType userType) {
        return (long) replayFlowDayUserInfoMapper.queryCountUserIds(
                ReplayFlowDayUserInfoRequest.builder()
                        .startFlowDate(request.getBeginDate())
                        .endFlowDate(request.getEndDate())
                        .userType(userType.toValue())
                        .companyIdFlag(request.getCompanyId() + "-")
                        .build());
    }

    /**
     * 获取流量分页列表
     *
     * @param request
     */
    public Page<FlowReponse> getPage(FlowReportRequest request) {
        request.setPageNum(request.getPageNum() * request.getPageSize() - request.getPageSize());
        // 判断参数是否为空
        if(Objects.nonNull(request.getBeginDate())){
            request.setBeginDateStr(DateUtil.format(request.getBeginDate(),"yyyy-MM-dd"));
        }
        if(Objects.nonNull(request.getEndDate())){
            request.setEndDateStr(DateUtil.format(request.getEndDate(),"yyyy-MM-dd"));
        }
        List<FlowReport> flowReportList = flowReportMapper.queryFlowPage(request);
        int total = flowReportMapper.queryFlowCount(request);
        List<FlowReponse> list = new ArrayList<>();
        flowReportList.forEach(info -> {
            FlowReponse flowReponse = new FlowReponse();
            BeanUtils.copyProperties(info, flowReponse);
            list.add(flowReponse);
        });
        request.setPageNum(request.getPageNum() / request.getPageSize());
        return new PageImpl(list, request.getPageable(), total);
    }

    /**
     * @Author lvzhenwei
     * @Description 获取店铺对应流量数据
     * @Date 10:16 2019/9/11
     * @Param [request]
     * @return java.util.List<com.wanmi.ares.report.flow.model.reponse.FlowStoreReportResponse>
     **/
    public List<FlowStoreReportResponse> getFlowStoreReportList(FlowStoreReportRequest request){
        return flowReportMapper.queryFlowStoreReportList(request);
    }

    private FlowReport getFlow(TerminalStatistics pv, TerminalStatistics uv) {
        return FlowReport.builder().appPv(pv.getAPP()).h5Pv(pv.getH5()).pcPv(pv.getPC()).totalPv(pv.getAll())
                .appUv((long) uv.getAppUserIds().size()).H5Uv((long) uv.getH5UserIds().size())
                .pcUv((long) uv.getPcUserIds().size()).totalUv((long) uv.getTotalUserIds().size()).userIds(uv.getTotalUserIds())
                .build();
    }


    /**
     * 保存全站流量统计数据
     *
     * @param flowReport
     */
    private boolean saveFlow(FlowReport flowReport) {
        String flowId = flowReport.getId() + "-" + DateUtil.format(flowReport.getDate(), DateUtil.FMT_DATE_2);
        flowReportMapper.deleteById(flowId);
        flowReportMapper.saveFlow(flowReport);
        //userIds数据保存以及skuTotalUvUserIds数据保存
        replayFlowDayUserInfoMapper.deleteByPrimary(ReplayFlowDayUserInfo.builder().flowDayId(flowId).build());
        saveReplayFlowDayUserInfo(flowReport, UserType.ALL.toValue());
        saveReplayFlowDayUserInfo(flowReport, UserType.SKU.toValue());
        return true;
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description userIds数据保存以及skuTotalUvUserIds数据保存
     * @Date 14:53 2019/8/20
     * @Param [flowReport, type]
     **/
    private void saveReplayFlowDayUserInfo(FlowReport flowReport, int type) {
        String flowId = flowReport.getId() + "-" + DateUtil.format(flowReport.getDate(), DateUtil.FMT_DATE_2);
        List<ReplayFlowDayUserInfo> replayFlowDaySkuUserInfoList = new ArrayList<>();
        Iterator<String> it = flowReport.getUserIds().iterator();
        if (type == 1) {
            it = flowReport.getSkuTotalUvUserIds().iterator();
        }
        while (it.hasNext()) {
            replayFlowDaySkuUserInfoList.add(ReplayFlowDayUserInfo.builder()
                    .flowDayId(flowId)
                    .flowDate(flowReport.getDate())
                    .userId(it.next())
                    .userType(type)
                    .build());
            if (replayFlowDaySkuUserInfoList.size() > 500) {
                replayFlowDayUserInfoMapper.insertByList(replayFlowDaySkuUserInfoList);
                replayFlowDaySkuUserInfoList.clear();
            }
        }
        if (replayFlowDaySkuUserInfoList.size() > 0) {
            replayFlowDayUserInfoMapper.insertByList(replayFlowDaySkuUserInfoList);
        }
    }

    /**
     * 保存商品维度流量统计数据
     *
     * @param skus
     */
    private void saveSkuFlow(List<GoodsInfoFlow> skus,LocalDate date) {
        String formatDate = DateUtil.format(date, DateUtil.FMT_DATE_1);
        log.info("商品维度流量统计skuSize={},date={}", skus == null ? null : skus.size(), formatDate);
        List<ReplaySkuFlow> replaySkuFlowList = new ArrayList<>();
        List<String> replaySkuIds = new ArrayList<>();
        List<String> skuIds = new ArrayList<>();
        List<ReplaySkuFlowUserInfo> replaySkuFlowUserInfoList = new ArrayList<>();
        Map<String,Set<String>> skuIdAndUserInfoIdMap = new HashMap<>();
        WmCollectionUtils.notEmpty2Loop(skus, info -> {
            //获取skuId对应商品店铺id
            String tmp = info.getCompanyId();
            if(Objects.isNull(tmp)){
                return;
            }
            Long companyId = Long.parseLong(tmp);
            ReplaySkuFlow replaySkuFlow = ReplaySkuFlow.builder()
                    .appPv(info.getPv().getAPP())
                    .h5Pv(info.getPv().getH5())
                    .pcPv(info.getPv().getPC())
                    .miniprogramPv(info.getPv().getMINIPROGRAM())
                    .totalPv(info.getPv().getAll())
                    .appUv((long) info.getUv().getAppUserIds().size())
                    .h5Uv((long) info.getUv().getH5UserIds().size())
                    .pcUv((long) info.getUv().getPcUserIds().size())
                    .miniprogramUv((long) info.getUv().getMINIPROGRAMUserIds().size())
                    .totalUv((long) info.getUv().getTotalUserIds().size())
                    .skuId(info.getSkuId())
                    .companyId(companyId.toString())
                    .skuFlowDate(date)
                    .sendTime(LocalDateTime.now())
                    .receiveTime(LocalDateTime.now())
                    .build();
            replaySkuFlowList.add(replaySkuFlow);
            replaySkuIds.add(info.getSkuId());
            skuIds.add(info.getSkuId());
            skuIdAndUserInfoIdMap.put(info.getSkuId(),info.getUv().getTotalUserIds());
            if(replaySkuFlowList.size()== SIZE){
                replaySkuFlowMapper.deleteByPrimary(ReplaySkuFlow.builder().skuIds(replaySkuIds).skuFlowDate(date).build());
                replaySkuFlowMapper.insertList(replaySkuFlowList);
                for(ReplaySkuFlow replaySkuFlowInfo:replaySkuFlowList){
                    Iterator<String> skuUserSet = skuIdAndUserInfoIdMap.get(replaySkuFlowInfo.getSkuId()).iterator();
                    while(skuUserSet.hasNext()){
                        String userId = skuUserSet.next();
                        replaySkuFlowUserInfoList.add(ReplaySkuFlowUserInfo.builder()
                                .skuFlowId(replaySkuFlowInfo.getId().toString())
                                .skuId(replaySkuFlowInfo.getSkuId())
                                .userId(userId)
                                .skuFlowDate(date)
                                .sendTime(LocalDateTime.now())
                                .receiveTime(LocalDateTime.now())
                                .build());
                    }
                }
                replaySkuFlowList .clear();
                replaySkuIds.clear();
            }
        });
        if(replaySkuFlowList.size()>0&&replaySkuFlowList.size()< SIZE){
            replaySkuFlowMapper.deleteByPrimary(ReplaySkuFlow.builder().skuIds(replaySkuIds).skuFlowDate(date).build());
            replaySkuFlowMapper.insertList(replaySkuFlowList);
            for(ReplaySkuFlow replaySkuFlowInfo:replaySkuFlowList){
                Iterator<String> skuUserSet = skuIdAndUserInfoIdMap.get(replaySkuFlowInfo.getSkuId()).iterator();
                while(skuUserSet.hasNext()){
                    String userId = skuUserSet.next();
                    replaySkuFlowUserInfoList.add(ReplaySkuFlowUserInfo.builder()
                            .skuFlowId(replaySkuFlowInfo.getId().toString())
                            .skuId(replaySkuFlowInfo.getSkuId())
                            .userId(userId)
                            .skuFlowDate(date)
                            .sendTime(LocalDateTime.now())
                            .receiveTime(LocalDateTime.now())
                            .build());
                }
            }
            replaySkuFlowList .clear();
        }
        if(replaySkuFlowUserInfoList.size()>0){
            int num = (int) Math.ceil((float) skuIds.size() / (float) SIZE);
            for(int pageNum = 0 ; pageNum < num ; pageNum++){
                int startNum = pageNum * SIZE;
                int endNum = startNum + SIZE;
                if(endNum > skuIds.size()){
                    endNum = skuIds.size();
                }
                List<String> skuIdList = skuIds.subList(startNum,endNum);
                replaySkuFlowUserInfoMapper.deleteByPrimary(ReplaySkuFlowUserInfo.builder().skuIds(skuIdList).skuFlowDate(date).build());
            }
            int flowUserInfoListNum = (int) Math.ceil((float) replaySkuFlowUserInfoList.size() / (float) SIZE);
            for(int pageNum = 0 ; pageNum < flowUserInfoListNum ; pageNum++){
                int startNum = pageNum * SIZE;
                int endNum = startNum + SIZE;
                if(endNum > replaySkuFlowUserInfoList.size()){
                    endNum = replaySkuFlowUserInfoList.size();
                }
                List<ReplaySkuFlowUserInfo> newReplaySkuFlowUserInfoList = replaySkuFlowUserInfoList.subList(startNum,endNum);
                replaySkuFlowUserInfoMapper.insertByList(newReplaySkuFlowUserInfoList);
            }
        }
    }

    /***
     * 根据商家类型生成统计数据
     * @param flowReport
     */
    private void generateFlowByStoreType(FlowReport flowReport) {
        // 统计商家数据
        String flowId = StoreSelectType.SUPPLIER.getMockCompanyInfoId() + "-" +
                DateUtil.format(flowReport.getDate(), DateUtil.FMT_DATE_2);
        flowReportMapper.deleteById(flowId);
        flowReportMapper.saveFlowSumByStoreType(
                flowReport.getDate(),
                StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        flowId = StoreSelectType.O2O.getMockCompanyInfoId() + "-" +
                DateUtil.format(flowReport.getDate(), DateUtil.FMT_DATE_2);
        flowReportMapper.deleteById(flowId);
        flowReportMapper.saveFlowSumByStoreType(
                flowReport.getDate(),
                StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
