package com.wanmi.sbc.communityactivity;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.communityactivity.response.CommunityActivityByIdMainResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivityOversoldByIdResponse;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.communityleader.service.CommunityStatisticsService;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.StorePartColsListByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityListRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityListResponse;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityPageResponse;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsByIdResponse;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityCommissionLeaderRelVO;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.order.request.TradeExportRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.ConfigUpdateRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.CommunityConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Tag(name =  "社区团购活动表管理API", description =  "CommunityActivityBaseController")
@RestController
@RequestMapping(value = "/communityActivity")
public class CommunityActivityBaseController {

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityStatisticsService communityStatisticsService;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Operation(summary = "分页查询社区团购活动表")
    @PostMapping("/page")
    public BaseResponse<CommunityActivityPageResponse> getPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        pageReq.putSort("createTime", "desc");
        Operator operator = commonUtil.getOperator();
        Long storeId = StringUtils.isBlank(operator.getStoreId())?null:NumberUtils.toLong(operator.getStoreId());
        pageReq.setStoreId(storeId);
        if(StringUtils.isNotBlank(pageReq.getStoreName())){
            ListStoreByNameRequest nameRequest = ListStoreByNameRequest.builder().storeName(pageReq.getStoreName()).build();
            List<Long> storeIds = storeQueryProvider.listByName(nameRequest).getContext().getStoreVOList().stream()
                    .map(StoreVO::getStoreId)
                    .collect(Collectors.toList());
            if(CollectionUtils.isEmpty(storeIds)) {
                return BaseResponse.success(CommunityActivityPageResponse.builder()
                        .communityActivityPage(new MicroServicePage<>(Collections.emptyList()))
                        .communityStatisticsList(Collections.emptyList()).build());
            }
            pageReq.setStoreIds(storeIds);
        }

