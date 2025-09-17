package com.wanmi.sbc.customer.ledger.lakala.iml;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.request.ledger.LedgerRequest;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.ledger.LedgerFunction;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgeraccount.repository.LedgerAccountRepository;
import com.wanmi.sbc.customer.ledgercontract.model.root.LedgerContract;
import com.wanmi.sbc.customer.ledgercontract.repository.LedgerContractRepository;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.service.LedgerFileService;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.customer.ledgerreceiverrel.service.LedgerReceiverRelService;
import com.wanmi.sbc.customer.mq.ProducerService;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.ApplyBindRequest;
import com.wanmi.sbc.empower.api.request.Ledger.UploadRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaApplyBindRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaUploadRequest;
import com.wanmi.sbc.empower.api.response.ledger.lakala.ApplyBindResponse;
import com.wanmi.sbc.empower.api.response.ledger.lakala.UploadResponse;
import com.wanmi.sbc.empower.bean.enums.AttType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Collections;
import java.util.List;

/**
 * @author xuyunpeng
 * @className LakalaBindService
 * @description 分账绑定
 * @date 2022/7/15 9:45 AM
 **/
@Service
@Slf4j
public class LakalaBindService implements LedgerFunction {

    @Autowired
    private LedgerProvider ledgerProvider;

    @Autowired
    private LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    private LedgerContractRepository ledgerContractRepository;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private LedgerReceiverRelService ledgerReceiverRelService;

    @Autowired
    private ProducerService producerService;

    private static final Integer DISTRIBUTION_MAX_BIND_SIZE = 1000;

    @Autowired
    private LedgerFileService ledgerFileService;

    @Override
    public void excute(LedgerRequest request) {

        //查询分账绑定关系数据
        if (StringUtils.isBlank(request.getReceiverRelId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LedgerReceiverRel rel = ledgerReceiverRelService.getOne(request.getReceiverRelId());
        if (rel == null
                || LedgerReceiverType.DISTRIBUTION.toValue() == rel.getReceiverType() && CheckState.NOT_PASS.toValue() == rel.getCheckState()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String supplierId = rel.getSupplierId().toString();

        //查询清分账户信息
        LedgerAccount supplierAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(supplierId, DeleteFlag.NO);
        LedgerAccount receiverAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(rel.getReceiverId(), DeleteFlag.NO);
        if (supplierAccount == null || receiverAccount == null ||
                LedgerState.PASS.toValue() != supplierAccount.getLedgerState()
                || LedgerAccountState.PASS.toValue() != receiverAccount.getAccountState() ) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010058);
        }

        if (LedgerBindState.CHECKING.toValue() == rel.getBindState()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010069);
        }

        if(LedgerBindState.BINDING.toValue() == rel.getBindState()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010059);
        }

        if (LedgerReceiverType.DISTRIBUTION.toValue() == receiverAccount.getReceiverType()) {
            Long count = ledgerReceiverRelService.countReceiver(rel.getReceiverId());
            if (count >= DISTRIBUTION_MAX_BIND_SIZE) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010060);
            }
        }

        //查询分账合作协议
        byte[] bindFile = null;
        if (LedgerReceiverType.DISTRIBUTION.toValue() == rel.getReceiverType()) {
            //平台协议
            LedgerContract contract = ledgerContractRepository.findFirstByOrderById();
            String contractContent = contract.getContent()
                    .replace(Constants.PART_A, supplierAccount.getMerBlisName())
                    .replace(Constants.PART_B, rel.getReceiverType()== LedgerReceiverType.DISTRIBUTION.toValue()
                            ? receiverAccount.getLarName() : receiverAccount.getMerBlisName());
//            bindFile = PdfUtil.htmlToPdfByte(contractContent);
            bindFile = PdfUtilForPDFBox.htmlToPdfByte(Constants.LAKALA_LEDGER_CONTRACT,supplierAccount.getMerBlisName(),rel.getReceiverType()== LedgerReceiverType.DISTRIBUTION.toValue()
                    ? receiverAccount.getLarName() : receiverAccount.getMerBlisName());
        } else {
            //商户上传的协议
            if (StringUtils.isNotBlank(rel.getBindContractId())) {
                LedgerFile ledgerFile = ledgerFileService.getOne(rel.getBindContractId());
                if (ledgerFile != null) {
                    bindFile = ledgerFile.getContent();
                }
            }
        }
        if (ArrayUtils.isEmpty(bindFile)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010072);
        }

        //查询结算授权委托协议
        LedgerFile authorizeFile = null;
        if (StringUtils.isNotBlank(rel.getEcContentId())) {
            authorizeFile = ledgerFileService.getOne(rel.getEcContentId());
        }
        if (authorizeFile == null || ArrayUtils.isEmpty(authorizeFile.getContent())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010064);
        }

        //合并协议
        List<byte[]> fileBytes = Lists.newArrayList(bindFile, authorizeFile.getContent());
        String pdfStr = Base64Utils.encodeToString(PdfUtilForPDFBox.mergePdfFile(fileBytes));

        //上传合同
        LakalaUploadRequest uploadRequest = LakalaUploadRequest.builder()
                .attContext(pdfStr)
                .attType(AttType.NETWORK_XY.toValue())
                .attExtName(Constants.PDF)
                .orderNo(generatorService.generateLedgerOrderNo())
                .build();
        BaseResponse uploadBaseResponse = ledgerProvider.uploadFile(UploadRequest.builder().lakalaUploadRequest(uploadRequest).build());
        UploadResponse uploadResponse = BeanUtils.beanCovert(uploadBaseResponse.getContext(), UploadResponse.class);

        //申请绑定
        LakalaApplyBindRequest bindRequest = LakalaApplyBindRequest.builder()
                .merInnerNo(supplierAccount.getThirdMemNo())
                .receiverNo(receiverAccount.getThirdMemNo())
                .entrustFilePath(uploadResponse.getAttFileId())
                .orderNo(generatorService.generateLedgerOrderNo())
                .build();
        ApplyBindRequest applyBindRequest = ApplyBindRequest.builder().lakalaApplyBindRequest(bindRequest).build();
        BaseResponse baseResponse = ledgerProvider.applyBind(applyBindRequest);
        ApplyBindResponse applyBindResponse = BeanUtils.beanCovert(baseResponse.getContext(), ApplyBindResponse.class);

        if (LedgerReceiverType.DISTRIBUTION.toValue() == rel.getReceiverType()) {
            rel.setCheckState(CheckState.CHECKED.toValue());
        }
        rel.setBindState(LedgerBindState.CHECKING.toValue());
        rel.setApplyId(applyBindResponse.getApplyId().toString());
        ledgerReceiverRelService.add(rel);
        //刷新es
        producerService.esAddLedgerBindInfo(Collections.singletonList(rel));
    }
}
