package com.wanmi.sbc.distribution;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordExportRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
 * 分销记录导出接口
 * Created by of2975 on 2019/4/14.
 */
@Tag(name =  "分销记录导出API", description =  "BossDistributionRecordExportController")
@Slf4j
@RestController
@Validated
@RequestMapping(value = "/distribution/record")
public class DistributionRecordExportController {

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    /**
     * 导出分销记录
     * @param encrypted
     */
    @Operation(summary = "导出分销记录")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        DistributionRecordExportRequest queryReq = JSON.parseObject(decrypted, DistributionRecordExportRequest.class);
        // 商家端
        queryReq.setStoreId(commonUtil.getStoreId());

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(queryReq));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_DISTRIBUTION_RECORD);
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("营销", "批量导出分销记录", "批量导出分销记录");

        return BaseResponse.SUCCESSFUL();
    }

}

