package com.wanmi.sbc.crm.preferencetagdetail;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.crm.api.provider.autotagpreference.AutotagPreferenceQueryProvider;
import com.wanmi.sbc.crm.api.provider.preferencetagdetail.PreferenceTagDetailProvider;
import com.wanmi.sbc.crm.api.provider.preferencetagdetail.PreferenceTagDetailQueryProvider;
import com.wanmi.sbc.crm.api.request.autotagpreference.AutoPreferencePageRequest;
import com.wanmi.sbc.crm.api.request.preferencetagdetail.*;
import com.wanmi.sbc.crm.api.response.autotagpreference.AutotagPreferencePageResponse;
import com.wanmi.sbc.crm.api.response.preferencetagdetail.PreferenceTagDetailAddResponse;
import com.wanmi.sbc.crm.api.response.preferencetagdetail.PreferenceTagDetailByIdResponse;
import com.wanmi.sbc.crm.api.response.preferencetagdetail.PreferenceTagDetailListResponse;
import com.wanmi.sbc.crm.api.response.preferencetagdetail.PreferenceTagDetailModifyResponse;
import com.wanmi.sbc.crm.bean.vo.PreferenceTagDetailVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;


@Tag(name =  "偏好标签明细管理API", description =  "PreferenceTagDetailController")
@RestController
@Validated
@RequestMapping(value = "/preferencetagdetail")
public class PreferenceTagDetailController {

    @Autowired
    private PreferenceTagDetailQueryProvider preferenceTagDetailQueryProvider;

    @Autowired
    private PreferenceTagDetailProvider preferenceTagDetailProvider;

    @Autowired
    private AutotagPreferenceQueryProvider autotagPreferenceQueryProvider;

    @Operation(summary = "分页查询偏好标签明细")
    @PostMapping("/page")
    public BaseResponse<AutotagPreferencePageResponse> getPage(@RequestBody @Valid AutoPreferencePageRequest pageReq) {
        return autotagPreferenceQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询偏好标签明细")
    @PostMapping("/list")
    public BaseResponse<PreferenceTagDetailListResponse> getList(@RequestBody @Valid PreferenceTagDetailListRequest listReq) {
        return preferenceTagDetailQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询偏好标签明细")
    @GetMapping("/{id}")
    public BaseResponse<PreferenceTagDetailByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PreferenceTagDetailByIdRequest idReq = new PreferenceTagDetailByIdRequest();
        idReq.setId(id);
        return preferenceTagDetailQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增偏好标签明细")
    @PostMapping("/add")
    public BaseResponse<PreferenceTagDetailAddResponse> add(@RequestBody @Valid PreferenceTagDetailAddRequest addReq) {
        return preferenceTagDetailProvider.add(addReq);
    }

    @Operation(summary = "修改偏好标签明细")
    @PutMapping("/modify")
    public BaseResponse<PreferenceTagDetailModifyResponse> modify(@RequestBody @Valid PreferenceTagDetailModifyRequest modifyReq) {
        return preferenceTagDetailProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除偏好标签明细")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PreferenceTagDetailDelByIdRequest delByIdReq = new PreferenceTagDetailDelByIdRequest();
        delByIdReq.setId(id);
        return preferenceTagDetailProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除偏好标签明细")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid PreferenceTagDetailDelByIdListRequest delByIdListReq) {
        return preferenceTagDetailProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出偏好标签明细列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        PreferenceTagDetailListRequest listReq = JSON.parseObject(decrypted, PreferenceTagDetailListRequest.class);
        List<PreferenceTagDetailVO> dataRecords = preferenceTagDetailQueryProvider.list(listReq).getContext().getPreferenceTagDetailVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("偏好标签明细列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<PreferenceTagDetailVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("标签id", new SpelColumnRender<PreferenceTagDetailVO>("tagId")),
            new Column("偏好类标签名称", new SpelColumnRender<PreferenceTagDetailVO>("detailName")),
            new Column("会员人数", new SpelColumnRender<PreferenceTagDetailVO>("customerCount"))
        };
        excelHelper.addSheet("偏好标签明细列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
