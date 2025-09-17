package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.*;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileAddRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountPicResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerContractResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;


@Tag(name =  "清分账户管理API", description =  "LedgerAccountController")
@RestController
@Validated
@RequestMapping(value = "/ledgeraccount")
public class LedgerAccountBaseController {

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private LedgerFileProvider ledgerFileProvider;

    private final static long FILE_MAX_SIZE = 5L * 1024 * 1024;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Operation(summary = "查询清分账户")
    @GetMapping
    public BaseResponse<LedgerAccountByIdResponse> getAccount() {
        ledgerAccountBaseService.checkGatewayOpen();
        String businessId = commonUtil.getCompanyInfoId() != null
                ? commonUtil.getCompanyInfoId().toString()
                : Constants.BOSS_DEFAULT_STORE_ID.toString();

        LedgerAccountFindRequest idReq = new LedgerAccountFindRequest();
        idReq.setBusinessId(businessId);
        idReq.setSetFileFlag(Boolean.FALSE);
        return ledgerAccountQueryProvider.getById(idReq);
    }

    @Operation(summary = "查询清分账户图片")
    @GetMapping("/pic")
    public BaseResponse<LedgerAccountPicResponse> getAccountPic(){
        ledgerAccountBaseService.checkGatewayOpen();
        String businessId = commonUtil.getCompanyInfoId() != null
                ? commonUtil.getCompanyInfoId().toString()
                : Constants.BOSS_DEFAULT_STORE_ID.toString();

        LedgerAccountPicFindRequest request = new LedgerAccountPicFindRequest();
        request.setBusinessId(businessId);
        return ledgerAccountQueryProvider.getPicById(request);
    }
    @Operation(summary = "上传素材")
    @RequestMapping(value = "/uploadResource", method = RequestMethod.POST)
    public BaseResponse<String> uploadFile(@RequestParam("uploadFile") MultipartFile file) {
        //验证上传参数
        if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (!ImageUtils.checkImageSuffix(file.getOriginalFilename())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (file.getSize() >= FILE_MAX_SIZE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        String fileId = null;
        try {
            String fileExt = ImageUtils.getImageType(file.getOriginalFilename());
            byte[] data = ledgerAccountBaseService.reSizeImage(file);
            fileId = ledgerFileProvider.add(LedgerFileAddRequest.builder()
                    .content(data).fileExt(fileExt).build()).getContext().getFileId();
        } catch (Exception e) {
            throw new SbcRuntimeException(e);
        }

        return BaseResponse.success(fileId);
    }

    /**
     * 检查支付开关是否开启
     * @return
     */
    @Operation(summary = "检查支付开关是否开启")
    @GetMapping(value = "/checkGatewayOpen")
    public BaseResponse<Boolean> checkGatewayOpen() {
        return BaseResponse.success(ledgerAccountBaseService.getGatewayOpen());
    }

    /**
     * 查询特约商户协议
     * @return
     */
    @Operation(summary = "查询特约商户协议")
    @GetMapping(value = "/supplierContract")
    public BaseResponse<LedgerContractResponse> findSupplierContract() {
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerAccountQueryProvider.findSupplierContract(LedgerSupplierContractRequest.builder()
                .companyInfoId(commonUtil.getCompanyInfoId().toString()).build());
    }

    /**
     * 申请结算授权委托协议
     * @return
     */
    @Operation(summary = "申请结算授权委托协议")
    @PostMapping(value = "/receiverContract/apply")
    public BaseResponse<String> applyReceiverContract(@RequestBody @Valid LedgerReceiverContractApplyRequest request) {
        ledgerAccountBaseService.checkGatewayOpen();
        request.setSupplierId(commonUtil.getCompanyInfoId());
        return ledgerReceiverRelQueryProvider.applyReceiverContract(request);
    }

    /**
     * 获取结算授权委托协议
     * @return
     */
    @Operation(summary = "获取结算授权委托协议内容")
    @GetMapping(value = "/receiverContract/{id}")
    public BaseResponse<String> findReceiverContract(@PathVariable String id) {
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerReceiverRelQueryProvider.findReceiverContract(LedgerReceiverContractApplyRequest.builder()
                .supplierId(commonUtil.getCompanyInfoId()).relId(id).build());
    }

    /**
     * 检查商户、平台绑定状态
     * @return
     */
    @Operation(summary = "检查商户、平台绑定状态")
    @GetMapping(value = "/checkLedgerAccount")
    public BaseResponse checkLedgerAccount(){
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerReceiverRelQueryProvider.checkLedgerAccount(LedgerAccountCheckRequest.builder()
                .supplierId(commonUtil.getCompanyInfoId()).build());
    }

}
