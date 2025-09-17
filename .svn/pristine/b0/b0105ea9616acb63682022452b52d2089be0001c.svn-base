package com.wanmi.sbc.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaQueryProvider;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaListRequest;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaListResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.setting.bean.vo.StoreExpressCompanyRelaVO;
import com.wanmi.sbc.trade.request.BatchDeliverCheckRequest;
import com.wanmi.sbc.trade.service.TradeBatchDeliverService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author 10486
 * @Date 15:37 2020/10/20
 **/
@Tag(name = "BatchDeliverController", description = "批量发货 Api")
@RestController
@Validated
@RequestMapping("/deliver")
@Slf4j
public class BatchDeliverController {

    @Autowired
    private CommonUtil commonUtil;

    @Value("classpath:/download/batch_deliver.xlsx")
    private Resource templateFile;

    @Value("classpath:/download/batch_deliver_provider.xlsx")
    private Resource templateFileForProvider;

    @Autowired
    private StoreExpressCompanyRelaQueryProvider storeExpressCompanyRelaQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private TradeBatchDeliverService tradeBatchDeliverService;

    /**
     * 下载模板
     */
    @Operation(summary = "下载模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        Resource file ;
        if (Platform.SUPPLIER == commonUtil.getOperator().getPlatform() || Platform.STOREFRONT == commonUtil.getOperator().getPlatform()){
            file = templateFile;
        }else {
            file = templateFileForProvider;
        }
        //获取店铺物流设置
        StoreExpressCompanyRelaListRequest queryRopRequest = new StoreExpressCompanyRelaListRequest();
        queryRopRequest.setStoreId(commonUtil.getStoreId());
        StoreExpressCompanyRelaListResponse response = storeExpressCompanyRelaQueryProvider.list(queryRopRequest).getContext();
        List<StoreExpressCompanyRelaVO> storeExpressCompanyRelaVOList;
        if(Objects.nonNull(response)){
            storeExpressCompanyRelaVOList = response.getStoreExpressCompanyRelaVOList();
        }else{
            storeExpressCompanyRelaVOList = new ArrayList<>();
        }
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream is = file.getInputStream();
            Workbook wk = WorkbookFactory.create(is)){
            Sheet expressCompanySheet = wk.getSheetAt(1);
            List<ExpressCompanyVO> expressCompanyVOList = new ArrayList<>();
            storeExpressCompanyRelaVOList.forEach(v -> {
               if (v.getExpressCompany().getDelFlag() == DeleteFlag.NO){
                   expressCompanyVOList.add(v.getExpressCompany());
               }
            });
            for(int i = 0; i < expressCompanyVOList.size(); i++){
                expressCompanySheet.createRow(i).createCell(0).setCellValue(expressCompanyVOList.get(i).getExpressName());
            }
            wk.write(outputStream);
            String fileName = URLEncoder.encode("批量发货导入模板.xlsx", StandardCharsets.UTF_8.name());
            HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            HttpUtil.getResponse().getOutputStream().write(outputStream.toByteArray());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        BatchDeliverCheckRequest checkRequest = new BatchDeliverCheckRequest();
        checkRequest.setUserId(commonUtil.getOperatorId());
        checkRequest.setStoreId(commonUtil.getStoreId());
        checkRequest.setPlatform(commonUtil.getOperator().getPlatform());
        return BaseResponse.success(tradeBatchDeliverService.upload(uploadFile, checkRequest));
    }

    /**
     * 确认导入批量发货模板
     */
    @Operation(summary = "确认导入批量发货模板")
    @Parameter(name = "ext", description= "后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse batchDeliver(@PathVariable String ext) {
        if(!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if(companyInfo == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        BatchDeliverCheckRequest checkRequest = new BatchDeliverCheckRequest();
        checkRequest.setExt(ext);
        checkRequest.setUserId(commonUtil.getOperatorId());
        checkRequest.setStoreId(commonUtil.getStoreId());
        checkRequest.setPlatform(commonUtil.getOperator().getPlatform());
        tradeBatchDeliverService.checkExcelForBatchDeliver(checkRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "下载错误模板")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @RequestMapping(value = "/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        tradeBatchDeliverService.downErrExcel(commonUtil.getOperatorId(), ext);
    }
}
