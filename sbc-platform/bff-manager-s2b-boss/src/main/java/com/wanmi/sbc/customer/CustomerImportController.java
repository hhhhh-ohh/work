package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.request.CustomerImportExcelRequest;
import com.wanmi.sbc.customer.response.CustomerImportExcelResponse;
import com.wanmi.sbc.customer.service.CustomerImportExcelService;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * 会员导入
 */
@Tag(name =  "会员导入API", description =  "CustomerImportController")
@RestController
@Validated
@RequestMapping("/customer/customerImport")
@Slf4j
public class CustomerImportController {

    @Value("classpath:/download/customer_import_template.xls")
    private Resource templateFile;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CustomerImportExcelService customerImportExcelService;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    /**
     * 会员导入模板下载
     *
     */
    @Operation(summary = "会员导入模板下载")
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
                String fileName = URLEncoder.encode("会员导入模板.xls", StandardCharsets.UTF_8.name());
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
                log.error("会员导入模板转Base64位异常", e);
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
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {

        String ext = customerImportExcelService.upload(uploadFile, commonUtil.getOperatorId());
        return BaseResponse.success(ext);
    }

    /**
     * 确认导入会员
     *
     * @param ext 文件格式 {@link String}
     * @return
     */
    @Operation(summary = "确认导入会员")
    @Parameter(name = "ext", description = "文件名后缀", required = true)
    @RequestMapping(value = "/import/{ext}/{sendMsgFlag}", method = RequestMethod.GET)
    public BaseResponse<CustomerImportExcelResponse> importCustomer(@PathVariable String ext, @PathVariable Boolean sendMsgFlag) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        CustomerImportExcelRequest excelRequest = new CustomerImportExcelRequest();
        excelRequest.setExt(ext);
        excelRequest.setSendMsgFlag(sendMsgFlag);
        excelRequest.setUserId(commonUtil.getOperatorId());
        CustomerImportExcelResponse response = customerImportExcelService.importCustomer(excelRequest);
        //操作日志记录
        operateLogMQUtil.convertAndSend("会员导入", "批量导入", "批量导入");
        return BaseResponse.success(response);
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
                .resourceKey(Constants.CUSTOMER_IMPORT_EXCEL_ERR_DIR.concat(userId))
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
