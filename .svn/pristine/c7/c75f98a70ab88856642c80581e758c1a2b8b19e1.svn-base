package com.wanmi.sbc.ledgersupplier;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgersupplier.LedgerSupplierQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.DistributionBindStateQueryRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.ProviderBindStateQueryRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierPageRequest;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.BindStateQueryResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierPageResponse;
import com.wanmi.sbc.customer.bean.vo.LakalaDistributionBindVO;
import com.wanmi.sbc.customer.bean.vo.LakalaProviderBindVO;
import com.wanmi.sbc.report.ExportCenter;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "商户分账绑定数据管理API", description =  "LedgerSupplierController")
@RestController
@Validated
@RequestMapping(value = "/ledgersupplier")
public class LedgerSupplierController {

    @Autowired
    private LedgerSupplierQueryProvider ledgerSupplierQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Operation(summary = "分页查询商户分账绑定数据")
    @PostMapping("/page")
    public BaseResponse<LedgerSupplierPageResponse> getPage(@RequestBody @Valid LedgerSupplierPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return ledgerSupplierQueryProvider.page(pageReq);
    }

    @Operation(summary = "分页查询商户和供应商绑定数据")
    @PostMapping("/provider/page")
    public BaseResponse<MicroServicePage<LakalaProviderBindVO>> queryProviderBindStatePage (@RequestBody ProviderBindStateQueryRequest providerBindStateQueryRequest) {
        BindStateQueryResponse bindStateQueryResponse =
                ledgerReceiverRelQueryProvider.queryProviderBindStatePage(providerBindStateQueryRequest).getContext();
        return BaseResponse.success(bindStateQueryResponse.getLakalaProviderBindVOPage());
    }

    @Operation(summary = "分页查询商户和分销员绑定数据")
    @PostMapping("/distribution/page")
    public BaseResponse<MicroServicePage<LakalaDistributionBindVO>> queryDistributionBindStatePage (@RequestBody DistributionBindStateQueryRequest distributionBindStateQueryRequest) {
        BindStateQueryResponse bindStateQueryResponse =
                ledgerReceiverRelQueryProvider.queryDistributionBindStatePage(distributionBindStateQueryRequest).getContext();
        return BaseResponse.success(bindStateQueryResponse.getLakalaDistributionBindVOPage());
    }

    @Operation(summary = "供应商绑定关系导出")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/provider/{encrypted}", method = RequestMethod.GET)
    public BaseResponse providerExport(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.LAKALA_SUPPLIER_PROVIDER_BIND_STATE_LIST);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "分销员绑定关系导出")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/distribution/{encrypted}", method = RequestMethod.GET)
    public BaseResponse distributionExport(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.LAKALA_SUPPLIER_DISTRIBUTION_BIND_STATE_LIST);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
