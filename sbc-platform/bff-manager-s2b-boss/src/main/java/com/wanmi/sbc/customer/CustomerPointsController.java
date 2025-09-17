package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailPageRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailQueryRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsStatisticsQueryRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsDetailPageResponse;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsStatisticsResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerPointsAdjustDTO;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailForPageVO;
import com.wanmi.sbc.customer.bean.vo.CustomerPointsDetailVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.customer.service.CustomerPointsExcelService;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodePageRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodePageResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name =  "会员积分API", description =  "BossCustomerPointsController")
@Slf4j
@RestController
@Validated
@RequestMapping(value = "/customer/points")
public class CustomerPointsController {

    @Autowired
    private CustomerPointsDetailQueryProvider customerPointsDetailQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private GoodsExcelProvider goodsExcelProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerPointsExcelService customerPointsExcelService;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Value("classpath:/download/customer_points_import_template.xlsx")
    private Resource templateFile;


    /**
     * 分页查询会员积分列表
     *
     * @param customerDetailQueryRequest
     * @return 会员信息
     */
    @Operation(summary = "分页查询会员积分列表")
    @EmployeeCheck
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_customer_points_page_sign_word")
    public ResponseEntity<BaseResponse> page(@RequestBody CustomerDetailPageRequest customerDetailQueryRequest) {
        customerDetailQueryRequest.putSort("customer.pointsAvailable", SortType.DESC.toValue());
        customerDetailQueryRequest.putSort("customer.customerId", SortType.DESC.toValue());
        customerDetailQueryRequest.setDefaultFlag(DefaultFlag.YES);
        return ResponseEntity.ok(customerQueryProvider.page(customerDetailQueryRequest));
    }

