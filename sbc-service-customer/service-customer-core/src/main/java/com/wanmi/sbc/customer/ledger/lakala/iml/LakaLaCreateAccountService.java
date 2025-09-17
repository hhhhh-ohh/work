package com.wanmi.sbc.customer.ledger.lakala.iml;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.ledger.LedgerRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountState;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.ledger.LedgerFunction;
import com.wanmi.sbc.customer.ledger.lakala.builder.LakalaParamBuilder;
import com.wanmi.sbc.customer.ledgeraccount.service.LedgerAccountService;
import com.wanmi.sbc.customer.ledgererrorrecord.service.LedgerErrorRecordService;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.service.LedgerFileService;
import com.wanmi.sbc.customer.mq.ProducerService;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.AddMerRequest;
import com.wanmi.sbc.empower.api.request.Ledger.ApplySplitReceiverRequest;
import com.wanmi.sbc.empower.api.request.Ledger.CardBinRequest;
import com.wanmi.sbc.empower.api.request.Ledger.UploadRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.*;
import com.wanmi.sbc.empower.api.response.ledger.lakala.AddMerResponse;
import com.wanmi.sbc.empower.api.response.ledger.lakala.ApplySplitReceiverResponse;
import com.wanmi.sbc.empower.api.response.ledger.lakala.CardBinResponse;
import com.wanmi.sbc.empower.api.response.ledger.lakala.UploadResponse;
import com.wanmi.sbc.empower.bean.enums.AttType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.*;

/**
 * @author xuyunpeng
 * @className LakaLaAccountService
 * @description
 * @date 2022/7/8 2:14 PM
 **/
@Service
@Slf4j
public class LakaLaCreateAccountService implements LedgerFunction {

    @Autowired
    private LedgerAccountService ledgerAccountService;

    @Autowired
    private LedgerProvider ledgerProvider;

    @Autowired
    private LedgerFileService ledgerFileService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private LedgerErrorRecordService ledgerErrorRecordService;

    @Autowired
    private ProducerService producerService;

    @Override
    public void excute(LedgerRequest request) {
        if (StringUtils.isBlank(request.getBusinessId())) {
            return;
        }
        LedgerAccountVO accountVO = ledgerAccountService.findByBusiness(request.getBusinessId(), Boolean.FALSE);
        if (accountVO == null) {
            log.info("清分分账不存在，业务id:{}", request.getBusinessId());
            return;
        }

        if (LedgerAccountState.PASS.toValue() == accountVO.getAccountState()) {
            return;
        }
        if (LedgerAccountType.MERCHANTS.toValue() == accountVO.getAccountType()) {
            addMer(accountVO);
        } else {
            applySplitReceiver(accountVO);
        }
    }

