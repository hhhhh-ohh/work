package com.wanmi.sbc.open;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.agent.AgentQueryProvider;
import com.wanmi.sbc.customer.api.request.agent.GetAgentRequest;
import com.wanmi.sbc.customer.api.response.agent.CrmOaAuthResponse;
import com.wanmi.sbc.customer.api.response.agent.GetAgentResponse;
import com.wanmi.sbc.open.request.GetSchooluUniformSalesDataRequest;
import com.wanmi.sbc.open.response.CrmSchooluUniformSalesDataResponse;
import com.wanmi.sbc.order.api.provider.orderperformance.OrderPerformanceQueryProvider;
import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceByUniqueCodesRequest;
import com.wanmi.sbc.order.api.response.orderperformance.GetSchooluUniformSalesDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/swdCrm")
@Slf4j
public class CrmAgentOrderDataController {

    @Autowired
    private AgentQueryProvider agentQueryProvider;

    @Autowired
    private OrderPerformanceQueryProvider orderPerformanceQueryProvider;

    @Operation(summary = "新版接口获取整体区域销售数据")
    @PostMapping("/getSchooluUniformSalesData")
    public BaseResponse<GetSchooluUniformSalesDataResponse> getSchooluUniformSalesData(@RequestBody GetSchooluUniformSalesDataRequest request){
        log.info("获取整体区域销售数据:{}",request);
        //设置响应参数
        GetSchooluUniformSalesDataResponse getSchooluUniformSalesDataResponse = new GetSchooluUniformSalesDataResponse();
        //判断oa账号是否为空
        if (StringUtils.isBlank(request.getOaAccount())) {
            return BaseResponse.success(getSchooluUniformSalesDataResponse);
        }
        //获取区域权限
        BaseResponse<CrmOaAuthResponse> crmOaAuthResponseBaseResponse = agentQueryProvider.crmOaAuth(request.getOaAccount());
        CrmOaAuthResponse crmOaAuthData = crmOaAuthResponseBaseResponse.getContext();
        if (CollUtil.isEmpty(crmOaAuthData.getCityList()) && CollUtil.isEmpty(crmOaAuthData.getAreaList())){
            return BaseResponse.success(getSchooluUniformSalesDataResponse);
        }
        OrderPerformanceByUniqueCodesRequest orderPerformanceByUniqueCodesRequest = new OrderPerformanceByUniqueCodesRequest();
        orderPerformanceByUniqueCodesRequest.setStartTime(request.getStartTime());
        orderPerformanceByUniqueCodesRequest.setEndTime(request.getEndTime());
        orderPerformanceByUniqueCodesRequest.setAreaId(request.getAreaId());
        orderPerformanceByUniqueCodesRequest.setCityList(crmOaAuthData.getCityList());
        orderPerformanceByUniqueCodesRequest.setAreaList(crmOaAuthData.getAreaList());

        BaseResponse<GetSchooluUniformSalesDataResponse> response = orderPerformanceQueryProvider.getSchooluUniformSalesDataNew(orderPerformanceByUniqueCodesRequest);
        return response;
    }

