package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.DistributionAccountCheckRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.DistributionReceiverSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileAddRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelApplyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelPageMobileRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountCheckResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountPageMobileResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import com.wanmi.sbc.customer.api.response.ledgercontract.LedgerContractByIdResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.customer.bean.vo.LedgerContractVO;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;


@Tag(name =  "清分账户管理API", description =  "LedgerAccountController")
@RestController
@Validated
@RequestMapping(value = "/ledgeraccount")
public class MobileLedgerAccountController {

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private LedgerFileProvider ledgerFileProvider;

    private final static long FILE_MAX_SIZE = 5L * 1024 * 1024;

    @Autowired
    private MobileLedgerAccountBaseService ledgerAccountBaseService;

    @Autowired
    private LedgerContractQueryProvider ledgerContractQueryProvider;

    @Autowired
    private LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Autowired
    private DistributionService distributionService;

    @Operation(summary = "保存清分账户")
    @PostMapping("/save")
    public BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid DistributionReceiverSaveRequest saveRequest) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerAccountSaveRequest request = KsBeanUtil.convert(saveRequest, LedgerAccountSaveRequest.class);
        request.setAccountType(LedgerAccountType.RECEIVER.toValue());
        request.setReceiverType(LedgerReceiverType.DISTRIBUTION.toValue());
        request.setBusinessId(commonUtil.getOperatorId());
        return ledgerAccountProvider.saveAccount(request);
    }

    @Operation(summary = "查询清分账户")
    @GetMapping
    public BaseResponse<LedgerAccountByIdResponse> getAccount() {
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerAccountFindRequest idReq = new LedgerAccountFindRequest();
        idReq.setBusinessId(commonUtil.getOperatorId());
        idReq.setSetFileFlag(Boolean.TRUE);
        return ledgerAccountQueryProvider.getById(idReq);
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

    @Operation(summary = "检查支付开关是否开启")
    @GetMapping(value = "/checkGatewayOpen")
    public BaseResponse<Boolean> checkGatewayOpen() {
        return BaseResponse.success(ledgerAccountBaseService.getGatewayOpen());
    }

    @Operation(summary = "检查支付开关是否开启")
    @GetMapping(value = "/checkCasherGatewayOpen")
    public BaseResponse<Boolean> checkCasherGatewayOpen() {
        return BaseResponse.success(ledgerAccountBaseService.getLaKaLaCasherPayOpen());
    }

    @Operation(summary = "查询分账合同协议")
    @GetMapping("/contract")
    public BaseResponse<LedgerContractByIdResponse> getContract() {
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerContractVO ledgerContractVO = ledgerContractQueryProvider.getContract().getContext().getLedgerContractVO();
        ledgerContractVO.setContent(ledgerContractVO.getContent()
                .replace(Constants.PART_A, "")
                .replace(Constants.PART_B, ""));
        return BaseResponse.success(new LedgerContractByIdResponse(ledgerContractVO));
    }

    @Operation(summary = "查询清分账户状态")
    @GetMapping("/state")
    public BaseResponse<LedgerAccountCheckResponse> getAccountState() {
        ledgerAccountBaseService.checkGatewayOpen();
        return ledgerAccountQueryProvider.checkDistributionState(DistributionAccountCheckRequest.builder()
                .businessId(commonUtil.getOperatorId()).build());
    }

    /**
     * 分页查询分销员分账绑定
     * @param request
     * @return
     */
    @Operation(summary = "分页查询分销员分账绑定")
    @PostMapping("/bind/page")
    public BaseResponse<LedgerAccountPageMobileResponse> pageBindInfo(@RequestBody @Valid LedgerReceiverRelPageMobileRequest request){
        //检验是否分销员
        boolean isDistributor = distributionService.isDistributor(commonUtil.getOperatorId());
        if (!isDistributor) {
            return BaseResponse.success(new LedgerAccountPageMobileResponse(new MicroServicePage<>()));
        }
        ledgerAccountBaseService.checkGatewayOpen();
        request.setCustomerId(commonUtil.getOperatorId());
        return ledgerAccountQueryProvider.pageMobile(request);
    }

    /**
     * 分销员申请分账绑定
     * @param request
     * @return
     */
    @Operation(summary = "分销员申请分账绑定")
    @PostMapping("/bind/apply")
    public BaseResponse applyBind(@RequestBody @Valid LedgerReceiverRelApplyRequest request){
        ledgerAccountBaseService.checkGatewayOpen();
        request.setCustomerId(commonUtil.getOperatorId());
        return ledgerReceiverRelProvider.applyBind(request);
    }


}
