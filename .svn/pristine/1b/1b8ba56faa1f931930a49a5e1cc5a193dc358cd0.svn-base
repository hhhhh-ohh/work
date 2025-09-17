package com.wanmi.sbc.empower.provider.impl.ledger;

import com.aliyun.oss.common.utils.IOUtils;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.*;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.FeeData;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaAddB2bBusiRequest;
import com.wanmi.sbc.empower.legder.LedgerService;
import com.wanmi.sbc.empower.legder.LedgerServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>分账服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@RestController
@Validated
public class LedgerController implements LedgerProvider {

    @Autowired
    LedgerServiceFactory ledgerServiceFactory;

    @Value("${lakala.openApi.feeRate323}")
    private String feeRate323;

    @Value("${lakala.openApi.max323}")
    private String max323;

    @Value("${lakala.openApi.feeRate325}")
    private String feeRate325;

    @Value("${lakala.openApi.feeRate326}")
    private String feeRate326;

    @Value("${lakala.openApi.feeLowerAmtPcnt323}")
    private String feeLowerAmtPcnt323;

    @Value("${lakala.openApi.feeLowerAmtPcnt326}")
    private String feeLowerAmtPcnt326;

    @Value("${lakala.openApi.feeUpperAmtPcnt326}")
    private String feeUpperAmtPcnt326;

    @Value("${lakala.openApi.feeLowerAmtPcnt325}")
    private String feeLowerAmtPcnt325;

    @Value("${lakala.openApi.feeUpperAmtPcnt325}")
    private String feeUpperAmtPcnt325;

    @Value("${lakala.openApi.feeRate324}")
    private String feeRate324;

    @Value("${lakala.openApi.feeLowerAmtPcnt324}")
    private String feeLowerAmtPcnt324;

    @Value("${lakala.openApi.feeUpperAmtPcnt324}")
    private String feeUpperAmtPcnt324;


    /**
     * 电子合同申请
     *
     * @param ecApplyRequest
     * @return
     */
    @Override
    public BaseResponse ecApply(@RequestBody @Valid EcApplyRequest ecApplyRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(ecApplyRequest.getLedgerType());
        return ledgerService.ecApply(ecApplyRequest);
    }

    /**
     * 电子合同下载
     *
     * @param ecDownloadRequest
     * @return
     */
    @Override
    public BaseResponse ecDownLoad(@RequestBody @Valid EcDownloadRequest ecDownloadRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(ecDownloadRequest.getLedgerType());
        return ledgerService.ecDownLoad(ecDownloadRequest);
    }

    /**
     * 电子合同查询
     *
     * @param ecQStatusRequest
     * @return
     */
    @Override
    public BaseResponse ecQStatus(@RequestBody @Valid EcQStatusRequest ecQStatusRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(ecQStatusRequest.getLedgerType());
        return ledgerService.ecQStatus(ecQStatusRequest);
    }

    /**
     * 附件上传
     *
     * @param uploadRequest
     * @return
     */
    @Override
    public BaseResponse uploadFile(@RequestBody @Valid UploadRequest uploadRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(uploadRequest.getLedgerType());
        return ledgerService.uploadFile(uploadRequest);
    }

    /**
     * 进件校验
     *
     * @param verifyContractInfoRequest
     * @return
     */
    @Override
    public BaseResponse verifyContractInfo(@RequestBody @Valid VerifyContractInfoRequest verifyContractInfoRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(verifyContractInfoRequest.getLedgerType());
        ledgerService.verifyContractInfo(verifyContractInfoRequest);
        return ledgerService.verifyContractInfo(verifyContractInfoRequest);
    }

    /**
     * 新增商户进件
     *
     * @param addMerRequest
     * @return
     */
    @Override
    public BaseResponse addMer(@RequestBody @Valid AddMerRequest addMerRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(addMerRequest.getLedgerType());
        return ledgerService.addMer(addMerRequest);
    }

    /**
     * 进件复议提交
     *
     * @param reconsiderSubmitRequest
     * @return
     */
    @Override
    public BaseResponse reconsiderSubmit(@RequestBody @Valid ReconsiderSubmitRequest reconsiderSubmitRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(reconsiderSubmitRequest.getLedgerType());
        return ledgerService.reconsiderSubmit(reconsiderSubmitRequest);
    }

    /**
     * 进件信息查询
     *
     * @param queryContractRequest
     * @return
     */
    @Override
    public BaseResponse queryContract(@RequestBody @Valid QueryContractRequest queryContractRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(queryContractRequest.getLedgerType());
        return ledgerService.queryContract(queryContractRequest);
    }


