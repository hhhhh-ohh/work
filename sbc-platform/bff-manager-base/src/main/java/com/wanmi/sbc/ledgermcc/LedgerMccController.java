package com.wanmi.sbc.ledgermcc;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.ledgermcc.LedgerMccProvider;
import com.wanmi.sbc.empower.api.provider.ledgermcc.LedgerMccQueryProvider;
import com.wanmi.sbc.empower.api.request.ledgermcc.*;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccByIdResponse;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccListResponse;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "拉卡拉mcc表管理API", description =  "LedgerMccController")
@RestController
@Validated
@RequestMapping(value = "/ledgermcc")
public class LedgerMccController {

    @Autowired
    private LedgerMccQueryProvider ledgerMccQueryProvider;

    @Autowired
    private LedgerMccProvider ledgerMccProvider;


    @Operation(summary = "分页查询拉卡拉mcc表")
    @PostMapping("/page")
    public BaseResponse<LedgerMccPageResponse> getPage(@RequestBody @Valid LedgerMccPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("mccId", "desc");
        return ledgerMccQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询拉卡拉mcc表")
    @PostMapping("/list")
    public BaseResponse<LedgerMccListResponse> getList(@RequestBody @Valid LedgerMccListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return ledgerMccQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询拉卡拉mcc表")
    @GetMapping("/{mccId}")
    public BaseResponse<LedgerMccByIdResponse> getById(@PathVariable Long mccId) {
        if (mccId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LedgerMccByIdRequest idReq = new LedgerMccByIdRequest();
        idReq.setMccId(mccId);
        return ledgerMccQueryProvider.getById(idReq);
    }


    @Operation(summary = "根据id删除拉卡拉mcc表")
    @DeleteMapping("/{mccId}")
    public BaseResponse deleteById(@PathVariable Long mccId) {
        if (mccId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LedgerMccDelByIdRequest delByIdReq = new LedgerMccDelByIdRequest();
        delByIdReq.setMccId(mccId);
        return ledgerMccProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除拉卡拉mcc表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid LedgerMccDelByIdListRequest delByIdListReq) {
        return ledgerMccProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出拉卡拉mcc表列表")
//    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        LedgerMccExportRequest listReq = JSON.parseObject(decrypted, LedgerMccExportRequest.class);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(listReq));
//FIXME: 需要手动添加ReportType里面的枚举值, 替换掉下面的ReportType.XXXXXXX, 并放开注释, 实现方法的调用
//        exportDataRequest.setTypeCd(ReportType.XXXXXXX);
//        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }


}