    /**
     * 商户进件
     */
    public void addMer(LedgerAccountVO account) {
        try {
            //1、上传文件
            List<String> fileIds = LakalaParamBuilder.fileIds(account);
            List<LedgerFile> files = ledgerFileService.findByIds(fileIds);
            Set<FileData> fileDataList = new HashSet<>();
            Map<AttType, LedgerFile> attTypeMap = LakalaParamBuilder.fileContents(account, files);
            attTypeMap.forEach((attType, file) -> {
                LakalaUploadRequest uploadRequest = LakalaUploadRequest.builder()
                        .attContext(Base64Utils.encodeToString(file.getContent()))
                        .attType(attType.toValue())
                        .attExtName(file.getFileExt())
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build();
                BaseResponse uploadBaseResponse = ledgerProvider.uploadFile(UploadRequest.builder().lakalaUploadRequest(uploadRequest).build());
                if (CommonErrorCodeEnum.K000000.getCode().equals(uploadBaseResponse.getCode())) {
                    UploadResponse uploadResponse = BeanUtils.beanCovert(uploadBaseResponse.getContext(), UploadResponse.class);
                    fileDataList.add(KsBeanUtil.convert(uploadResponse, FileData.class));
                } else {
                    log.error("商户进件，上传附件失败，业务id：{}，文件id：{}，错误信息：{}", account.getBusinessId(), file.getId(), uploadBaseResponse.getMessage());
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, uploadBaseResponse.getMessage());
                }
            });

            //商户入网
            LakalaAddMerRequest addMerRequest = LakalaParamBuilder.buildAddMerRequest(account, fileDataList);
            addMerRequest.setOrderNo(generatorService.generateLedgerOrderNo());
            BaseResponse addMerBaseResponse = ledgerProvider.addMer(AddMerRequest.builder().lakalaAddMerRequest(addMerRequest).build());
            AddMerResponse addMerResponse = BeanUtils.beanCovert(addMerBaseResponse.getContext(), AddMerResponse.class);
            LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                    .businessId(account.getBusinessId())
                    .contractId(addMerResponse.getContractId())
                    .accountState(LedgerAccountState.CHECKING.toValue())
                    .build();
            ledgerAccountService.updateForCreateAccount(modifyRequest, account);
        } catch (SbcRuntimeException e) {
            if (EmpowerErrorCodeEnum.K060029.getCode().equals(e.getErrorCode())) {
                LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                        .businessId(account.getBusinessId())
                        .accountRejectReason(e.getMessage())
                        .accountState(LedgerAccountState.NOT_PASS.toValue())
                        .build();
                ledgerAccountService.updateForCreateAccount(modifyRequest, account);
            } else {
                log.error("拉卡拉商户进件失败，错误信息：{}", e);
                //错误记录
                ledgerErrorRecordService.addErrorRecord(account, e.getMessage(), LedgerFunctionType.CREATE_ACCOUNT);
            }
        }  catch (Exception e) {
            log.error("拉卡拉商户进件失败，错误信息：{}", e);
            //错误记录
            ledgerErrorRecordService.addErrorRecord(account, e.getMessage(), LedgerFunctionType.CREATE_ACCOUNT);
        }
    }

    /**
     * 创建接收方
     * @param account
     */
    public void applySplitReceiver(LedgerAccountVO account) {
        try {
            //1、上传文件
            List<String> fileIds = LakalaParamBuilder.fileIds(account);
            List<LedgerFile> files = ledgerFileService.findByIds(fileIds);
            List<Attach> fileDataList = new ArrayList<>();
            Map<AttType, LedgerFile> attTypeMap = LakalaParamBuilder.fileContents(account, files);
            attTypeMap.forEach((attType, file) -> {
                LakalaUploadRequest uploadRequest = LakalaUploadRequest.builder()
                        .attContext(Base64Utils.encodeToString(file.getContent()))
                        .attType(attType.toValue())
                        .attExtName(file.getFileExt())
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build();
                BaseResponse uploadBaseResponse = ledgerProvider.uploadFile(UploadRequest.builder().lakalaUploadRequest(uploadRequest).build());
                UploadResponse uploadResponse = BeanUtils.beanCovert(uploadBaseResponse.getContext(), UploadResponse.class);
                Attach attach = new Attach();
                attach.setAttachType(attType.toValue());
                attach.setAttachStorePath(uploadResponse.getAttFileId());
                attach.setAttachName(file.getFileExt());
                fileDataList.add(attach);
            });

            //2、卡bin查询
            CardBinResponse cardBinResponse = null;
            if (LedgerReceiverType.DISTRIBUTION.toValue() == account.getReceiverType()) {
                LakalaCardBinRequest binRequest = LakalaCardBinRequest.builder()
                        .cardNo(account.getAcctNo())
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build();
                BaseResponse binBaseResponse = ledgerProvider.cardBin(CardBinRequest.builder().lakalaCardBinRequest(binRequest).build());
                cardBinResponse = BeanUtils.beanCovert(binBaseResponse.getContext(), CardBinResponse.class);
                if (StringUtils.isBlank(cardBinResponse.getBankCode())) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060029);
                }
            }


            //3、创建接收方
            LakalaApplySplitReceiverRequest receiverRequest = LakalaParamBuilder.buildReceiverRequest(account, fileDataList, cardBinResponse);
            receiverRequest.setOrderNo(generatorService.generateLedgerOrderNo());
            BaseResponse receiverBaseResponse = ledgerProvider.applySplitReceiver(ApplySplitReceiverRequest.builder()
                    .lakalaApplySplitReceiverRequest(receiverRequest).build());
            ApplySplitReceiverResponse receiverResponse = BeanUtils.beanCovert(receiverBaseResponse.getContext(), ApplySplitReceiverResponse.class);
            LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                    .businessId(account.getBusinessId())
                    .thirdMemNo(receiverResponse.getReceiverNo())
                    .accountState(LedgerAccountState.PASS.toValue())
                    .build();
            ledgerAccountService.updateForCreateAccount(modifyRequest, account);
            if (LedgerReceiverType.PROVIDER.toValue() == account.getReceiverType()) {
                //通知创建绑定关系数据 该接收方与所有已开通分账的商户的关系
                producerService.batchAddLedgerReceiverRel(account.getId());
            }
        } catch (SbcRuntimeException e) {
           if (EmpowerErrorCodeEnum.K060029.getCode().equals(e.getErrorCode())) {
               LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                       .businessId(account.getBusinessId())
                       .accountRejectReason(e.getMessage())
                       .accountState(LedgerAccountState.NOT_PASS.toValue())
                       .build();
               ledgerAccountService.updateForCreateAccount(modifyRequest, account);
           } else {
               log.error("拉卡拉接收方申请失败，错误信息：{}", e);
               //错误记录
               ledgerErrorRecordService.addErrorRecord(account, e.getMessage(), LedgerFunctionType.CREATE_ACCOUNT);
           }
        } catch (Exception e) {
            log.error("拉卡拉接收方申请失败，错误信息：{}", e);
            //错误记录
            ledgerErrorRecordService.addErrorRecord(account, e.getMessage(), LedgerFunctionType.CREATE_ACCOUNT);
        }
    }


}
