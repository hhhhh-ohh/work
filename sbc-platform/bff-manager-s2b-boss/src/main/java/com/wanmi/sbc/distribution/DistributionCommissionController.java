package com.wanmi.sbc.distribution;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.constant.DistributionCommissionRedisKey;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCommissionStatisticsResponse;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerPageResponse;
import com.wanmi.sbc.elastic.bean.vo.customer.EsDistributionCustomerVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by feitingting on 2019/2/26.
 */
@Tag(name = "DistributionCommissionController", description = "分销员佣金接口")
@Slf4j
@RestController
@Validated
@RequestMapping("/distribution-commission")
public class DistributionCommissionController {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsDistributionCustomerQueryProvider esDistributionCustomerQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Resource
    private CommonUtil commonUtil;

    @Operation(summary = "S2B 平台端-分页获取分销员佣金记录")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_find_distribution_invite_new_record_sign_word")
    public BaseResponse<EsDistributionCustomerPageResponse> findDistributionInviteNewRecord(@RequestBody EsDistributionCustomerPageRequest esDistributionCustomerPageRequest) {
        //默认按创建时间降序
        esDistributionCustomerPageRequest.setDistributorFlag(DefaultFlag.YES);
        BaseResponse<EsDistributionCustomerPageResponse> page = esDistributionCustomerQueryProvider.page(esDistributionCustomerPageRequest);
        //获取用户注销标识
        List<String> customerIds = page.getContext().getDistributionCustomerVOPage().getContent()
                .stream()
                .map(EsDistributionCustomerVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

        page.getContext().getDistributionCustomerVOPage().getContent().forEach(v -> {
            v.setLogOutStatus(map.get(v.getCustomerId()));
        });

        return page;
    }

    @Operation(summary = "S2B 平台端-分销员佣金统计（佣金、分销佣金、邀新奖金、未入账分销佣金、未入账邀新奖金）")
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public BaseResponse<DistributionCommissionStatisticsResponse> statistics() {
        //佣金总额
        String commissionTotal = Objects.toString(redisService.getValueByKey(DistributionCommissionRedisKey.DISTRIBUTION_COMMISSION_TOTAL),"0.00");
        //分销佣金
        String commission = Objects.toString(redisService.getValueByKey(DistributionCommissionRedisKey.DISTRIBUTION_COMMISSION),"0.00");
        //邀新奖金
        String rewardCash = Objects.toString(redisService.getValueByKey(DistributionCommissionRedisKey.DISTRIBUTION_REWARD_CASH),"0.00");
        //未入账分销佣金
        String commissionNotRecorded = Objects.toString(redisService.getValueByKey(DistributionCommissionRedisKey.DISTRIBUTION_COMMISSION_NOTRECORDED),"0.00");
        //未入账邀新奖金
        String rewardCashNotRecorded = Objects.toString(redisService.getValueByKey(DistributionCommissionRedisKey.DISTRIBUTION_REWARD_CASH_NOTRECORDED),"0.00");

        return BaseResponse.success(new DistributionCommissionStatisticsResponse(BigDecimal.valueOf(Double.parseDouble(commissionTotal)),
                BigDecimal.valueOf(Double.parseDouble(commission)),BigDecimal.valueOf(Double.parseDouble(rewardCash)),
                BigDecimal.valueOf(Double.parseDouble(commissionNotRecorded)),BigDecimal.valueOf(Double.parseDouble(rewardCashNotRecorded))));
    }

    /**
     * 导出分销员佣金记录
     * @param encrypted
     */
    @Operation(summary = "导出分销员佣金记录")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted, DistributionCommissionExportRequest request) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        //业务员会员id
        if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
            JSONObject jsonObject = JSONObject.parseObject(decrypted);
            jsonObject.put("employeeCustomerIds", request.getEmployeeCustomerIds());
            decrypted = jsonObject.toString();
        }
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.BUSINESS_DISTRIBUTION_COMMISSION);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("财务", "批量导出分销员佣金", "批量导出分销员佣金");

        return BaseResponse.SUCCESSFUL();
    }
}