        CommunityActivityPageResponse response = communityActivityQueryProvider.page(pageReq).getContext();
        //填充店铺名称
        if(Platform.PLATFORM.equals(operator.getPlatform())
                && CollectionUtils.isNotEmpty(response.getCommunityActivityPage().getContent())) {
            List<Long> storeIds = response.getCommunityActivityPage().getContent().stream()
                    .map(CommunityActivityVO::getStoreId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(storeIds)) {
                StorePartColsListByIdsRequest idsRequest = StorePartColsListByIdsRequest.builder().storeIds(storeIds)
                        .cols(Arrays.asList("storeName", "storeId")).build();
                Map<Long, String> storeMap = storeQueryProvider.listStorePartColsByIds(idsRequest).getContext().getStoreVOList().stream()
                        .filter(s -> StringUtils.isNotBlank(s.getStoreName()))
                        .collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
                response.getCommunityActivityPage().getContent().stream()
                        .filter(s -> storeMap.containsKey(s.getStoreId()))
                        .forEach(s -> s.setStoreName(storeMap.get(s.getStoreId())));
            }
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "分页查询社区团购活动表")
    @PostMapping("/statisticsPage")
    public BaseResponse<CommunityActivityPageResponse> statisticsPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        CommunityActivityPageResponse response = this.getPage(pageReq).getContext();
        MicroServicePage<CommunityActivityVO> activityPage = response.getCommunityActivityPage();
        List<CommunityStatisticsVO> statisticsList = Collections.emptyList();
        if(CollectionUtils.isNotEmpty(activityPage.getContent())) {
            statisticsList = communityStatisticsService.totalByActivity(activityPage.getContent().stream()
                    .map(CommunityActivityVO::getActivityId).collect(Collectors.toList()));
        }
        response.setCommunityStatisticsList(statisticsList);
        return BaseResponse.success(response);
    }

    @Operation(summary = "列表查询社区团购活动表")
    @PostMapping("/list")
    public BaseResponse<CommunityActivityListResponse> getList(@RequestBody @Valid CommunityActivityListRequest listReq) {
        listReq.putSort("createTime", "desc");
        listReq.setStoreId(commonUtil.getStoreId());
        return communityActivityQueryProvider.list(listReq);
    }

    @ReturnSensitiveWords(functionName = "f_community_leader_page_word")
    @Operation(summary = "根据id查询社区团购活动表")
    @GetMapping("/{activityId}")
    public BaseResponse<CommunityActivityByIdMainResponse> getById(@PathVariable String activityId) {
        if (activityId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityActivityByIdMainResponse mainResponse = new CommunityActivityByIdMainResponse();

        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(activityId);
        idReq.setStoreId(commonUtil.getStoreId());
        idReq.setSkuRelFlag(Boolean.TRUE);
        idReq.setCommissionRelFlag(Boolean.TRUE);
        idReq.setSaleRelFlag(Boolean.TRUE);
        CommunityActivityByIdResponse response = communityActivityQueryProvider.getById(idReq).getContext();
        mainResponse.setCommunityActivityVO(response.getCommunityActivityVO());
        List<String> skuIds = response.getCommunityActivityVO().getSkuList().stream().map(CommunitySkuRelVO::getSkuId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(skuIds)) {
            //填充商品信息
            GoodsInfoListByConditionRequest conditionRequest = GoodsInfoListByConditionRequest.builder()
                    .goodsInfoIds(skuIds)
                    .showSpecFlag(Boolean.TRUE)
                    .fillLmInfoFlag(Boolean.TRUE)
                    .showProviderInfoFlag(Boolean.TRUE)
                    .fillStoreCate(Boolean.TRUE)
                    .build();
            mainResponse.setSkuList(goodsInfoQueryProvider.listByCondition(conditionRequest).getContext().getGoodsInfos());

            //填充品牌名称
            List<Long> brandIds = mainResponse.getSkuList().stream().map(GoodsInfoVO::getBrandId).collect(Collectors.toList());
            GoodsBrandByIdsRequest idsRequest = GoodsBrandByIdsRequest.builder().brandIds(brandIds).build();
            Map<Long, String> brandMap = goodsBrandQueryProvider.listByIds(idsRequest).getContext()
                    .getGoodsBrandVOList().stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, GoodsBrandVO::getBrandName));
            mainResponse.getSkuList().stream().filter(s -> brandMap.containsKey(s.getBrandId()))
                    .forEach(s -> s.setBrandName(brandMap.get(s.getBrandId())));
        }

        if (CommunityCommissionFlag.PICKUP.equals(response.getCommunityActivityVO().getCommissionFlag())
                && CollectionUtils.isNotEmpty(response.getCommunityActivityVO().getCommissionLeaderList())) {
            List<String> pickupIds = response.getCommunityActivityVO().getCommissionLeaderList().stream().map(CommunityCommissionLeaderRelVO::getPickupPointId).collect(Collectors.toList());
            CommunityLeaderPickupPointListRequest listRequest = CommunityLeaderPickupPointListRequest.builder().delFlag(DeleteFlag.NO).assistRelFlag(Boolean.TRUE).pickupPointIdList(pickupIds).build();
            mainResponse.setCommissionLeaderList(communityLeaderPickupPointQueryProvider.list(listRequest).getContext().getCommunityLeaderPickupPointList());

            if (CollectionUtils.isNotEmpty(mainResponse.getCommissionLeaderList())) {
                Map<String, CommunityStatisticsVO> statisticsMap = communityStatisticsService.totalByLeaders(
                                mainResponse.getCommissionLeaderList().stream().map(CommunityLeaderPickupPointVO::getLeaderId).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(CommunityStatisticsVO::getLeaderId, Function.identity()));
                CommunityStatisticsVO empty = new CommunityStatisticsVO();
                mainResponse.getCommissionLeaderList().forEach(l -> {
                    l.setPickupServiceOrderNum(statisticsMap.getOrDefault(l.getLeaderId(), empty).getPickupServiceOrderNum());
                    l.setAssistOrderNum(statisticsMap.getOrDefault(l.getLeaderId(), empty).getAssistOrderNum());
                });
            }
        }
        return BaseResponse.success(mainResponse);
    }

    @Operation(summary = "查询社区团购统计明细")
    @GetMapping("/statistics/{activityId}")
    public BaseResponse<CommunityStatisticsByIdResponse> getStatistics(@PathVariable String activityId) {
        CommunityStatisticsVO statistics = communityStatisticsService.totalByActivity(Collections.singletonList(activityId)).get(0);
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(activityId);
        idReq.setStoreId(commonUtil.getStoreId());
        CommunityActivityVO activity = communityActivityQueryProvider.getById(idReq).getContext().getCommunityActivityVO();
        statistics.setActivityName(activity.getActivityName());
        statistics.setStartTime(activity.getStartTime());
        statistics.setEndTime(activity.getEndTime());
        if (Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            StorePartColsListByIdsRequest idsRequest = StorePartColsListByIdsRequest.builder().storeIds(Collections.singletonList(activity.getStoreId()))
                    .cols(Collections.singletonList("storeName")).build();
            storeQueryProvider.listStorePartColsByIds(idsRequest).getContext().getStoreVOList().stream().findFirst()
                    .ifPresent(s -> statistics.setStoreName(s.getStoreName()));
        }
        return BaseResponse.success(CommunityStatisticsByIdResponse.builder().communityStatistics(statistics).build());
    }

    @Operation(summary = "查询活动是否存在超卖订单")
    @GetMapping("/oversold/{activityId}")
    public BaseResponse<CommunityActivityOversoldByIdResponse> getOversold(@PathVariable String activityId) {
        Integer checkResult = communityActivityService.checkOversoldTrade(activityId, commonUtil.getStoreId());
        return BaseResponse.success(CommunityActivityOversoldByIdResponse.builder().result(checkResult).build());
    }

    /**
     * 查询社区团购开关设置
     * @param
     * @return
     */
    @Operation(summary = "查询社区团购开关设置")
    @GetMapping("/config")
    public BaseResponse<SystemConfigTypeResponse> getCommunityConfig(){
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.COMMUNITY_CONFIG.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest);
    }

    /**
     * 修改社区团购开关设置
     */
    @Operation(summary = "修改社区团购开关设置")
    @RequestMapping(value = "/config/modify", method = RequestMethod.POST)
    public BaseResponse modifyCommunityConfig (@RequestBody @Valid CommunityConfigModifyRequest request) {
        Platform platform = commonUtil.getOperator().getPlatform();
        if(platform == null || !platform.equals(Platform.PLATFORM)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        ConfigUpdateRequest userSetting = new ConfigUpdateRequest();
        userSetting.setConfigKey(ConfigKey.COMMUNITY_CONFIG.toValue());
        userSetting.setStatus(request.getStatus().ordinal());
        systemConfigSaveProvider.update(userSetting);

        operateLogMQUtil.convertAndSend("设置", "修改社区团购开关",
                "状态：" + (request.getStatus().equals(DefaultFlag.YES) ? "开启" : "关闭"));

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 导出团订单
     * @param encrypted
     */
    @Operation(summary = "导出团订单")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/trade/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportDetail(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        TradeExportRequest request = JSON.parseObject(decrypted, TradeExportRequest.class);
        if (StringUtils.isBlank(request.getCommunityActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.COMMUNITY_TRADE_LIST);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
