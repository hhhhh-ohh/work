package com.wanmi.sbc.empower.legder.lakala;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.LakalaCommonConstant;
import com.wanmi.sbc.empower.api.request.Ledger.*;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.*;
import com.wanmi.sbc.empower.api.response.ledger.lakala.*;
import com.wanmi.sbc.empower.legder.LedgerService;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 拉卡拉分账业务
 */
@Service
@Slf4j
public class LakalaLedgerServiceImpl implements LedgerService {


    @Value("${lakala.openApi.url}")
    private String openUrl;

    @Value("${lakala.openApi.feeRate300}")
    private String feeRate300;

    @Value("${lakala.openApi.max300}")
    private String max300;

    @Value("${lakala.openApi.feeRate301}")
    private String feeRate301;

    @Value("${lakala.openApi.feeRate302}")
    private String feeRate302;

    @Value("${lakala.openApi.feeRate303}")
    private String feeRate303;

    @Value("${lakala.openApi.feeRate314}")
    private String feeRate314;

    @Value("${lakala.openApi.feeRate315}")
    private String feeRate315;

    @Value("${lakala.openApi.feeRate322}")
    private String feeRate322;

    @Value("${lakala.openApi.max322}")
    private String max322;

    @Value("${lakala.openApi.B2B}")
    private String B2B;

    @Value("${lakala.openApi.splitLowestRatio}")
    private String splitLowestRatio;