    @Operation(summary = "获取整体区域销售数据")
    @PostMapping("/getSchooluUniformSalesDataOld")
    public BaseResponse<GetSchooluUniformSalesDataResponse> getSchooluUniformSalesDataOld(@RequestBody GetSchooluUniformSalesDataRequest request){
        log.info("获取整体区域销售数据:{}",request);
        //设置响应参数
        GetSchooluUniformSalesDataResponse getSchooluUniformSalesDataResponse = new GetSchooluUniformSalesDataResponse();
        //判断oa账号是否为空
        if (StringUtils.isBlank(request.getOaAccount())) {
            return BaseResponse.success(getSchooluUniformSalesDataResponse);
        }
        GetAgentRequest getAgentRequest = new GetAgentRequest();
        getAgentRequest.setAreaId(request.getAreaId());
        getAgentRequest.setOaAccount(request.getOaAccount());
        BaseResponse<List<GetAgentResponse>> agentQueryProviderAgentListResponse = agentQueryProvider.getOaAuthAgentList(getAgentRequest);
        List<GetAgentResponse> agentResponses = agentQueryProviderAgentListResponse.getContext();
        if (CollUtil.isEmpty(agentResponses)){
            return BaseResponse.success(getSchooluUniformSalesDataResponse);
        }
        List<String> agentUniqaueCodeList = agentResponses.stream().map(GetAgentResponse::getAgentUniqueCode).collect(Collectors.toList());

        OrderPerformanceByUniqueCodesRequest orderPerformanceByUniqueCodesRequest = new OrderPerformanceByUniqueCodesRequest();
        orderPerformanceByUniqueCodesRequest.setUniqueCodes(agentUniqaueCodeList);
        orderPerformanceByUniqueCodesRequest.setStartTime(request.getStartTime());
        orderPerformanceByUniqueCodesRequest.setEndTime(request.getEndTime());
        orderPerformanceByUniqueCodesRequest.setAreaId(request.getAreaId());

        BaseResponse<GetSchooluUniformSalesDataResponse> schooluUniformSalesDataResponse = orderPerformanceQueryProvider.getAllSchooluUniformSalesDataAndZero(orderPerformanceByUniqueCodesRequest);

        return schooluUniformSalesDataResponse;
    }
    @Operation(summary = "废弃的接口获取整体区域销售数据")
    @PostMapping("/getSchooluUniformSalesDataNoUse")
    public BaseResponse<CrmSchooluUniformSalesDataResponse> getSchooluUniformSalesDataNoUse(@RequestBody GetSchooluUniformSalesDataRequest request){
        log.info("获取整体区域销售数据:{}",request);
        //设置响应参数
        CrmSchooluUniformSalesDataResponse crmSchooluUniformSalesDataResponse = new CrmSchooluUniformSalesDataResponse();
        crmSchooluUniformSalesDataResponse.setChildList(new ArrayList<>());
        //判断oa账号是否为空
        if (StringUtils.isBlank(request.getOaAccount())) {
            return BaseResponse.success(crmSchooluUniformSalesDataResponse);
        }
        GetAgentRequest getAgentRequest = new GetAgentRequest();
        getAgentRequest.setAreaId(request.getAreaId());
        getAgentRequest.setOaAccount(request.getOaAccount());
        BaseResponse<List<GetAgentResponse>> agentQueryProviderAgentListResponse = agentQueryProvider.getOaAuthAgentList(getAgentRequest);
        List<GetAgentResponse> agentResponses = agentQueryProviderAgentListResponse.getContext();
        if (CollUtil.isEmpty(agentResponses)){
            return BaseResponse.success(crmSchooluUniformSalesDataResponse);
        }
        List<String> agentUniqaueCodeList = agentResponses.stream().map(GetAgentResponse::getAgentUniqueCode).collect(Collectors.toList());

        OrderPerformanceByUniqueCodesRequest orderPerformanceByUniqueCodesRequest = new OrderPerformanceByUniqueCodesRequest();
        orderPerformanceByUniqueCodesRequest.setUniqueCodes(agentUniqaueCodeList);
        orderPerformanceByUniqueCodesRequest.setStartTime(request.getStartTime());
        orderPerformanceByUniqueCodesRequest.setEndTime(request.getEndTime());

        BaseResponse<List<GetSchooluUniformSalesDataResponse>> schooluUniformSalesDataResponse = orderPerformanceQueryProvider.getSchooluUniformSalesData(orderPerformanceByUniqueCodesRequest);
        List<GetSchooluUniformSalesDataResponse> getSchooluUniformSalesDataResponseList = schooluUniformSalesDataResponse.getContext();
        //计算出总数据
        crmSchooluUniformSalesDataResponse = sumSaleAmountResponses(getSchooluUniformSalesDataResponseList);

        //判断是查所有的还是 查询某个区的数据
        if (request.getAreaId() != null) {
            Map<String, List<String>> shopNameToAgentCodes = agentResponses.stream()
                    .collect(Collectors.groupingBy(
                            entity -> entity.getShopName() != null ? entity.getShopName() : "未知店铺",
                            Collectors.mapping(
                                    entity -> entity.getAgentUniqueCode() != null ? entity.getAgentUniqueCode() : "",
                                    Collectors.toList()
                            )
                    ));
            List<CrmSchooluUniformSalesDataResponse> shopChildList = shopNameToAgentCodes.entrySet().stream().map(entry -> {
                String shopName = entry.getKey();
                List<String> theUniqueCodeList = entry.getValue();
                List<GetSchooluUniformSalesDataResponse> filterData = filterByAgentUniqueCodes(getSchooluUniformSalesDataResponseList, theUniqueCodeList);
                CrmSchooluUniformSalesDataResponse crmSchooluUniformSalesDataResponseChild = sumCommissionResponses(filterData);
                crmSchooluUniformSalesDataResponseChild.setShopName(shopName);
                return crmSchooluUniformSalesDataResponseChild;
            }).collect(Collectors.toList());
            //区域查询页面设置门店列表
            crmSchooluUniformSalesDataResponse.setChildList(shopChildList);
        }else {
            //查询所有城市数据
            Map<Long, Map<Long, List<GetAgentResponse>>> cityMap = agentResponses.stream()
                    .collect(Collectors.groupingBy(
                            GetAgentResponse::getCityId,  // 第一级分组：按 cityId
                            Collectors.groupingBy(
                                    GetAgentResponse::getAreaId  // 第二级分组：按 areaId
                            )
                    ));
            List<CrmSchooluUniformSalesDataResponse> cityDataList =
            cityMap.entrySet().stream().map(cityEntry -> {
                //单个 城市数据
                CrmSchooluUniformSalesDataResponse tempcityData = new CrmSchooluUniformSalesDataResponse();
                //收集区域销售数据
                List<GetSchooluUniformSalesDataResponse> citySaleDataList = new ArrayList<>();

                Map<Long, List<GetAgentResponse>> areaMap = cityEntry.getValue();
                List<CrmSchooluUniformSalesDataResponse> areaDataList = areaMap.entrySet().stream().map(areaEntry -> {
                    //获取区域下的所有代理商
                    List<GetAgentResponse> values = areaEntry.getValue();
                    //设置城市id,名称,区域id,区域名称
                    tempcityData.setCityId(values.get(0).getCityId());
                    tempcityData.setCityName(values.get(0).getCityName());

                    List<String> theAreaUniqueCodeList = values.stream().map(GetAgentResponse::getAgentUniqueCode).collect(Collectors.toList());
                    List<GetSchooluUniformSalesDataResponse> filterData = filterByAgentUniqueCodes(getSchooluUniformSalesDataResponseList, theAreaUniqueCodeList);
                    //收集
                    citySaleDataList.addAll(filterData);
                    CrmSchooluUniformSalesDataResponse areaData = sumCommissionResponses(filterData);
                    areaData.setCityId(values.get(0).getCityId());
                    areaData.setCityName(values.get(0).getCityName());
                    areaData.setAreaId(values.get(0).getAreaId());
                    areaData.setAreaName(values.get(0).getAreaName());
                    return areaData;
                }).collect(Collectors.toList());
                CrmSchooluUniformSalesDataResponse cityData = sumCommissionResponses(citySaleDataList);
                cityData.setCityName(tempcityData.getCityName());
                cityData.setCityId(tempcityData.getCityId());
                cityData.setChildList(areaDataList);
                return cityData;
            }).collect(Collectors.toList());
            crmSchooluUniformSalesDataResponse.setChildList(cityDataList);
        }

        return BaseResponse.success(crmSchooluUniformSalesDataResponse);
    }

