package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.ReceiverSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgercontract.LedgerContractSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.DistributionApplyRecordRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.SupplierApplyRecordRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.customer.bean.vo.DistributionApplyRecordVO;
import com.wanmi.sbc.customer.bean.vo.SupplierApplyRecordVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Tag(name =  "清分账户管理API", description =  "LedgerAccountController")
@RestController
@Validated
@RequestMapping(value = "/ledgeraccount")
public class BossLedgerAccountController {

    @Autowired
    private LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Autowired
    private LedgerContractProvider ledgerContractProvider;

    @Operation(summary = "保存清分账户")
    @PostMapping("/save")
    public BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid ReceiverSaveRequest addReq) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerAccountSaveRequest request = KsBeanUtil.convert(addReq, LedgerAccountSaveRequest.class);
        request.setAccountType(LedgerAccountType.RECEIVER.toValue());
        request.setReceiverType(LedgerReceiverType.PLATFORM.toValue());
        request.setBusinessId(Constants.BOSS_DEFAULT_STORE_ID.toString());
        return ledgerAccountProvider.saveAccount(request);
    }


    @Operation(summary = "商家、供应商进件记录")
    @PostMapping("/supplier/apply-recordPage")
    public BaseResponse<MicroServicePage<SupplierApplyRecordVO>> supplierApplyRecordPage(@RequestBody @Valid SupplierApplyRecordRequest request) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        MicroServicePage<SupplierApplyRecordVO> supplierApplyRecordVOMicroServicePage =
                ledgerAccountQueryProvider.supplierApplyRecordPage(request).getContext();
        supplierApplyRecordVOMicroServicePage.getContent().forEach(g -> g.setAccountName(SensitiveUtils.handlerMobilePhone(g.getAccountName())));
        return BaseResponse.success(supplierApplyRecordVOMicroServicePage);
    }

    @Operation(summary = "分销员进件记录")
    @PostMapping("/distribution/apply-recordPage")
    public BaseResponse<MicroServicePage<DistributionApplyRecordVO>> distributionApplyRecordPage(@RequestBody @Valid DistributionApplyRecordRequest request) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerAccountQueryProvider.distributionApplyRecordPage(request);
    }

    /**
     * 保存分账绑定关系协议
     * @return
     */
    @Operation(summary = "保存分账绑定关系协议")
    @PostMapping("/bind/contract/save")
    public BaseResponse saveBindContract(@RequestBody @Valid LedgerContractSaveRequest request){
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerContractProvider.save(request);
    }


}