    /**
     * 电子合同申请
     *
     * @param ecApplyRequest
     * @return
     */
    @Override
    public BaseResponse<EcApplyResponse> ecApply(EcApplyRequest ecApplyRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaEcApplyRequest lakalaEcApplyRequest = ecApplyRequest.getLakalaEcApplyRequest();
        if (LakalaCommonConstant.EC005.equals(lakalaEcApplyRequest.getEcTypeCode())) {
            String ecContentParameters = lakalaEcApplyRequest.getEcContentParameters();
            Ec ec = JSON.parseObject(ecContentParameters, Ec.class);
            //境内卡支付业务借记卡、贷记卡
            ec.setA7(feeRate300);
            ec.setA8(max300);
            ec.setA10(feeRate301);
            //扫码支付业务
            ec.setA19(feeRate302);
            //网银B2C
            ec.setA30(feeRate314);
            ec.setA31(feeRate315);
            ec.setA32("1");
            ec.setA33("1");
            //网银B2B
            ec.setA35(B2B);
            //聚合支付业务
            ec.setA37(feeRate302);
            ec.setA39(feeRate303);
            //大额付款业务
            ec.setA44(feeRate322);
            ec.setA45("1");
            ec.setA46(feeRate322);
            ec.setA47(max322);
            lakalaEcApplyRequest.setEcContentParameters(JSON.toJSONString(ec));
        }
        //填充机构号
        lakalaEcApplyRequest.setOrgId(Integer.valueOf(payGateway.getConfig().getAccount()));
        //填充回调url
        lakalaEcApplyRequest.setRetUrl(payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.EC_APPLY_CALL_BACK_URL));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaEcApplyRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.EC_APPLY_URL), body, authorization, EcApplyResponse.class);
    }

    /**
     * 电子合同下载
     *
     * @param ecDownloadRequest
     * @return
     */
    @Override
    public BaseResponse<EcDownloadResponse> ecDownLoad(EcDownloadRequest ecDownloadRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaEcDownloadRequest lakalaEcDownloadRequest = ecDownloadRequest.getLakalaEcDownloadRequest();
        //填充机构号
        lakalaEcDownloadRequest.setOrgId(Integer.valueOf(payGateway.getConfig().getAccount()));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaEcDownloadRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.EC_DOWNLOAD_URL), body, authorization, EcDownloadResponse.class);
    }

    /**
     * 电子合同查询
     *
     * @param ecQStatusRequest
     * @return
     */
    @Override
    public BaseResponse<EcQStatusResponse> ecQStatus(EcQStatusRequest ecQStatusRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaEcQStatusRequest lakalaEcQStatusRequest = ecQStatusRequest.getLakalaEcQStatusRequest();
        //填充机构号
        lakalaEcQStatusRequest.setOrgId(Integer.valueOf(payGateway.getConfig().getAccount()));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaEcQStatusRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.EC_Q_STATUS_URL), body, authorization, EcQStatusResponse.class);
    }

    /**
     * 附件上传
     *
     * @param uploadRequest
     * @return
     */
    @Override
    public BaseResponse<UploadResponse> uploadFile(UploadRequest uploadRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaUploadRequest lakalaUploadRequest = uploadRequest.getLakalaUploadRequest();
        //填充机构号
        lakalaUploadRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaUploadRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.UPLOAD_FILE_URL), body, authorization, UploadResponse.class);
    }

    /**
     * 进件校验
     *
     * @param verifyContractInfoRequest
     * @return
     */
    @Override
    public BaseResponse<VerifyContractInfoResponse> verifyContractInfo(VerifyContractInfoRequest verifyContractInfoRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaVerifyContractInfoRequest lakalaVerifyContractInfoRequest = verifyContractInfoRequest.getLakalaVerifyContractInfoRequest();
        //填充机构号
        lakalaVerifyContractInfoRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaVerifyContractInfoRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.VERIFY_CONTRACT_INFO_URL), body, authorization, VerifyContractInfoResponse.class);
    }

    /**
     * 新增商户进件
     *
     * @param addMerRequest
     * @return
     */
    @Override
    public BaseResponse<AddMerResponse> addMer(AddMerRequest addMerRequest) {
        LakalaAddMerRequest lakalaAddMerRequest = addMerRequest.getLakalaAddMerRequest();
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        //填充机构号
        lakalaAddMerRequest.setOrgCode(payGateway.getConfig().getAccount());
        //填充费率
//        lakalaAddMerRequest.setFeeData(Sets.newHashSet(new FeeData("300", "银联借记卡", feeRate),
//                new FeeData("302", "微信", feeRate),
//                new FeeData("303", "支付宝", feeRate)));
        // 填充费率 此处因为修改进件POS类型为B2B收银台而调整，拉卡拉技术方的要求
        lakalaAddMerRequest.setFeeData(Sets.newHashSet(new FeeData("300", "银联借记卡", feeRate300, max300),
                new FeeData("301", "银联贷记卡", feeRate301, null),
                new FeeData("302", "微信", feeRate302, null),
                new FeeData("303", "支付宝", feeRate303, null),
                new FeeData("314", "银联二维码借记卡", feeRate314, null),
                new FeeData("315", "银联二维码贷记卡", feeRate315, null),
                new FeeData("322", "B2B转账", feeRate322, max322)));
        //填充回调地址
        lakalaAddMerRequest.setRetUrl(payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.ADD_MER_CALL_BACK_URL));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaAddMerRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.ADD_MER_URL), body, authorization, AddMerResponse.class);
    }

    /**
     * 进件复议提交
     *
     * @param reconsiderSubmitRequest
     * @return
     */
    @Override
    public BaseResponse<ReconsiderSubmitResponse> reconsiderSubmit(ReconsiderSubmitRequest reconsiderSubmitRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaReconsiderSubmitRequest lakalaReconsiderSubmitRequest = reconsiderSubmitRequest.getLakalaReconsiderSubmitRequest();
        //填充机构号
        lakalaReconsiderSubmitRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaReconsiderSubmitRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.RECONSIDER_SUBMIT_URL), body, authorization, ReconsiderSubmitResponse.class);
    }

    /**
     * 进件信息查询
     *
     * @param queryContractRequest
     * @return
     */
    @Override
    public BaseResponse<QueryContractResponse> queryContract(QueryContractRequest queryContractRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaQueryContractRequest lakalaQueryContractRequest = queryContractRequest.getLakalaQueryContractRequest();
        //填充机构号
        lakalaQueryContractRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaQueryContractRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.QUERY_CONTRACT_URL), body, authorization, QueryContractResponse.class);
    }


    /**
     * 卡bin查询
     *
     * @param cardBinRequest
     */
    @Override
    public BaseResponse<CardBinResponse> cardBin(CardBinRequest cardBinRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaCardBinRequest lakalaCardBinRequest = cardBinRequest.getLakalaCardBinRequest();
        //填充机构号
        lakalaCardBinRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaCardBinRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.CARD_BIN_SUBMIT_URL), body, authorization, CardBinResponse.class);
    }

    /**
     * 商户分账业务开通申请
     *
     * @param applySplitMerRequest
     * @return
     */
    @Override
    public BaseResponse<ApplySplitMerResponse> applySplitMer(ApplySplitMerRequest applySplitMerRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaApplySplitMerRequest lakalaApplySplitMerRequest = applySplitMerRequest.getLakalaApplySplitMerRequest();
        if (StringUtils.isNotBlank(splitLowestRatio)) {
            lakalaApplySplitMerRequest.setSplitLowestRatio(BigDecimal.valueOf(Double.valueOf(splitLowestRatio)));
        }
        //填充机构号
        lakalaApplySplitMerRequest.setOrgCode(payGateway.getConfig().getAccount());
        //填充回调地址
        lakalaApplySplitMerRequest.setRetUrl(payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.APPLY_SPLIT_MER_CALL_BACK_URL));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaApplySplitMerRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.APPLY_SPLIT_MER_URL), body, authorization, ApplySplitMerResponse.class);
    }

    /**
     * 分账商户信息查询
     *
     * @param querySplitMerRequest
     * @return
     */
    @Override
    public BaseResponse<QuerySplitMerResponse> querySplitMer(QuerySplitMerRequest querySplitMerRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaQuerySplitMerRequest lakalaQuerySplitMerRequest = querySplitMerRequest.getLakalaQuerySplitMerRequest();
        //填充机构号
        lakalaQuerySplitMerRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaQuerySplitMerRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.QUERY_SPLIT_MER_URL), body, authorization, QuerySplitMerResponse.class);
    }

    /**
     * 分账接收方创建申请
     *
     * @param applySplitReceiverRequest
     * @return
     */
    @Override
    public BaseResponse<ApplySplitReceiverResponse> applySplitReceiver(ApplySplitReceiverRequest applySplitReceiverRequest) {
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaApplySplitReceiverRequest lakalaApplySplitReceiverRequest = applySplitReceiverRequest.getLakalaApplySplitReceiverRequest();
        //填充机构号
        lakalaApplySplitReceiverRequest.setOrgCode(payGateway.getConfig().getAccount());
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaApplySplitReceiverRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.APPLY_SPLIT_RECEIVER_URL), body, authorization, ApplySplitReceiverResponse.class);
    }

    /**
     * 分账关系绑定申请
     *
     * @param applyBindRequest
     * @return
     */
    @Override
    public BaseResponse<ApplyBindResponse> applyBind(ApplyBindRequest applyBindRequest) {
        LakalaApplyBindRequest lakalaApplyBindRequest = applyBindRequest.getLakalaApplyBindRequest();
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        //填充机构号
        lakalaApplyBindRequest.setOrgCode(payGateway.getConfig().getAccount());
        //填充回调地址
        lakalaApplyBindRequest.setRetUrl(payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.APPLY_BIND_CALL_BACK_URL));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaApplyBindRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.APPLY_BIND_URL), body, authorization, ApplyBindResponse.class);
    }

    /**
     * 新增线上业务类型
     *
     * @param addB2bBusiRequest
     * @return
     */
    @Override
    public BaseResponse<AddB2bBusiResponse> addB2bBusi(AddB2bBusiRequest addB2bBusiRequest) {
        LakalaAddB2bBusiRequest lakalaApplyBindRequest = addB2bBusiRequest.getLakalaAddB2bBusiRequest();
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        //填充机构号
        lakalaApplyBindRequest.setOrgCode(payGateway.getConfig().getAccount());
        lakalaApplyBindRequest.setBusiTypeCode("E_BANK");
        //填充回调地址
        lakalaApplyBindRequest.setRetUrl(payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.ADD_B2B_BUSI_CALL_BACK_URL));
        BaseRequest baseRequest = BaseRequest.builder()
                .reqData(lakalaApplyBindRequest)
                .build();
        String body = JSON.toJSONString(baseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(openUrl.concat(LakalaUtils.ADD_B2B_BUSI), body, authorization, AddB2bBusiResponse.class);
    }
}