    /**
     * 卡bin查询
     *
     * @param cardBinRequest
     */
    @Override
    public BaseResponse cardBin(@RequestBody @Valid CardBinRequest cardBinRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(cardBinRequest.getLedgerType());
        return ledgerService.cardBin(cardBinRequest);
    }

    /**
     * 商户分账业务开通申请
     *
     * @param applySplitMerRequest
     * @return
     */
    @Override
    public BaseResponse applySplitMer(@RequestBody @Valid ApplySplitMerRequest applySplitMerRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(applySplitMerRequest.getLedgerType());
        return ledgerService.applySplitMer(applySplitMerRequest);
    }


    /**
     * 分账商户信息查询
     *
     * @param querySplitMerRequest
     * @return
     */
    @Override
    public BaseResponse querySplitMer(@RequestBody @Valid QuerySplitMerRequest querySplitMerRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(querySplitMerRequest.getLedgerType());
        return ledgerService.querySplitMer(querySplitMerRequest);
    }

    /**
     * 分账接收方创建申请
     *
     * @param applySplitReceiverRequest
     * @return
     */
    @Override
    public BaseResponse applySplitReceiver(@RequestBody @Valid ApplySplitReceiverRequest applySplitReceiverRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(applySplitReceiverRequest.getLedgerType());
        return ledgerService.applySplitReceiver(applySplitReceiverRequest);
    }

    /**
     * 分账关系绑定申请
     *
     * @param applyBindRequest
     * @return
     */
    @Override
    public BaseResponse applyBind(@RequestBody @Valid ApplyBindRequest applyBindRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(applyBindRequest.getLedgerType());
        return ledgerService.applyBind(applyBindRequest);
    }

    /**
     * 新增线上业务类型
     * @param addB2bBusiRequest
     * @return
     */
    @Override
    public BaseResponse addB2bBusi(@RequestBody @Valid AddB2bBusiRequest addB2bBusiRequest) {
        LedgerService ledgerService = ledgerServiceFactory.create(addB2bBusiRequest.getLedgerType());
        LakalaAddB2bBusiRequest lakalaApplyBindRequest = addB2bBusiRequest.getLakalaAddB2bBusiRequest();
        ClassPathResource resource = new ClassPathResource("bank-code");
        String expressCom = null;
        Set<FeeData> feeData = new HashSet();
        //323 B2B网银借记, 324 B2B网银贷记, 325 B2C网银借记, 326 B2C网银贷记
        List<String> feeRateTypeCodeList = Arrays.asList("323", "324", "325", "326");
        try {
            expressCom = IOUtils.readStreamAsString( resource.getInputStream(), "UTF-8");
            Arrays.stream(expressCom.split("\\n")).forEach(express -> {
                String[] expr = express.split(",");
                feeRateTypeCodeList.forEach(feeRateTypeCode ->{
                    if("323".equals(feeRateTypeCode)){
                        feeData.add(FeeData.builder()
                                .issuerTypeId(expr[0])
                                .feeRateTypeCode(feeRateTypeCode)
                                .feeRatePct(feeRate323)
                                .feeUpperAmtPcnt(max323)
                                .feeLowerAmtPcnt(feeLowerAmtPcnt323)
                                .build());
                    } else if("324".equals(feeRateTypeCode)){
                        feeData.add(FeeData.builder()
                                .issuerTypeId(expr[0])
                                .feeRateTypeCode(feeRateTypeCode)
                                .feeRatePct(feeRate324)
                                .feeLowerAmtPcnt(feeLowerAmtPcnt324)
                                .feeUpperAmtPcnt(feeUpperAmtPcnt324)
                                .build());
                    } else if("325".equals(feeRateTypeCode)){
                        feeData.add(FeeData.builder()
                                .issuerTypeId(expr[0])
                                .feeRateTypeCode(feeRateTypeCode)
                                .feeRatePct(feeRate325)
                                .feeLowerAmtPcnt(feeLowerAmtPcnt325)
                                .feeUpperAmtPcnt(feeUpperAmtPcnt325)
                                .build());
                    } else if("326".equals(feeRateTypeCode)){
                        feeData.add(FeeData.builder()
                                .issuerTypeId(expr[0])
                                .feeRateTypeCode(feeRateTypeCode)
                                .feeRatePct(feeRate326)
                                .feeLowerAmtPcnt(feeLowerAmtPcnt326)
                                .feeUpperAmtPcnt(feeUpperAmtPcnt326)
                                .build());
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        lakalaApplyBindRequest.setFeeData(feeData);
        return ledgerService.addB2bBusi(addB2bBusiRequest);
    }
}
