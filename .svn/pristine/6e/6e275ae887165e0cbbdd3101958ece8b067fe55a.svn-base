package com.wanmi.sbc.funds;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailExportRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailPageRequest;
import com.wanmi.sbc.account.bean.vo.CustomerFundsDetailVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.bean.enums.TabType;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 余额明细bff
 * @author: Geek Wang
 * @createDate: 2019/2/20 15:29
 * @version: 1.0
 */
@RestController("bossCustomerFundsDetailController")
@Validated
@RequestMapping("/funds-detail")
@Tag(name = "CustomerFundsDetailController", description = "S2B 平台端-余额明细API")
@Slf4j
public class CustomerFundsDetailController {

    @Autowired
    private CustomerFundsDetailQueryProvider customerFundsDetailQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 获取余额明细分页列表
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-获取余额明细分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<CustomerFundsDetailVO>> page(@RequestBody CustomerFundsDetailPageRequest request) {
        return BaseResponse.success(customerFundsDetailQueryProvider.page(request).getContext().getMicroServicePage());
    }



    
    /**
     * 导出分销员佣金明细/会员资金明细
     * @param encrypted
     */
    @Operation(summary = "导出分销员佣金明细/会员资金明细")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        CustomerFundsDetailExportRequest queryReq = JSON.parseObject(decrypted, CustomerFundsDetailExportRequest.class);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.BUSINESS_CUSTOMER_FUNDS_DETAIL);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);

        if (queryReq.getTabType().equals(TabType.COMMISSION.toValue())) {
            operateLogMQUtil.convertAndSend("财务", "批量导出分销员佣金明细", "批量导出分销员佣金明细");
        } else {
            operateLogMQUtil.convertAndSend("财务", "批量导出会员资金明细", "批量导出会员资金明细");
        }
        return BaseResponse.SUCCESSFUL();
    }
}