    public CrmSchooluUniformSalesDataResponse sumCommissionResponses(List<GetSchooluUniformSalesDataResponse> responseList) {
        if (responseList== null || responseList.isEmpty()){
            return new CrmSchooluUniformSalesDataResponse();
        }
        // 使用 reduce 进行累加，提供初始值
        GetSchooluUniformSalesDataResponse reduceResponse = responseList.stream()
                .reduce(new GetSchooluUniformSalesDataResponse(), (sum, response) -> {
                    // 累加数量（处理空值）
                    // 累加数量（处理 sum 和 response 的空值）
                    sum.setSummerNum(getValueOrDefault(sum.getSummerNum()) + getValueOrDefault(response.getSummerNum()));
                    sum.setAutumnNum(getValueOrDefault(sum.getAutumnNum()) + getValueOrDefault(response.getAutumnNum()));
                    sum.setWinterNum(getValueOrDefault(sum.getWinterNum()) + getValueOrDefault(response.getWinterNum()));
                    // 重新计算总数量
                    sum.setTotalNum(getValueOrDefault(sum.getTotalNum())+getValueOrDefault(response.getTotalNum()));

                    // 累加佣金金额（处理 sum 和 response 的空值）
                    sum.setSummerCommission(getValueOrDefault(sum.getSummerCommission()).add(getValueOrDefault(response.getSummerCommission())));
                    sum.setAutumnCommission(getValueOrDefault(sum.getAutumnCommission()).add(getValueOrDefault(response.getAutumnCommission())));
                    sum.setWinterCommission(getValueOrDefault(sum.getWinterCommission()).add(getValueOrDefault(response.getWinterCommission())));
                    // 重新计算总佣金
                    sum.setTotalCommission(getValueOrDefault(sum.getTotalCommission()).add(getValueOrDefault(response.getTotalCommission())));

                    return sum;
                });
        CrmSchooluUniformSalesDataResponse crmSchooluUniformSalesDataResponse = new CrmSchooluUniformSalesDataResponse();
        BeanUtil.copyProperties(reduceResponse, crmSchooluUniformSalesDataResponse);
        return crmSchooluUniformSalesDataResponse;
    }

