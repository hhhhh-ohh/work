package com.wanmi.sbc.job.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileProvider;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgersupplier.LedgerSupplierProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountListRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileAddRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelAddRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelUpdateBindStateRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.UpdateEC003InfoRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierAddRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoByIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByCompanyInfoIdResponse;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.bean.vo.LedgerFileVO;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.ESStoreInfoInitRequest;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.*;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.*;
import com.wanmi.sbc.empower.api.response.ledger.lakala.*;
import com.wanmi.sbc.empower.bean.enums.AttType;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoModifyByStoreIdAndStatusRequest;
import com.wanmi.sbc.mq.ProducerService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class LedgerCallBackJobService {
    @Autowired
    LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    LedgerProvider ledgerProvider;

    @Autowired
    GeneratorService generatorService;

    @Autowired
    LedgerFileProvider ledgerFileProvider;

    @Autowired
    LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    LedgerFileQueryProvider ledgerFileQueryProvider;

    @Autowired
    StoreProvider storeProvider;

    @Autowired
    CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    LedgerContractQueryProvider ledgerContractQueryProvider;

    @Autowired
    LedgerSupplierProvider ledgerSupplierProvider;

    @Autowired
    LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Autowired
    ProducerService producerService;

    @Autowired
    LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    EsLedgerBindInfoProvider esLedgerBindInfoProvider;

    @Autowired
    StoreQueryProvider storeQueryProvider;

    @Autowired
    DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Autowired
    EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    public static final String COMPLETED_EC_STATUS = "COMPLETED";

    public static final String COMPLETED_CONTRACT_STATUS = "WAIT_FOR_CONTACT";

    public static final String REJECTED_CONTRACT_STATUS = "INNER_CHECK_REJECTED";

    @GlobalTransactional
    public void executeEcApply(String ecApplyId) {
        BaseResponse baseResponse = ledgerProvider.ecQStatus(EcQStatusRequest.builder()
                .lakalaEcQStatusRequest(LakalaEcQStatusRequest.builder()
                        .ecApplyId(Long.valueOf(ecApplyId))
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build())
                .build());
        EcQStatusResponse ecQStatusResponse = BeanUtils.beanCovert(baseResponse.getContext(), EcQStatusResponse.class);
        String ecStatus = ecQStatusResponse.getEcStatus();
        // 如果合同状态已完成，则开始补偿
        if (StringUtils.equals(COMPLETED_EC_STATUS, ecStatus)) {
            //下载电子合同
            BaseResponse ecDownLoadBaseResponse = ledgerProvider.ecDownLoad(EcDownloadRequest.builder()
                    .lakalaEcDownloadRequest(LakalaEcDownloadRequest.builder()
                            .orderNo(generatorService.generateLedgerOrderNo())
                            .ecApplyId(Long.valueOf(ecApplyId))
                            .build())
                    .build());
            if (CommonErrorCodeEnum.K000000.getCode().equals(ecDownLoadBaseResponse.getCode())) {
                EcDownloadResponse ecDownloadResponse = BeanUtils.beanCovert(ecDownLoadBaseResponse.getContext(), EcDownloadResponse.class);
                //获取电子合同的字节数组
                byte[] content = Base64Utils.decodeFromUrlSafeString(ecDownloadResponse.getEcFile());
                //保存文件后获取fileId
                String fileId = ledgerFileProvider.add(LedgerFileAddRequest.builder()
                        .fileExt(Constants.PDF)
                        .content(content)
                        .build()).getContext().getFileId();
                LedgerAccountVO ledgerAccountVO = ledgerAccountQueryProvider.findByEcApplyId(LedgerAccountListRequest.builder()
                        .ecApplyId(ecApplyId)
                        .build()).getContext().getLedgerAccountVO();
                //保存信息到分账账户中
                ledgerAccountProvider.updateEcContentByBusinessId(LedgerAccountModifyRequest.builder()
                        .businessId(ledgerAccountVO.getBusinessId())
                        .ecApplyId(ecApplyId)
                        .ecContent(fileId)
                        .ecNo(ecDownloadResponse.getEcNo())
                        .build());
            }
        }
    }

    @GlobalTransactional
    public void executeEc003Apply(String ecApplyId) {
        BaseResponse baseResponse = ledgerProvider.ecQStatus(EcQStatusRequest.builder()
                .lakalaEcQStatusRequest(LakalaEcQStatusRequest.builder()
                        .ecApplyId(Long.valueOf(ecApplyId))
                        .orderNo(generatorService.generateEc003OrderNo())
                        .build())
                .build());
        EcQStatusResponse ecQStatusResponse = BeanUtils.beanCovert(baseResponse.getContext(), EcQStatusResponse.class);
        String ecStatus = ecQStatusResponse.getEcStatus();
        // 如果合同状态已完成，则开始补偿
        if (StringUtils.equals(COMPLETED_EC_STATUS, ecStatus)) {
            //下载电子合同
            BaseResponse ecDownLoadBaseResponse = ledgerProvider.ecDownLoad(EcDownloadRequest.builder()
                    .lakalaEcDownloadRequest(LakalaEcDownloadRequest.builder()
                            .orderNo(generatorService.generateEc003OrderNo())
                            .ecApplyId(Long.valueOf(ecApplyId))
                            .build())
                    .build());
            if (CommonErrorCodeEnum.K000000.getCode().equals(ecDownLoadBaseResponse.getCode())) {
                EcDownloadResponse ecDownloadResponse = BeanUtils.beanCovert(ecDownLoadBaseResponse.getContext(), EcDownloadResponse.class);
                //获取电子合同的字节数组
                byte[] content = Base64Utils.decodeFromUrlSafeString(ecDownloadResponse.getEcFile());
                //保存文件后获取fileId
                String fileId = ledgerFileProvider.add(LedgerFileAddRequest.builder()
                        .fileExt(Constants.PDF)
                        .content(content)
                        .build()).getContext().getFileId();
                ledgerReceiverRelProvider.updateEC003Info(UpdateEC003InfoRequest.builder()
                        .ecContentId(fileId)
                        .ecNO(ecDownloadResponse.getEcNo())
                        .ecApplyId(ecApplyId)
                        .build());
            }
        }
    }


    @GlobalTransactional
    public void executeContract(String contractId) {
        BaseResponse baseResponse = ledgerProvider.queryContract(QueryContractRequest.builder()
                .lakalaQueryContractRequest(LakalaQueryContractRequest.builder()
                        .contractId(contractId)
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build())
                .build());
        QueryContractResponse queryContractResponse = BeanUtils.beanCovert(baseResponse.getContext(), QueryContractResponse.class);
        String contractStatus = queryContractResponse.getContractStatus();
        LedgerAccountVO ledgerAccountVO = ledgerAccountQueryProvider
                .findByContractId(LedgerAccountListRequest.builder()
                        .contractId(contractId)
                        .build()).getContext().getLedgerAccountVO();
        //  商户进件成功，开始补偿
        if (StringUtils.equals(contractStatus, COMPLETED_CONTRACT_STATUS)) {
            String ledgerApplyId = ledgerAccountVO.getLedgerApplyId();
            //分账申请id 没有，需要重新申请
            if (StringUtils.isEmpty(ledgerApplyId)) {
                String ecContent = ledgerAccountVO.getEcContent();
                LedgerFileVO ledgerFileVO = ledgerFileQueryProvider.getById(LedgerFileByIdRequest.builder()
                        .id(ecContent)
                        .build()).getContext().getLedgerFileVO();
                //附件上传 ec002合同
                BaseResponse uploadFileResponse = ledgerProvider.uploadFile(UploadRequest.builder()
                        .lakalaUploadRequest(LakalaUploadRequest.builder()
                                .attExtName(ledgerFileVO.getFileExt())
                                .attType(AttType.NETWORK_XY.toValue())
                                .attContext(Base64Utils.encodeToString(ledgerFileVO.getContent()))
                                .orderNo(generatorService.generateLedgerOrderNo())
                                .build())
                        .build());
                UploadResponse uploadResponse = BeanUtils.beanCovert(uploadFileResponse.getContext(), UploadResponse.class);
                //附件ID
                String attFileId = uploadResponse.getAttFileId();
                //申请分账
                BaseResponse applySplitMerBaseResponse = ledgerProvider.applySplitMer(ApplySplitMerRequest.builder()
                        .lakalaApplySplitMerRequest(LakalaApplySplitMerRequest.builder()
                                .contactMobile(ledgerAccountVO.getMerContactMobile())
                                .merInnerNo(queryContractResponse.getMerInnerNo())
                                .orderNo(generatorService.generateLedgerOrderNo())
                                .splitEntrustFilePath(attFileId)
                                .build())
                        .build());
                ApplySplitMerResponse applySplitMerResponse = BeanUtils.beanCovert(applySplitMerBaseResponse.getContext(), ApplySplitMerResponse.class);
                //更新清分账户
                LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                        .businessId(ledgerAccountVO.getBusinessId())
                        .accountState(Constants.TWO)
                        .contractId(contractId)
                        .merCupNo(queryContractResponse.getMerCupNo())
                        .thirdMemNo(queryContractResponse.getMerInnerNo())
                        .termNo(queryContractResponse.getTermDatas().stream().findFirst().get().getTermNo())
                        .ledgerApplyId(String.valueOf(applySplitMerResponse.getApplyId()))
                        .build();
                ledgerAccountProvider.updateAccountByContractId(modifyRequest);
            } else {
                //更新清分账户
                LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                        .businessId(ledgerAccountVO.getBusinessId())
                        .accountState(Constants.TWO)
                        .contractId(contractId)
                        .merCupNo(queryContractResponse.getMerCupNo())
                        .thirdMemNo(queryContractResponse.getMerInnerNo())
                        .termNo(queryContractResponse.getTermDatas().stream().findFirst().get().getTermNo())
                        .ledgerApplyId(ledgerAccountVO.getLedgerApplyId())
                        .build();
                ledgerAccountProvider.updateAccountByContractId(modifyRequest);
            }
            try {
                StoreByCompanyInfoIdResponse store = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                        .companyInfoId(Long.valueOf(ledgerAccountVO.getBusinessId()))
                        .build())
                        .getContext();
                if (Objects.nonNull(store) && Objects.nonNull(store.getStoreVO())) {
                    //新增线上业务类型
                    BaseResponse addB2bBusiBaseResponse = ledgerProvider.addB2bBusi(AddB2bBusiRequest.builder()
                            .lakalaAddB2bBusiRequest(LakalaAddB2bBusiRequest.builder()
                                    .merInnerNo(queryContractResponse.getMerInnerNo())
                                    .merCupNo(queryContractResponse.getMerCupNo())
                                    .termNo(queryContractResponse.getTermDatas().stream().findFirst().get().getTermNo())
                                    .orderNo(generatorService.generateLedgerOrderNo())
                                    .build()).build());
                    AddB2bBusiResponse addB2bBusiResponse = BeanUtils.beanCovert(addB2bBusiBaseResponse.getContext(), AddB2bBusiResponse.class);
                    if(StringUtils.isNotBlank(addB2bBusiResponse.getContractId())){
                        //成功，修改账户b2b开通状态
                        LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                                .businessId(ledgerAccountVO.getBusinessId())
                                .b2bAddState(Constants.ONE)
                                .b2bAddApplyId(addB2bBusiResponse.getContractId())
                                .build();
                        ledgerAccountProvider.updateB2bAddStateById(modifyRequest);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (StringUtils.equals(contractStatus, REJECTED_CONTRACT_STATUS)) {
            // 商户进件审核驳回
            //更新清分账户
            LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                    .businessId(ledgerAccountVO.getBusinessId())
                    .accountState(Constants.THREE)
                    .contractId(contractId)
                    .accountRejectReason(queryContractResponse.getContractMemo())
                    .build();
            ledgerAccountProvider.updateAccountByContractId(modifyRequest);
        }
    }


    @GlobalTransactional
    public void executeApplySplitMer(String merInnerNo) {
        BaseResponse baseResponse = ledgerProvider.querySplitMer(QuerySplitMerRequest.builder()
                .lakalaQuerySplitMerRequest(LakalaQuerySplitMerRequest.builder()
                        .merInnerNo(merInnerNo)
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build())
                .build());
        QuerySplitMerResponse querySplitMerResponse = BeanUtils.beanCovert(baseResponse.getContext(), QuerySplitMerResponse.class);
        LedgerAccountVO supplierAccount = ledgerAccountQueryProvider.list(LedgerAccountListRequest.builder()
                .thirdMemNo(merInnerNo)
                .build()).getContext().getLedgerAccountVOList().get(0);
        //如果存在，则更新本地数据，保持同步
        if (Objects.nonNull(querySplitMerResponse) && CollectionUtils.isNotEmpty(querySplitMerResponse.getBindRelations())) {
            // 更新商家清分账户分账审核状态
            ledgerAccountProvider.updateAccountById(LedgerAccountModifyRequest.builder()
                    .businessId(supplierAccount.getBusinessId())
                    .ledgerState(Constants.TWO)
                    .build());
        } else {
            //重新申请分账
            // 更新商家清分账户分账审核状态
            ledgerAccountProvider.updateAccountById(LedgerAccountModifyRequest.builder()
                    .businessId(supplierAccount.getBusinessId())
                    .ledgerState(Constants.TWO)
                    .build());
            //商户分账审核通过后，自动与平台进行分账绑定
            // 获取平台清分账户
            LedgerAccountVO bossAccount = ledgerAccountQueryProvider.getById(LedgerAccountFindRequest.builder()
                    .businessId(String.valueOf(Constants.BOSS_DEFAULT_STORE_ID))
                    .setFileFlag(Boolean.FALSE)
                    .build()).getContext().getLedgerAccountVO();
            //查询 分账协议
            LedgerFileVO ledgerFileVO = ledgerFileQueryProvider.getById(
                            LedgerFileByIdRequest.builder()
                                    .id(supplierAccount.getBindContractId())
                                    .build())
                    .getContext().getLedgerFileVO();
            // 附件上传 分账合作协议
            BaseResponse uploadBaseResponse = ledgerProvider.uploadFile(UploadRequest.builder()
                    .lakalaUploadRequest(LakalaUploadRequest.builder()
                            .attExtName(Constants.PDF)
                            .attType(AttType.NETWORK_XY.toValue())
                            .attContext(Base64Utils.encodeToString(ledgerFileVO.getContent()))
                            .orderNo(generatorService.generateLedgerOrderNo())
                            .build())
                    .build());
            UploadResponse uploadResponse = BeanUtils.beanCovert(uploadBaseResponse.getContext(), UploadResponse.class);
            String attFileId = uploadResponse.getAttFileId();

            //与平台分账绑定
            ApplyBindRequest applyBindRequest = ApplyBindRequest.builder()
                    .lakalaApplyBindRequest(LakalaApplyBindRequest.builder()
                            .merInnerNo(supplierAccount.getThirdMemNo())
                            .receiverNo(bossAccount.getThirdMemNo())
                            .entrustFilePath(attFileId)
                            .orderNo(generatorService.generateLedgerOrderNo())
                            .build())
                    .build();
            ApplyBindResponse applyBindResponse = BeanUtils.beanCovert(ledgerProvider.applyBind(applyBindRequest).getContext(), ApplyBindResponse.class);
            Long bindApplyId = applyBindResponse.getApplyId();
            CompanyInfoByIdResponse companyInfo = companyInfoQueryProvider.getCompanyInfoById(CompanyInfoByIdRequest.builder()
                    .companyInfoId(Long.valueOf(supplierAccount.getBusinessId()))
                    .build()).getContext();
            //初始化分账绑定业务数据
            String id = ledgerSupplierProvider.add(LedgerSupplierAddRequest.builder()
                    .companyCode(companyInfo.getCompanyCode())
                    .companyInfoId(companyInfo.getCompanyInfoId())
                    .companyName(companyInfo.getCompanyName())
                    .distributionNum(BigDecimal.ZERO.longValue())
                    .ledgerAccountId(supplierAccount.getId())
                    .providerNum(BigDecimal.ZERO.longValue())
                    .platBindState(Constants.ZERO)
                    .build()).getContext().getLedgerSupplierVO().getId();
            ledgerReceiverRelProvider.add(LedgerReceiverRelAddRequest.builder()
                    .ledgerSupplierId(id)
                    .applyId(String.valueOf(bindApplyId))
                    .supplierId(Long.valueOf(supplierAccount.getBusinessId()))
                    .receiverId(bossAccount.getBusinessId())
                    .receiverName("平台")
                    .receiverType(Constants.ZERO)
                    .accountState(Constants.TWO)
                    .bindState(Constants.ONE)
                    .bindTime(LocalDateTime.now())
                    .delFlag(DeleteFlag.NO)
                    .build());
            producerService.batchAddLedgerReceiverRel(supplierAccount.getId());
        }
    }

    @GlobalTransactional
    public void executeBind(String applyId) {
        LedgerReceiverRelVO ledgerReceiverRelVO = ledgerReceiverRelQueryProvider.findByApplyId(LedgerReceiverRelListRequest.builder()
                .applyId(applyId)
                .build()).getContext().getLedgerReceiverRelVO();
        Long supplierId = ledgerReceiverRelVO.getSupplierId();
        //获取商户清分账户
        LedgerAccountVO supplierLedgerAccountVO = ledgerAccountQueryProvider.findByBusiness(LedgerAccountFindRequest.builder()
                .businessId(String.valueOf(supplierId))
                .setFileFlag(Boolean.FALSE)
                .build()).getContext().getLedgerAccountVO();
        //获取拉卡拉的内部号
        String thirdMemNo = supplierLedgerAccountVO.getThirdMemNo();
        BaseResponse baseResponse = ledgerProvider.querySplitMer(QuerySplitMerRequest.builder()
                .lakalaQuerySplitMerRequest(LakalaQuerySplitMerRequest.builder()
                        .merInnerNo(thirdMemNo)
                        .orderNo(generatorService.generateLedgerOrderNo())
                        .build())
                .build());
        QuerySplitMerResponse querySplitMerResponse = BeanUtils.beanCovert(baseResponse.getContext(), QuerySplitMerResponse.class);
        List<BindRelation> bindRelations = querySplitMerResponse.getBindRelations();
        String receiverId = ledgerReceiverRelVO.getReceiverId();
        //获取接收方清分账户
        LedgerAccountVO receiverLedgerAccountVO = ledgerAccountQueryProvider.findByBusiness(LedgerAccountFindRequest.builder()
                .businessId(receiverId)
                .setFileFlag(Boolean.FALSE)
                .build()).getContext().getLedgerAccountVO();
        String receiverNo = receiverLedgerAccountVO.getThirdMemNo();
        //查询是否有此绑定关系
        Optional<BindRelation> optional = bindRelations.parallelStream().filter(bindRelation -> bindRelation.getReceiverNo().equals(receiverNo)).findFirst();
        // 如果有则补偿，没有表明还在审核中，无需补偿
        if (optional.isPresent()) {
            //更新绑定状态
            ledgerReceiverRelProvider.updateBindState(LedgerReceiverRelUpdateBindStateRequest.builder()
                    .bindState(Constants.TWO)
                    .bindTime(LocalDateTime.now())
                    .id(ledgerReceiverRelVO.getId())
                    .build());
            //刷新es
            esLedgerBindInfoProvider.init(EsLedgerBindInfoInitRequest.builder()
                    .idList(Lists.newArrayList(ledgerReceiverRelVO.getId()))
                    .build());
            String ledgerSupplierId = ledgerReceiverRelVO.getLedgerSupplierId();
            // 获取接收方的类型
            Integer receiverType = ledgerReceiverRelVO.getReceiverType();
            //如果接收方是平台，则更新平台的绑定状态
            if (receiverType == Constants.ZERO) {
                ledgerSupplierProvider.updatePlatBindStateById(LedgerSupplierModifyRequest.builder()
                        .id(ledgerSupplierId)
                        .platBindState(Constants.ONE)
                        .build());
                //店铺更新拉卡拉状态
                storeProvider.updateLaKaLaState(StoreByCompanyInfoIdRequest.builder()
                        .companyInfoId(ledgerReceiverRelVO.getSupplierId())
                        .build());
                //获取storeId
                Long storeId = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                        .companyInfoId(ledgerReceiverRelVO.getSupplierId())
                        .build()).getContext().getStoreVO().getStoreId();
                //刷新店铺的状态
                esStoreInformationProvider.initStoreInformationList(ESStoreInfoInitRequest.builder()
                        .idList(Lists.newArrayList(storeId)).build());
                //刷新店铺商品的状态
                esGoodsInfoElasticProvider.modifyStore(EsGoodsStoreInfoModifyRequest.builder()
                        .storeId(storeId).storeState(StoreState.OPENING).build());
            }
            //如果是供应商，则更新供应商的数量
            if (receiverType == Constants.ONE) {
                ledgerSupplierProvider.updateProviderNumById(LedgerSupplierModifyRequest.builder()
                        .id(ledgerSupplierId)
                        .build());
            }
            //如果是分销员，则更新分销员的数量
            if (receiverType == Constants.TWO) {
                ledgerSupplierProvider.updateDistributionNumById(LedgerSupplierModifyRequest.builder()
                        .id(ledgerSupplierId)
                        .build());
                Long storeId = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                        .companyInfoId(ledgerReceiverRelVO.getSupplierId())
                        .build()).getContext().getStoreVO().getStoreId();

                DistributorGoodsInfoModifyByStoreIdAndStatusRequest modifyByStoreIdAndStatusRequest =
                        new DistributorGoodsInfoModifyByStoreIdAndStatusRequest();
                modifyByStoreIdAndStatusRequest.setStatus(Constants.ZERO);
                modifyByStoreIdAndStatusRequest.setCustomerId(ledgerReceiverRelVO.getReceiverId());
                modifyByStoreIdAndStatusRequest.setStoreId(storeId);
                distributorGoodsInfoProvider.modifyByStoreIdAndStatusForLaKaLA(modifyByStoreIdAndStatusRequest);
            }
        }

    }
}
