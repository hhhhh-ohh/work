package com.wanmi.sbc.crm.autotag;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagProvider;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagQueryProvider;
import com.wanmi.sbc.crm.api.provider.autotagOther.AutoTagOtherQueryProvider;
import com.wanmi.sbc.crm.api.request.autotag.*;
import com.wanmi.sbc.crm.api.request.autotagother.AutoTagOtherPageRequest;
import com.wanmi.sbc.crm.api.response.autotag.*;
import com.wanmi.sbc.crm.api.response.autotagother.AutotagOtherPageResponse;
import com.wanmi.sbc.crm.bean.vo.AutoTagVO;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.util.CommonUtil;


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
import java.util.*;


@Tag(name =  "自动标签管理API", description =  "AutoTagController")
@RestController
@Validated
@RequestMapping(value = "/autotag")
public class AutoTagController {

    @Autowired
    private AutoTagQueryProvider autoTagQueryProvider;

    @Autowired
    private AutoTagProvider autoTagProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AutoTagOtherQueryProvider autoTagOtherQueryProvider;

    @Operation(summary = "分页查询自动标签")
    @PostMapping("/page")
    public BaseResponse<AutotagOtherPageResponse> getPage(@RequestBody @Valid AutoTagOtherPageRequest pageReq) {
       return autoTagOtherQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询自动标签")
    @PostMapping("/list")
    public BaseResponse<AutoTagListResponse> getList(@RequestBody @Valid AutoTagListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return autoTagQueryProvider.list(listReq);
    }

    @Operation(summary = "列表查询自动标签")
    @PostMapping("/preferenceList")
    public BaseResponse<PreferenceTagListResponse> getPreferenceList(@RequestBody @Valid AutoTagListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return autoTagQueryProvider.getPreferenceList(listReq);
    }

    @Operation(summary = "自动标签总数")
    @GetMapping("/count")
    public BaseResponse<Long> getCount() {
        return autoTagQueryProvider.getCount();
    }

    @Operation(summary = "根据id查询自动标签")
    @GetMapping("/{id}")
    public BaseResponse<AutoTagByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AutoTagByIdRequest idReq = new AutoTagByIdRequest();
        idReq.setId(id);
        BaseResponse<AutoTagByIdResponse> response = autoTagQueryProvider.getById(idReq);
//        fillInfoName(response.getContext().getAutoTagVO());
        return response;
    }

    @Operation(summary = "新增自动标签")
    @PostMapping("/add")
    @MultiSubmit
    public BaseResponse<AutoTagAddResponse> add(@RequestBody @Valid AutoTagAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return autoTagProvider.add(addReq);
    }

    @Operation(summary = "修改自动标签")
    @PutMapping("/modify")
    @MultiSubmit
    public BaseResponse<AutoTagModifyResponse> modify(@RequestBody @Valid AutoTagModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return autoTagProvider.modify(modifyReq);
    }

    @Operation(summary = "同步标签")
    @PostMapping("/init")
    public BaseResponse init(@RequestBody @Valid AutoTagInitRequest request) {
        request.setCreatePerson(commonUtil.getOperatorId());
        return autoTagProvider.init(request);
    }

    @Operation(summary = "系统标签列表")
    @PostMapping("/system-list")
    public BaseResponse<AutoTagInitListResponse> systemList() {
        return autoTagQueryProvider.systemList();
    }

    @Operation(summary = "根据id删除自动标签")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AutoTagDelByIdRequest delByIdReq = new AutoTagDelByIdRequest();
        delByIdReq.setId(id);
        return autoTagProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除自动标签")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid AutoTagDelByIdListRequest delByIdListReq) {
        return autoTagProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出自动标签列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        AutoTagListRequest listReq = JSON.parseObject(decrypted, AutoTagListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        List<AutoTagVO> dataRecords = autoTagQueryProvider.list(listReq).getContext().getAutoTagVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("自动标签列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<AutoTagVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("自动标签名称", new SpelColumnRender<AutoTagVO>("tagName")),
            new Column("会员人数", new SpelColumnRender<AutoTagVO>("customerCount")),
            new Column("标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签", new SpelColumnRender<AutoTagVO>("type")),
            new Column("一级维度且或关系，0：且，1：或", new SpelColumnRender<AutoTagVO>("relationType"))
        };
        excelHelper.addSheet("自动标签列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
