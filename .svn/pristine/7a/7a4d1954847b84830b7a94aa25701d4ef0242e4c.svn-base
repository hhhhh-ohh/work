package com.wanmi.sbc.message.smssetting;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.empower.api.provider.sms.SmsSettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.sms.SmsSettingSaveProvider;
import com.wanmi.sbc.empower.api.request.sms.*;
import com.wanmi.sbc.empower.api.response.sms.*;
import com.wanmi.sbc.empower.bean.vo.SmsSettingVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;


@Tag(name =  "短信配置管理API", description =  "SmsSettingController")
@RestController
@Validated
@RequestMapping(value = "/smssetting")
public class SmsSettingController {

    @Autowired
    private SmsSettingQueryProvider smsSettingQueryProvider;

    @Autowired
    private SmsSettingSaveProvider smsSettingSaveProvider;

    @Operation(summary = "分页查询短信配置")
    @PostMapping("/page")
    public BaseResponse<SmsSettingPageResponse> getPage(@RequestBody @Valid SmsSettingPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return smsSettingQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询短信配置")
    @PostMapping("/list")
    public BaseResponse<SmsSettingListResponse> getList(@RequestBody @Valid SmsSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return smsSettingQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询短信配置")
    @GetMapping("/{id}")
    public BaseResponse<SmsSettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        SmsSettingByIdRequest idReq = new SmsSettingByIdRequest();
        idReq.setId(id);
        return smsSettingQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增短信配置")
    @PostMapping("/add")
    public BaseResponse<SmsSettingAddResponse> add(@RequestBody @Valid SmsSettingAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        return smsSettingSaveProvider.add(addReq);
    }

    @Operation(summary = "修改短信配置")
    @PutMapping("/modify")
    public BaseResponse<SmsSettingModifyResponse> modify(@RequestBody @Valid SmsSettingModifyRequest modifyReq) {
        return smsSettingSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除短信配置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        SmsSettingDelByIdRequest delByIdReq = new SmsSettingDelByIdRequest();
        delByIdReq.setId(id);
        return smsSettingSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除短信配置")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid SmsSettingDelByIdListRequest delByIdListReq) {
        return smsSettingSaveProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出短信配置列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        SmsSettingListRequest listReq = JSON.parseObject(decrypted, SmsSettingListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        List<SmsSettingVO> dataRecords = smsSettingQueryProvider.list(listReq).getContext().getSmsSettingVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("短信配置列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            exportDataList(dataRecords, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 导出列表数据具体实现
     */
    private void exportDataList(List<SmsSettingVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("调用api参数key", new SpelColumnRender<SmsSettingVO>("accessKeyId")),
            new Column("调用api参数secret", new SpelColumnRender<SmsSettingVO>("accessKeySecret")),
            new Column("短信平台类型：0：阿里云短信平台", new SpelColumnRender<SmsSettingVO>("type")),
            new Column("是否启用：0：未启用；1：启用", new SpelColumnRender<SmsSettingVO>("status")),
            new Column("创建时间", new SpelColumnRender<SmsSettingVO>("createTime"))
        };
        excelHelper.addSheet("短信配置列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