    public CrmSchooluUniformSalesDataResponse sumSaleAmountResponses(List<GetSchooluUniformSalesDataResponse> responseList) {
        if (responseList== null || responseList.isEmpty()){
            return new CrmSchooluUniformSalesDataResponse();
        }
        // 使用 reduce 进行累加，提供初始值
        GetSchooluUniformSalesDataResponse reduceResponse = responseList.stream()
                .reduce(new GetSchooluUniformSalesDataResponse(), (sum, response) -> {
                    // 累加数量（处理空值）
                    // 累加数量（处理 sum 和 response 的空值）
                    sum.setSummerNum(getValueOrDefault(sum.getSummerNum()) + getValueOrDefault(response.getSummerNum()));
                    sum.setAutumnNum(getValueOrDefault(sum.getAutumnNum()) + getValueOrDefault(response.getAutumnNum()));
                    sum.setWinterNum(getValueOrDefault(sum.getWinterNum()) + getValueOrDefault(response.getWinterNum()));
                    // 重新计算总数量
                    sum.setTotalNum(getValueOrDefault(sum.getTotalNum())+getValueOrDefault(response.getTotalNum()));

                    // 累加销售营业额金额（处理 sum 和 response 的空值
                    sum.setSummerSaleAmount(getValueOrDefault(sum.getSummerSaleAmount()).add(getValueOrDefault(response.getSummerSaleAmount())));
                    sum.setAutumnSaleAmount(getValueOrDefault(sum.getAutumnSaleAmount()).add(getValueOrDefault(response.getAutumnSaleAmount())));
                    sum.setWinterSaleAmount(getValueOrDefault(sum.getWinterSaleAmount()).add(getValueOrDefault(response.getWinterSaleAmount())));
                    // 重新计算总佣金
                    sum.setTotalSaleAmount(getValueOrDefault(sum.getTotalSaleAmount()).add(getValueOrDefault(response.getTotalSaleAmount())));

                    return sum;
                });
        //整体数据为销售营业额,未了不让前端改, 这里放入佣金字段
        reduceResponse.setSummerCommission(reduceResponse.getSummerSaleAmount());
        reduceResponse.setAutumnCommission(reduceResponse.getAutumnSaleAmount());
        reduceResponse.setWinterCommission(reduceResponse.getWinterSaleAmount());
        reduceResponse.setTotalCommission(reduceResponse.getTotalSaleAmount());
        CrmSchooluUniformSalesDataResponse crmSchooluUniformSalesDataResponse = new CrmSchooluUniformSalesDataResponse();
        BeanUtil.copyProperties(reduceResponse, crmSchooluUniformSalesDataResponse);
        return crmSchooluUniformSalesDataResponse;
    }


    // 筛选出 agentUniqueCode 与 codes 有交集的 GetSchooluUniformSalesDataResponse 列表
    private List<GetSchooluUniformSalesDataResponse> filterByAgentUniqueCodes(
            List<GetSchooluUniformSalesDataResponse> responseList,
            List<String> codes) {

        if (responseList == null || responseList.isEmpty() || codes == null || codes.isEmpty()) {
            return new ArrayList<>();
        }

        // 将 codes 转换为 Set 以提高查找效率
        Set<String> codesSet = new HashSet<>(codes);

        return responseList.stream()
                .filter(response -> response.getAgentUniqueCode() != null)
                .filter(response -> codesSet.contains(response.getAgentUniqueCode()))
                .collect(Collectors.toList());
    }


    // 获取Integer值的默认方法
    private Long getValueOrDefault(Long value) {
        return value != null ? value : 0l;
    }

    // 获取BigDecimal值的默认方法
    private BigDecimal getValueOrDefault(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

}
