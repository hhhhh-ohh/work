package com.wanmi.sbc.funds;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.constant.AccountRedisKey;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsPageRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsStatisticsResponse;
import com.wanmi.sbc.account.bean.vo.CustomerFundsVO;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
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
 * 会员资金bff
 * @author: Geek Wang
 * @createDate: 2019/2/20 15:29
 * @version: 1.0
 */
@RestController("bossCustomerFundsController")
@Validated
@RequestMapping("/funds")
@Tag(name = "CustomerFundsController", description = "S2B 平台端-会员资金API")
@Slf4j
public class CustomerFundsController {

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 获取会员资金统计（会员余额总额、冻结余额总额、可提现余额总额）
     * @return
     */
    @Operation(summary = "S2B 平台端-获取会员资金统计（会员余额总额、冻结余额总额、可提现余额总额）")
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public BaseResponse<CustomerFundsStatisticsResponse> statistics() {
        String accountBalanceTotal = Objects.toString(redisService.getValueByKey(AccountRedisKey.ACCOUNT_BALANCE_TOTAL),"0.00");
        String blockedBalanceTotal = Objects.toString(redisService.getValueByKey(AccountRedisKey.BLOCKED_BALANCE_TOTAL),"0.00");
        String withdrawAmountTotal = Objects.toString(redisService.getValueByKey(AccountRedisKey.WITHDRAW_AMOUNT_TOTAL),"0.00");
        return BaseResponse.success(new CustomerFundsStatisticsResponse(BigDecimal.valueOf(Double.parseDouble(accountBalanceTotal)),BigDecimal.valueOf(Double.parseDouble(blockedBalanceTotal)),BigDecimal.valueOf(Double.parseDouble(withdrawAmountTotal))));
    }

    /**
     * 根据会员ID查询会员资金信息
     * @param customerId
     * @return
     */
    @Operation(summary = "S2B 平台端-根据会员ID查询会员资金信息")
    @Parameter(name = "customerId", description = "会员id", required = true)
    @RequestMapping(value = "/statistics/{customerId}", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_get_by_id-sign_word")
    public BaseResponse<CustomerFundsByCustomerIdResponse> getById(@PathVariable String customerId) {
      CustomerFundsByCustomerIdResponse context = customerFundsQueryProvider
              .getByCustomerId(new CustomerFundsByCustomerIdRequest(customerId)).getContext();

      //获取用户注销状态
      context.setLogOutStatus(customerCacheService.getCustomerLogOutStatus(context.getCustomerId()));

      return BaseResponse.success(context);
    }

    /**
     * 获取会员资金分页列表
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-获取会员资金分页列表")
	@EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_funds_page_sign_word")
    public BaseResponse<MicroServicePage<CustomerFundsVO>> page(@RequestBody CustomerFundsPageRequest request) {
        MicroServicePage<CustomerFundsVO> microServicePage = customerFundsQueryProvider.page(request).getContext().getMicroServicePage();

        //获取用户注销状态
        List<String> customerIds = microServicePage.getContent()
                .stream()
                .map(CustomerFundsVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        microServicePage.getContent().forEach(page -> {
          page.setLogOutStatus(map.get(page.getCustomerId()));
        });

        return BaseResponse.success(microServicePage);
    }

    /**
     * 导出会员资金记录
     * @param encrypted
     */
    @Operation(summary = "导出会员资金记录")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted, CustomerFundsPageRequest request) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        //业务员会员id
		if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
			JSONObject jsonObject = JSONObject.parseObject(decrypted);
			jsonObject.put("employeeCustomerIds", request.getEmployeeCustomerIds());
			decrypted = jsonObject.toString();
		}

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.BUSINESS_CUSTOMER_FUNDS);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("财务", "批量导出会员资金", "");
        return BaseResponse.SUCCESSFUL();
    }


}
