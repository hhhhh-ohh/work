package com.wanmi.sbc.ledgercontent;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.ledgercontent.LedgerContentProvider;
import com.wanmi.sbc.empower.api.provider.ledgercontent.LedgerContentQueryProvider;
import com.wanmi.sbc.empower.api.request.ledgercontent.*;
import com.wanmi.sbc.empower.api.response.ledgercontent.*;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "拉卡拉经营内容表管理API", description =  "LedgerContentController")
@RestController
@Validated
@RequestMapping(value = "/ledgercontent")
public class LedgerContentController {

    @Autowired
    private LedgerContentQueryProvider ledgerContentQueryProvider;

    @Autowired
    private LedgerContentProvider ledgerContentProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Operation(summary = "分页查询拉卡拉经营内容表")
    @PostMapping("/page")
    public BaseResponse<LedgerContentPageResponse> getPage(@RequestBody @Valid LedgerContentPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("contentId", "desc");
        return ledgerContentQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询拉卡拉经营内容表")
    @PostMapping("/list")
    public BaseResponse<LedgerContentListResponse> getList(@RequestBody @Valid LedgerContentListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return ledgerContentQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询拉卡拉经营内容表")
    @GetMapping("/{contentId}")
    public BaseResponse<LedgerContentByIdResponse> getById(@PathVariable Long contentId) {
        if (contentId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LedgerContentByIdRequest idReq = new LedgerContentByIdRequest();
        idReq.setContentId(contentId);
        return ledgerContentQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增拉卡拉经营内容表")
    @PostMapping("/add")
    public BaseResponse<LedgerContentAddResponse> add(@RequestBody @Valid LedgerContentAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return ledgerContentProvider.add(addReq);
    }

    @Operation(summary = "修改拉卡拉经营内容表")
    @PutMapping("/modify")
    public BaseResponse<LedgerContentModifyResponse> modify(@RequestBody @Valid LedgerContentModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return ledgerContentProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除拉卡拉经营内容表")
    @DeleteMapping("/{contentId}")
    public BaseResponse deleteById(@PathVariable Long contentId) {
        if (contentId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LedgerContentDelByIdRequest delByIdReq = new LedgerContentDelByIdRequest();
        delByIdReq.setContentId(contentId);
        return ledgerContentProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除拉卡拉经营内容表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid LedgerContentDelByIdListRequest delByIdListReq) {
        return ledgerContentProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出拉卡拉经营内容表列表")
//    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        LedgerContentExportRequest listReq = JSON.parseObject(decrypted, LedgerContentExportRequest.class);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(listReq));
//FIXME: 需要手动添加ReportType里面的枚举值, 替换掉下面的ReportType.XXXXXXX, 并放开注释, 实现方法的调用
//        exportDataRequest.setTypeCd(ReportType.XXXXXXX);
//        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }


}
