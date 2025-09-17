package com.wanmi.sbc.ares;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.provider.ExportDataServiceProvider;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.ares.view.export.ExportDataResponse;
import com.wanmi.ares.view.export.ExportDataView;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.configure.ThriftClientConfig;
import com.wanmi.sbc.message.bean.enums.MessageErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * 导出任务要求控制器
 * Author: bail
 * Time: 2017/11/2.16:16
 */
@Tag(name = "ExportDataController", description = "导出任务要求 Api")
@RestController
@Validated
@RequestMapping("/export")
@Slf4j
@EnableConfigurationProperties(ThriftClientConfig.class)
public class ExportDataController {
    @Autowired
    private ExportDataServiceProvider exportDataServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 发送导出任务请求
     *
     * @param expRequest
     * @return
     */
    @Operation(summary = "发送导出任务请求")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public BaseResponse<ExportDataView> send(@RequestBody ExportDataRequest expRequest) {
        Long storeId = commonUtil.getStoreId();
        Long companyInfoId = commonUtil.getCompanyInfoId();
        Platform platform = commonUtil.getOperator().getPlatform();
        if(expRequest.getStoreId() == null
            && (expRequest.getTypeCd() == ReportType.COUPON_INFO_EFFECT
                || expRequest.getTypeCd() == ReportType.COUPON_ACTIVITY_EFFECT
                || expRequest.getTypeCd() == ReportType.COUPON_INFO_EFFECT_ACTIVITY)){
            if(storeId==null){
                expRequest.setStoreId(-1L);
            }else {
                expRequest.setStoreId(storeId);
            }
        }
        if(expRequest.getCompanyInfoId() == null
                && (expRequest.getTypeCd() == ReportType.COUPON_INFO_EFFECT
                || expRequest.getTypeCd() == ReportType.COUPON_ACTIVITY_EFFECT
                || expRequest.getTypeCd() == ReportType.COUPON_INFO_EFFECT_ACTIVITY)){
            if(companyInfoId == null){
                expRequest.setCompanyInfoId(0L);
            }else{
                expRequest.setCompanyInfoId(companyInfoId);
            }
        }
        if (Platform.SUPPLIER == platform || Platform.STOREFRONT == platform){
            expRequest.setStoreId(storeId);
            expRequest.setCompanyInfoId(companyInfoId);
        }
        //平台自营,客户统计,查询内容同boss
        if (platform == Platform.SUPPLIER && BoolFlag.NO.equals(commonUtil.getCompanyType())
                && (expRequest.getTypeCd() == ReportType.CUSTOMER_GROW
                || expRequest.getTypeCd() == ReportType.CUSTOMER_TRADE
                || expRequest.getTypeCd() == ReportType.CUSTOMER_LEVEL_TRADE
                || expRequest.getTypeCd() == ReportType.CUSTOMER_AREA_TRADE)){
            expRequest.setStoreId(null);
            expRequest.setCompanyInfoId(null);
        }
        try {
            String employeeId = commonUtil.getOperatorId();
            expRequest.setUserId(employeeId);
            expRequest.setOperator(commonUtil.getOperator());
            ExportDataView response = exportDataServiceProvider.sendExportDataRequest(expRequest);
            return BaseResponse.success(response);
        } catch (Exception e) {
            log.error("Get exportData client view fail,the thrift request error,", e);
            return BaseResponse.FAILED();
        }
    }

    /**
     * 分页查询历史导出任务请求
     *
     * @param expRequest
     * @return
     */
    @Operation(summary = "分页查询历史导出任务请求")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public BaseResponse<BaseQueryResponse<ExportDataView>> query(@RequestBody ExportDataRequest expRequest) {
        Long storeId = commonUtil.getStoreId();
        Long companyInfoId = commonUtil.getCompanyInfoId();
        if(expRequest.getStoreId() == null){
            if(storeId==null){
                expRequest.setStoreId(-1L);
            }else {
                expRequest.setStoreId(storeId);
            }
        }
        if(expRequest.getCompanyInfoId() == null){
            if(companyInfoId == null){
                expRequest.setCompanyInfoId(0L);
            }else{
                expRequest.setCompanyInfoId(companyInfoId);
            }
        }
        try {
            String employeeId = commonUtil.getOperatorId();
            expRequest.setUserId(employeeId);
            ExportDataResponse response = exportDataServiceProvider.queryExportDataRequestPage(expRequest);
            return BaseResponse.success(BaseQueryResponse.<ExportDataView>builder()
                    .total(response.getTotal())
                    .data(response.getViewList())
                    .pageSize(expRequest.getPageSize())
                    .pageNum(expRequest.getPageNum())
                    .build());
        } catch (Exception e) {
            log.error("Get exportData client view fail,the thrift request error,", e);
            return BaseResponse.FAILED();
        }
    }

    /**
     * 删除某个导出任务请求
     *
     * @param expRequest
     * @return
     */
    @Operation(summary = "删除某个导出任务请求")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody ExportDataRequest expRequest) {
        Long storeId = commonUtil.getStoreId();
        Long companyInfoId = commonUtil.getCompanyInfoId();
        if(expRequest.getStoreId() == null){
            if(storeId==null){
                expRequest.setStoreId(-1L);
            }else {
                expRequest.setStoreId(storeId);
            }
        }
        if(expRequest.getCompanyInfoId() == null){
            if(companyInfoId == null){
                expRequest.setCompanyInfoId(0L);
            }else{
                expRequest.setCompanyInfoId(companyInfoId);
            }
        }
        try {
            String employeeId = commonUtil.getOperatorId();
            expRequest.setUserId(employeeId);
            if (exportDataServiceProvider.deleteExportDataRequest(expRequest) > 0) {
                return BaseResponse.SUCCESSFUL();
            } else {
                return new BaseResponse(MessageErrorCodeEnum.K090022);
            }
        } catch (Exception e) {
            log.error("Get exportData client view fail,the thrift request error,", e);
            return BaseResponse.FAILED();
        }
    }
}
