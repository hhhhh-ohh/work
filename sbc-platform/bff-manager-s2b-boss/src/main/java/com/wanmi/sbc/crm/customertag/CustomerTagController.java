package com.wanmi.sbc.crm.customertag;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.crm.api.provider.customertag.CustomerTagQueryProvider;
import com.wanmi.sbc.crm.api.provider.customertag.CustomerTagSaveProvider;
import com.wanmi.sbc.crm.api.request.customertag.*;
import com.wanmi.sbc.crm.api.response.customertag.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import com.wanmi.sbc.crm.bean.vo.CustomerTagVO;
import com.wanmi.sbc.util.CommonUtil;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "会员标签管理API", description =  "CustomerTagController")
@RestController
@Validated
@RequestMapping(value = "/customertag")
public class CustomerTagController {

    @Autowired
    private CustomerTagQueryProvider customerTagQueryProvider;

    @Autowired
    private CustomerTagSaveProvider customerTagSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询会员标签")
    @PostMapping("/page")
    public BaseResponse<CustomerTagPageResponse> getPage(@RequestBody @Valid CustomerTagPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");
        pageReq.putSort("id", "desc");
        return customerTagQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询会员标签")
    @PostMapping("/list")
    public BaseResponse<CustomerTagListResponse> getList(@RequestBody @Valid CustomerTagListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return customerTagQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询会员标签")
    @GetMapping("/{id}")
    public BaseResponse<CustomerTagByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerTagByIdRequest idReq = new CustomerTagByIdRequest();
        idReq.setId(id);
        return customerTagQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增会员标签")
    @PostMapping("/add")
    public BaseResponse<CustomerTagAddResponse> add(@RequestBody @Valid CustomerTagAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return customerTagSaveProvider.add(addReq);
    }

    @Operation(summary = "修改会员标签")
    @PutMapping("/modify")
    public BaseResponse<CustomerTagModifyResponse> modify(@RequestBody @Valid CustomerTagModifyRequest modifyReq) {
        return customerTagSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除会员标签")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerTagDelByIdRequest delByIdReq = new CustomerTagDelByIdRequest();
        delByIdReq.setId(id);
        return customerTagSaveProvider.deleteById(delByIdReq);
    }


    @Operation(summary = "导出会员标签列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        CustomerTagListRequest listReq = JSON.parseObject(decrypted, CustomerTagListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        List<CustomerTagVO> dataRecords = customerTagQueryProvider.list(listReq).getContext().getCustomerTagVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("会员标签列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<CustomerTagVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("标签名称", new SpelColumnRender<CustomerTagVO>("name")),
            new Column("会员人数", new SpelColumnRender<CustomerTagVO>("customerNum"))
        };
        excelHelper.addSheet("会员标签列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