    @Operation(summary = "分页查询会员积分增减记录")
    @EmployeeCheck
    @RequestMapping(value = "/pageDetail", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_points_page_detail_sign_word")
    public BaseResponse<CustomerPointsDetailPageResponse> pageDetail(@RequestBody @Valid CustomerPointsDetailQueryRequest request) {
        if (CollectionUtils.isNotEmpty(request.getEmployeeIds())) {
            List<String> customerIdList = customerQueryProvider.page(CustomerDetailPageRequest.builder().employeeIds(request.getEmployeeIds()).build())
                    .getContext()
                    .getDetailResponseList()
                    .stream()
                    .map(CustomerDetailForPageVO::getCustomerId)
                    .collect(Collectors.toList());
            request.setCustomerIdList(customerIdList);
        }
        BaseResponse<CustomerPointsDetailPageResponse> page = customerPointsDetailQueryProvider.page(request);
        List<String> customerIds = page.getContext().getCustomerPointsDetailVOPage().getContent()
                .stream()
                .map(CustomerPointsDetailVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContext().getCustomerPointsDetailVOPage().getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return page;
    }

    @Operation(summary = "查询积分历史累计发放及使用")
    @EmployeeCheck
    @RequestMapping(value = "/queryIssueStatistics", method = RequestMethod.POST)
    public BaseResponse<CustomerPointsStatisticsResponse> queryIssueStatistics(@RequestBody CustomerPointsStatisticsQueryRequest request) {
        if (CollectionUtils.isNotEmpty(request.getEmployeeIds())) {
            List<String> customerIdList = customerQueryProvider.page(CustomerDetailPageRequest.builder().employeeIds(request.getEmployeeIds()).build())
                    .getContext()
                    .getDetailResponseList()
                    .stream()
                    .map(item -> item.getCustomerId())
                    .collect(Collectors.toList());
            //业务员没有关联的会员直接返回：0
            if (CollectionUtils.isEmpty(customerIdList)) {
                return BaseResponse.success(CustomerPointsStatisticsResponse.builder().pointsAvailableStatictics(0L).pointsIssueStatictics(0L).build());
            }
            request.setCustomerIdList(customerIdList);
        }
        return customerPointsDetailQueryProvider.queryIssueStatistics(request);
    }

    /**
     * 会员积分列表导出
     *
     * @return
     */
    @Operation(summary = "会员积分列表导出")
    @EmployeeCheck
    @RequestMapping(value = "/export/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportDataStatictics(CustomerDetailPageRequest req,  @PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        CustomerDetailPageRequest customerDetailQueryRequest = JSON.parseObject(decrypted, CustomerDetailPageRequest.class);
        customerDetailQueryRequest.setEmployeeIds(req.getEmployeeIds());

        ExportDataRequest request = new ExportDataRequest();
        request.setTypeCd(ReportType.BUSINESS_CUSTOMER_POINTS);
        request.setParam(JSONObject.toJSONString(customerDetailQueryRequest));
        exportCenter.sendExport(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量调整会员积分
     */
    @Operation(summary = "批量调整会员积分")
    @RequestMapping(value = "/batch/adjust", method = RequestMethod.POST)
    public BaseResponse adjustPoints(@RequestBody CustomerPointsBatchAdjustRequest request) {

        //如果是批量增加积分
        List<CustomerPointsAdjustDTO> addAdjustList = request.getPointsAdjustDTOList().stream()
                .filter(adjustDTO -> OperateType.GROWTH.equals(adjustDTO.getOperateType()))
                .collect(Collectors.toList());
        if (addAdjustList.size() > 50) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010162);
        }
        if(CollectionUtils.isNotEmpty(addAdjustList)){
            customerPointsDetailSaveProvider.batchAdd(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(addAdjustList).build());
        }

        //如果是批量减少会员积分
        List<CustomerPointsAdjustDTO> deductAdjustList = request.getPointsAdjustDTOList().stream()
                .filter(adjustDTO -> OperateType.DEDUCT.equals(adjustDTO.getOperateType()))
                .collect(Collectors.toList());
        if (deductAdjustList.size() > 50) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010162);
        }
        if(CollectionUtils.isNotEmpty(deductAdjustList)){
            customerPointsDetailSaveProvider.batchReduce(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(deductAdjustList).build());
        }

        //如果是批量覆盖积分
        List<CustomerPointsAdjustDTO> replaceAdjustList = request.getPointsAdjustDTOList().stream()
                .filter(adjustDTO -> OperateType.REPLACE.equals(adjustDTO.getOperateType()))
                .collect(Collectors.toList());
        if (replaceAdjustList.size() > 50) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010162);
        }
        if(CollectionUtils.isNotEmpty(replaceAdjustList)){
            customerPointsDetailSaveProvider.batchCover(CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(replaceAdjustList).build());
        }
        
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 下载批量修改客户积分模板
     */
    @Operation(summary = "下载批量修改客户积分模板")
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
    public void export() {
        if (templateFile == null || !templateFile.exists()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }
        InputStream is = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Workbook wk = null;
        try {
            is = templateFile.getInputStream();
            wk = WorkbookFactory.create(is);
            wk.write(byteArrayOutputStream);
            String file = Base64.getMimeEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            if (StringUtils.isNotBlank(file)) {
                String fileName = URLEncoder.encode("客户积分导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getMimeDecoder().decode(file));
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                log.error("客户积分导入模板转Base64位异常", e);
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("读取会员导入模板异常", e);
                }
            }
            try {
                if(wk != null){
                    wk.close();
                }
            }catch (IOException e){
                log.error("会员导入模板下载关闭Workbook异常", e);
            }

        }
    }

    /**
     * 上传客户积分修改文件
     */
    @Operation(summary = "上传客户积分修改文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> uploadForCustomerPoints(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(customerPointsExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 确认导入客户积分修改
     */
    @Operation(summary = "确认导入客户积分修改")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implCustomerPoints(@PathVariable String ext) {
        if(!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerPointsExcelService.implGoods(commonUtil.getOperatorId());

        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @RequestMapping(value = "/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        this.downloadErrExcel(commonUtil.getOperatorId(), ext);
    }

    /**
     * 下载Excel错误文档
     *
     * @param userId 用户Id
     * @param ext    文件扩展名
     */
    private void downloadErrExcel(String userId, String ext) {
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.CUSTOMER_POINTS_ERR_EXCEL_DIR.concat(userId))
                .build()).getContext();
        if (yunGetResourceResponse == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        byte[] content = yunGetResourceResponse.getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try (
                InputStream is = new ByteArrayInputStream(content);
                ServletOutputStream os = HttpUtil.getResponse().getOutputStream()
        ) {
            //下载错误文档时强制清除页面文档缓存
            HttpServletResponse response = HttpUtil.getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("expries", -1);
            String fileName = URLEncoder.encode("错误表格.".concat(ext), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition",
                    String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));

            byte b[] = new byte[1024];
            //读取文件，存入字节数组b，返回读取到的字符数，存入read,默认每次将b数组装满
            int read = is.read(b);
            while (read != -1) {
                os.write(b, 0, read);
                read = is.read(b);
            }
            HttpUtil.getResponse().flushBuffer();
        } catch (Exception e) {
            log.error("下载EXCEL文件异常->", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

    }

}
