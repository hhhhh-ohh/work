package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountContractRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerVerifyContractInfoRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.MerchantsSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerBindContractFileSaveRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import com.wanmi.sbc.customer.bean.enums.BindContractUploadType;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
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
public class SupplierLedgerAccountController {

    @Autowired
    private LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    private final static long CONTRACT_MAX_SIZE = 4L *  1024 * 1024;

    @Operation(summary = "保存清分账户")
    @PostMapping("/save")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid MerchantsSaveRequest saveRequest) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerAccountSaveRequest request = KsBeanUtil.convert(saveRequest, LedgerAccountSaveRequest.class);
        request.setBusinessId(commonUtil.getCompanyInfoId().toString());
        request.setAccountType(LedgerAccountType.MERCHANTS.toValue());
        return ledgerAccountProvider.saveAccount(request);
    }

    @Operation(summary = "签署协议")
    @PostMapping("/contract")
    @GlobalTransactional
    public BaseResponse acceptContract() {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        ledgerAccountProvider.acceptContract(LedgerAccountContractRequest.builder()
                .businessId(commonUtil.getCompanyInfoId().toString()).build());
        managerBaseProducerService.saveLedgerAccount(commonUtil.getCompanyInfoId().toString());

        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "获取验证码")
    @PostMapping("/sendVerifiedCode/{customerAccount}")
    public BaseResponse sendVerifiedCode(@PathVariable String customerAccount) {
        //验证手机号格式
        if(!ValidateUtil.isPhone(customerAccount)){
           throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        //发送验证码
        if (Constants.yes.equals(customerCacheService.sendMobileCode(CacheKeyConstant.YZM_MOBILE_LEDGER, customerAccount,
                SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }

        return BaseResponse.FAILED();
    }

    @Operation(summary = "校验boss是否已开通清分账户")
    @GetMapping("/checkBossAccount")
    public BaseResponse checkBossAccount() {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        ledgerAccountQueryProvider.checkBossAccount();
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "商户进件校验")
    @PostMapping("/verifyContractInfo")
    public BaseResponse verifyContractInfo(@RequestBody @Valid LedgerVerifyContractInfoRequest request) {
        //检查支付开关是否开启
        ledgerAccountBaseService.checkGatewayOpen();
        ledgerAccountQueryProvider.verifyContractInfo(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 商户进件上传分账合作协议
     * @param file
     * @return
     */
    @Operation(summary = "商户进件上传分账合作协议")
    @RequestMapping(value = "/supplier/bindContract/upload", method = RequestMethod.POST)
    public BaseResponse uploadForMer(@RequestParam("uploadFile") MultipartFile file) {
        uploadPDF(BindContractUploadType.APPLY_MER, file, null);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 分账绑定上传分账合作协议
     * @param file
     * @return
     */
    @Operation(summary = "分账绑定上传分账合作协议")
    @RequestMapping(value = "/bindContract/upload", method = RequestMethod.POST)
    public BaseResponse uploadForMer(@RequestParam("uploadFile") MultipartFile file, @RequestParam("relId") String relId) {
        uploadPDF(BindContractUploadType.APPLY_BIND, file, relId);
        return BaseResponse.SUCCESSFUL();
    }



    /**
     * 上传pdf协议
     * @param type
     * @param file
     * @param relId
     */
    public void uploadPDF(BindContractUploadType type, MultipartFile file, String relId) {
        //验证上传参数
        if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        if ( fileName.length() > Constants.NUM_20) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010073);
        }

        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        if (!fileExt.equalsIgnoreCase(Constants.PDF)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010071);
        }

        if (file.getSize() >= CONTRACT_MAX_SIZE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (Exception e) {
            throw new SbcRuntimeException(e);
        }

        ledgerReceiverRelProvider.saveBindContractFile(LedgerBindContractFileSaveRequest.builder()
                .supplierId(commonUtil.getCompanyInfoId())
                .contract(bytes)
                .type(type)
                .relId(relId).build());
    }



}
