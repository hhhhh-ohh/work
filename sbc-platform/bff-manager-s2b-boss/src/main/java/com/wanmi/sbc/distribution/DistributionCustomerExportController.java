package com.wanmi.sbc.distribution;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerExportRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 分销员导出接口
 * Created by of2975 on 2019/4/13.
 */
@Tag(name =  "分销员导出API", description =  "BossDistributionCustomerExportController")
@Slf4j
@RestController
@Validated
@RequestMapping(value = "/distribution/customer")
public class DistributionCustomerExportController {

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExportCenter exportCenter;

    /**
     * 导出分销员
     * @param encrypted
     */
    @Operation(summary = "导出分销员")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {

        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        DistributionCustomerExportRequest queryReq = JSON.parseObject(decrypted, DistributionCustomerExportRequest.class);
        // 具备分销员资格
        queryReq.setDistributorFlag(DefaultFlag.YES);
        EsDistributionCustomerPageRequest pageRequest = EsDistributionCustomerPageRequest.builder().build();
        BeanUtils.copyProperties(queryReq,pageRequest);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(pageRequest));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_DISTRIBUTION_CUSTOMER);
        exportCenter.sendExport(exportDataRequest);

        operateLogMQUtil.convertAndSend("营销", "批量导出分销员", "批量导出分销员");
        return BaseResponse.SUCCESSFUL();
    }



}

