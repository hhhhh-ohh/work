package com.wanmi.sbc.ledger;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.LakalaCommonConstant;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
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
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileModifyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierAddRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelByIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByCompanyInfoIdResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
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


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 分账回调
 * Created by sunkun on 2017/8/8.
 */
@Tag(name = "LedgeCallbackController", description = "分账回调")
@RestController
@Validated
@RequestMapping("/ledgeCallback")
@Slf4j
public class LedgeCallbackController {

    @Autowired
    private LedgerProvider ledgerProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private LedgerFileProvider ledgerFileProvider;

    @Autowired
    private LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Autowired
    private LedgerSupplierProvider ledgerSupplierProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProducerService producerService;


    @Autowired
    private EsLedgerBindInfoProvider esLedgerBindInfoProvider;

    @Autowired
    private DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private LedgerFileQueryProvider ledgerFileQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;
    /***
     * 拉卡拉 电子合同申请回调
     * @date 10:30 2021/3/15
     * @author zhangyong
     * @param request
     * @param
     * @return
     */
    @Operation(summary = "拉卡拉 电子合同申请回调")
    @RequestMapping(value = "/ec/applyCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public JSONObject ecApplyCallBack(HttpServletRequest request)  {
        log.info("拉卡拉电子合同申请回成功");
        RLock rLock = null;
        try {
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("拉卡拉电子合同申请回调验成功,参数为{}",respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = true;
                    //VerifySignUtils.verify(authorization, respStr);
            if (verify) {
                log.info("拉卡拉电子合同申请回调验签成功,参数为{}",respStr);
                JSONObject jsonObject = JSONObject.parseObject(respStr);
                //获取电子签约申请受理编号
                Long ecApplyId = jsonObject.getLong("ecApplyId");
                // 申请订单号
                String orderNo = jsonObject.getString("orderNo");
                // 电子签约申请结果H5
                String resultUrl = jsonObject.getString("resultUrl");
                //回调保存到redis中，用于回溯及补偿
                String redisKey = null;
                if (orderNo.startsWith(LakalaCommonConstant.EC003)){
                    redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("ec003ApplyCallBack");
                } else {
                    redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("ecApplyCallBack");
                }
                redisUtil.hset(redisKey,String.valueOf(ecApplyId),respStr);
                //获取电子合同状态
                String ecStatus = jsonObject.getString("ecStatus");
                //如果电子合同已完成，则正确回调
                if (VerifySignUtils.COMPLETED_EC_STATUS.equals(ecStatus)) {
                    // 加锁
                    rLock = redissonClient.getFairLock(String.valueOf(ecApplyId));
                    rLock.lock();
                    LedgerAccountVO ledgerAccountVO = null;
                    BaseResponse baseResponse;
                    // 判断合同类型
                    if (orderNo.startsWith(LakalaCommonConstant.EC003)){
                        LedgerReceiverRelByIdResponse ledgerReceiverRelByIdResponse =
                                ledgerReceiverRelQueryProvider.findByEcApplyId(LedgerReceiverEcApplyRequest.builder()
                                        .ecApplyId(String.valueOf(ecApplyId)).build()).getContext();
                        LedgerReceiverRelVO ledgerReceiverRelVO =
                                ledgerReceiverRelByIdResponse.getLedgerReceiverRelVO();
                        //下载电子合同
                        baseResponse = ledgerProvider.ecDownLoad(EcDownloadRequest.builder()
                                .lakalaEcDownloadRequest(LakalaEcDownloadRequest.builder()
                                        .orderNo(generatorService.generateEc003OrderNo())
                                        .ecApplyId(ecApplyId)
                                        .build())
                                .build());
                    } else {
                        ledgerAccountVO =
                                ledgerAccountQueryProvider.findByEcApplyId(LedgerAccountListRequest.builder()
                                .ecApplyId(String.valueOf(ecApplyId))
                                .build()).getContext().getLedgerAccountVO();
                        //下载电子合同
                        baseResponse = ledgerProvider.ecDownLoad(EcDownloadRequest.builder()
                                .lakalaEcDownloadRequest(LakalaEcDownloadRequest.builder()
                                        .orderNo(generatorService.generateLedgerOrderNo())
                                        .ecApplyId(ecApplyId)
                                        .build())
                                .build());
                    }


                    if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
                        EcDownloadResponse ecDownloadResponse = BeanUtils.beanCovert(baseResponse.getContext(),EcDownloadResponse.class);
                        //获取电子合同的字节数组
                        byte[] content = Base64Utils.decodeFromUrlSafeString(ecDownloadResponse.getEcFile());
                        //保存文件后获取fileId
                        String fileId = ledgerFileProvider.add(LedgerFileAddRequest.builder()
                                .fileExt(Constants.PDF)
                                .content(content)
                                .build()).getContext().getFileId();

                        if (orderNo.startsWith(LakalaCommonConstant.EC003)){
                            ledgerReceiverRelProvider.updateEC003Info(UpdateEC003InfoRequest.builder()
                                    .ecContentId(fileId)
                                    .ecNO(ecDownloadResponse.getEcNo())
                                    .ecUrl(resultUrl)
                                    .ecApplyId(String.valueOf(ecApplyId))
                                    .build());
                        } else {
                            //保存信息到分账账户中
                            if (Objects.nonNull(ledgerAccountVO) && StringUtils.isNotBlank(ledgerAccountVO.getBusinessId())) {
                                ledgerAccountProvider.updateEcContentByBusinessId(LedgerAccountModifyRequest.builder()
                                        .businessId(ledgerAccountVO.getBusinessId())
                                        .ecApplyId(String.valueOf(ecApplyId))
                                        .ecNo(ecDownloadResponse.getEcNo())
                                        .ecContent(fileId)
                                        .build());
                            }
                        }
                        //这里成功后，删除redis记录
                        redisUtil.hdelete(redisKey,String.valueOf(ecApplyId));
                        return VerifySignUtils.getSuccessResponse();
                    }
                }
            } else {
                log.error("验签失败");
            }
        } catch (Exception e) {
            log.error("lakala  call back io exception", e);
        } finally {
            if (Objects.nonNull(rLock)){
                rLock.unlock();
            }
        }

        return VerifySignUtils.getFailResponse();
    }


    /***
     * 拉卡拉 商户进件申请回调
     * @date 10:30 2021/3/15
     * @author zhangyong
     * @param request
     * @param
     * @return
     */
    @Operation(summary = "拉卡拉 商户进件申请回调")
    @RequestMapping(value = "/addMerCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public JSONObject addMerCallBack(HttpServletRequest request) {
        log.info("拉卡拉商户进件申请回调成功");
        RLock rLock = null;
        try {
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("拉卡拉商户进件申请回调成功,参数为{}",respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = true;
                    //VerifySignUtils.verify(authorization, respStr);
            if (verify) {
                log.info("拉卡拉商户进件申请回调验签成功,参数为{}",respStr);
                JSONObject jsonObject = JSONObject.parseObject(respStr);
                //开始解析报文
                JSONObject data = jsonObject.getJSONObject("data");
                String code = jsonObject.getString("code");
                String contractId = data.getString("contractId");
                //回调保存到redis中，用于回溯及补偿
                String redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("addMerCallBack");
                redisUtil.hset(redisKey,contractId,respStr);
                if (VerifySignUtils.CALL_BACK_SUCCESS.equals(code)) {
                    // 加锁
                    rLock = redissonClient.getFairLock(contractId);
                    rLock.lock();
                    //查询清分账户
                    LedgerAccountListRequest ledgerAccountListRequest = LedgerAccountListRequest.builder()
                            .contractId(contractId)
                            .build();
                    LedgerAccountVO ledgerAccountVO = ledgerAccountQueryProvider
                            .findByContractId(ledgerAccountListRequest).getContext().getLedgerAccountVO();
                    // 获取商户开户审核状态
                    Integer accountState = ledgerAccountVO.getAccountState();
                    // 如果是审核成功 或者 审核失败，代表已经处理过，并告知拉卡拉已经处理成功，不用继续推送
                    if (accountState == Constants.TWO || accountState == Constants.THREE) {
                        redisUtil.hdelete(redisKey,contractId);
                        log.info("拉卡拉商户进件申请已经处理,无需再次处理，LedgerAccountVO数据为{}",ledgerAccountVO);
                        return VerifySignUtils.getSuccessResponse();
                    }
                    String contractStatus = data.getString("contractStatus");
                    // 商户进件审核成功
                    if (StringUtils.equals(contractStatus,"WAIT_FOR_CONTACT")) {
                        JSONArray termDatas = data.getJSONArray("termDatas");
//                        JSONObject termData = termDatas.getJSONObject(0);
                        List<TermData> termDataList = JSONArray.parseArray(termDatas.toJSONString(), TermData.class);
                        //银行卡终端
                        Optional<TermData> optionalBankTermData = termDataList.stream().filter(termData -> StringUtils.equals(termData.getBusiTypeCode(), "BANK_CARD")).findFirst();
                        //扫码终端
                        Optional<TermData> optionalTermData = termDataList.stream().filter(termData -> StringUtils.equals(termData.getBusiTypeCode(), "QR_CODE_CARD")).findFirst();
                        String bankCardTermNo = optionalBankTermData.map(TermData::getTermNo).orElse("");
                        String qrTermNo = optionalTermData.map(TermData::getTermNo).orElse("");
                        //网银终端
                        Optional<TermData> eBankTermData = termDataList.stream().filter(termData -> StringUtils.equals(termData.getBusiTypeCode(), "E_BANK")).findFirst();
                        String eBankTermNo = eBankTermData.map(TermData::getTermNo).orElse("");
                        String ecContent = ledgerAccountVO.getEcContent();
                        LedgerFileVO ledgerFileVO = ledgerFileQueryProvider.getById(LedgerFileByIdRequest.builder()
                                .id(ecContent)
                                .build()).getContext().getLedgerFileVO();
                        byte[] content = ledgerFileVO.getContent();
                        //下载电子合同
                        BaseResponse ecDownLoad = ledgerProvider.ecDownLoad(EcDownloadRequest.builder()
                                .lakalaEcDownloadRequest(LakalaEcDownloadRequest.builder()
                                        .orderNo(generatorService.generateLedgerOrderNo())
                                        .ecApplyId(Long.valueOf(ledgerAccountVO.getEcApplyId()))
                                        .build())
                                .build());
                        if (CommonErrorCodeEnum.K000000.getCode().equals(ecDownLoad.getCode())) {
                            EcDownloadResponse ecDownloadResponse = BeanUtils.beanCovert(ecDownLoad.getContext(),EcDownloadResponse.class);
                            //获取电子合同的字节数组
                            content = Base64Utils.decodeFromUrlSafeString(ecDownloadResponse.getEcFile());
                            //更新
                            ledgerFileProvider.modify(LedgerFileModifyRequest.builder()
                                    .content(content)
                                    .id(ledgerFileVO.getId())
                                    .build()).getContext();
                        }
                        //附件上传 ec005合同
                        BaseResponse uploadFileResponse = ledgerProvider.uploadFile(UploadRequest.builder()
                                .lakalaUploadRequest(LakalaUploadRequest.builder()
                                        .attExtName(ledgerFileVO.getFileExt())
                                        .attType(AttType.NETWORK_XY.toValue())
                                        .attContext(Base64Utils.encodeToString(content))
                                        .orderNo(generatorService.generateLedgerOrderNo())
                                        .build())
                                .build());
                        UploadResponse uploadResponse = BeanUtils.beanCovert(uploadFileResponse.getContext(), UploadResponse.class);
                        //附件ID
                        String attFileId = uploadResponse.getAttFileId();
                        //申请分账
                        BaseResponse baseResponse = ledgerProvider.applySplitMer(ApplySplitMerRequest.builder()
                                .lakalaApplySplitMerRequest(LakalaApplySplitMerRequest.builder()
                                        .contactMobile(ledgerAccountVO.getMerContactMobile())
                                        .merInnerNo(data.getString("merInnerNo"))
                                        .orderNo(generatorService.generateLedgerOrderNo())
                                        .splitEntrustFilePath(attFileId)
                                        .build())
                                .build());
                        ApplySplitMerResponse applySplitMerResponse = BeanUtils.beanCovert(baseResponse.getContext(),ApplySplitMerResponse.class);
                        //更新清分账户
                        LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                                .businessId(ledgerAccountVO.getBusinessId())
                                .accountState(Constants.TWO)
                                .passDateTime(LocalDateTime.now())
                                .contractId(contractId)
                                .merCupNo(data.getString("merCupNo"))
                                .thirdMemNo(data.getString("merInnerNo"))
                                .termNo(qrTermNo)
                                .bankTermNo(bankCardTermNo)
                                .unionTermNo(eBankTermNo)
                                .ledgerApplyId(String.valueOf(applySplitMerResponse.getApplyId()))
                                .build();
                        ledgerAccountProvider.updateAccountByContractId(modifyRequest);
                        redisUtil.hdelete(redisKey,contractId);
                        try {
                            StoreByCompanyInfoIdResponse store = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                                            .companyInfoId(Long.valueOf(ledgerAccountVO.getBusinessId()))
                                            .build())
                                    .getContext();
                            if (Objects.nonNull(store) && Objects.nonNull(store.getStoreVO())) {
                                //新增线上业务类型
                                BaseResponse addB2bBusiBaseResponse = ledgerProvider.addB2bBusi(AddB2bBusiRequest.builder()
                                        .lakalaAddB2bBusiRequest(LakalaAddB2bBusiRequest.builder()
                                                .merInnerNo(data.getString("merInnerNo"))
                                                .merCupNo(data.getString("merCupNo"))
                                                .termNo(bankCardTermNo)
                                                .orderNo(generatorService.generateLedgerOrderNo())
                                                .build()).build());
                                AddB2bBusiResponse addB2bBusiResponse = BeanUtils.beanCovert(addB2bBusiBaseResponse.getContext(), AddB2bBusiResponse.class);
                                if(StringUtils.isNotBlank(addB2bBusiResponse.getContractId())){
                                    //成功，修改账户b2b开通状态
                                    LedgerAccountModifyRequest modifyRequest1 = LedgerAccountModifyRequest.builder()
                                            .businessId(ledgerAccountVO.getBusinessId())
                                            .b2bAddState(Constants.ONE)
                                            .b2bAddApplyId(addB2bBusiResponse.getContractId())
                                            .build();
                                    ledgerAccountProvider.updateB2bAddStateById(modifyRequest1);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return VerifySignUtils.getSuccessResponse();
                    } else if (StringUtils.equals(contractStatus,"INNER_CHECK_REJECTED")) {
                        // 商户进件审核驳回
                        //更新清分账户
                        LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                                .businessId(ledgerAccountVO.getBusinessId())
                                .accountState(Constants.THREE)
                                .contractId(contractId)
                                .accountRejectReason(data.getString("contractMemo"))
                                .build();
                        ledgerAccountProvider.updateAccountByContractId(modifyRequest);
                        redisUtil.hdelete(redisKey,contractId);
                        return VerifySignUtils.getSuccessResponse();
                    } else {
                        //其他状态不用处理
                        redisUtil.hdelete(redisKey,contractId);
                        return VerifySignUtils.getSuccessResponse();
                    }
                }

            } else {
                log.error("验签失败");
            }
        } catch (Exception e) {
            log.error("lakala  call back io exception", e);
        } finally {
            if (Objects.nonNull(rLock)){
                rLock.unlock();
            }
        }

        return VerifySignUtils.getFailResponse();
    }


    /***
     * 拉卡拉 商户分账开通回调
     * @date 10:30 2021/3/15
     * @author zhangyong
     * @param request
     * @param
     * @return
     */
    @Operation(summary = "拉卡拉 商户分账开通回调")
    @RequestMapping(value = "/applySplitMerCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public JSONObject applySplitMerCallBack(HttpServletRequest request) {
        log.info("拉卡拉商户分账开通回调成功");
        RLock rLock = null;
        try {
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("拉卡拉商户分账开通回调成功,参数为{}",respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = true;
                    //VerifySignUtils.verify(authorization, respStr);
            if (verify) {
                log.info("拉卡拉商户分账开通回调验签成功,参数为{}",respStr);
                JSONObject resp = JSONObject.parseObject(respStr);
                JSONObject respData;
                if (resp.containsKey("respData")) {
                    respData = resp.getJSONObject("respData");
                } else {
                    respData = resp;
                }
                String auditStatus = respData.getString("auditStatus");
                // 获取受理id
                String applyId =String.valueOf(respData.getLong("applyId"));
                // 获取 商家清分账户
                LedgerAccountVO supplierAccount = ledgerAccountQueryProvider.findByLedgerApplyId(LedgerAccountListRequest.builder()
                        .ledgerApplyId(applyId)
                        .build()).getContext().getLedgerAccountVO();
                //如果审核通过，更新账户，暂无审核不通过的问题
                if (StringUtils.equals(String.valueOf(Constants.ONE),auditStatus)) {
                    //回调保存到redis中，用于回溯及补偿
                    String redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("applySplitMerCallBack");
                    redisUtil.hset(redisKey,applyId,respStr);
                    // 加锁
                    rLock = redissonClient.getFairLock(applyId);
                    rLock.lock();
                    // 获取分账审核状态
                    Integer ledgerState = supplierAccount.getLedgerState();
                    // 如果是开通的状态，代表已经处理过，并告知拉卡拉已经处理成功，不用继续推送
                    if (ledgerState == Constants.TWO) {
                        redisUtil.hdelete(redisKey,applyId);
                        log.info("拉卡拉商户分账开通已经成功,无需处理，LedgerAccountVO数据为{}",supplierAccount);
                        return VerifySignUtils.getSuccessResponse();
                    }
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
                    BaseResponse baseResponse = ledgerProvider.uploadFile(UploadRequest.builder()
                            .lakalaUploadRequest(LakalaUploadRequest.builder()
                                    .attExtName(Constants.PDF)
                                    .attType(AttType.NETWORK_XY.toValue())
                                    .attContext(Base64Utils.encodeToString(ledgerFileVO.getContent()))
                                    .orderNo(generatorService.generateLedgerOrderNo())
                                    .build())
                            .build());
                    UploadResponse uploadResponse = BeanUtils.beanCovert(baseResponse.getContext(), UploadResponse.class);
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
                    ApplyBindResponse applyBindResponse = BeanUtils.beanCovert(ledgerProvider.applyBind(applyBindRequest).getContext(),ApplyBindResponse.class);
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
                    LedgerReceiverRelVO rel = ledgerReceiverRelProvider.add(LedgerReceiverRelAddRequest.builder()
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
                            .build()).getContext().getLedgerReceiverRelVO();
                    producerService.batchAddLedgerReceiverRel(supplierAccount.getId());
                    redisUtil.hdelete(redisKey,applyId);
                    producerService.esAddLedgerBindInfo(Lists.newArrayList(rel));
                } else {
                    // 更新商家清分账户分账审核状态
                    ledgerAccountProvider.updateAccountById(LedgerAccountModifyRequest.builder()
                            .businessId(supplierAccount.getBusinessId())
                            .ledgerState(Constants.THREE)
                            .ledgerRejectReason(respData.getString("remark"))
                            .build());
                }
                return VerifySignUtils.getSuccessResponse();
            } else {
                log.error("验签失败");
            }
        } catch (Exception e) {
            log.error("lakala  call back io exception", e);
        } finally {
            if (Objects.nonNull(rLock)){
                rLock.unlock();
            }
        }

        return VerifySignUtils.getFailResponse();
    }


    /***
     * 拉卡拉 分账绑定回调
     * @date 10:30 2021/3/15
     * @author zhangyong
     * @param request
     * @param
     * @return
     */
    @Operation(summary = "/拉卡拉 分账绑定回调")
    @RequestMapping(value = "/applyBindCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public JSONObject applyBindCallBackUrl(HttpServletRequest request) {
        log.info("拉卡拉分账绑定回调成功");
        RLock rLock = null;
        try {
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("拉卡拉分账绑定回调成功,参数为{}",respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = true;
                    //VerifySignUtils.verify(authorization, respStr);
            if (verify) {
                log.info("拉卡拉分账绑定回调验签成功,参数为{}",respStr);
                JSONObject resp = JSONObject.parseObject(respStr);
                // 获取受理id
                String applyId =String.valueOf(resp.getLong("applyId"));
                Integer auditStatus = resp.getInteger("auditStatus");
                String remark = resp.getString("remark");
                //回调保存到redis中，用于回溯及补偿
                String redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("applyBindCallBack");
                redisUtil.hset(redisKey,applyId,respStr);
                // 加锁
                rLock = redissonClient.getFairLock(applyId);
                rLock.lock();
                LedgerReceiverRelVO ledgerReceiverRelVO = ledgerReceiverRelQueryProvider.findByApplyId(LedgerReceiverRelListRequest.builder()
                        .applyId(applyId)
                        .build()).getContext().getLedgerReceiverRelVO();
                Integer bindState = ledgerReceiverRelVO.getBindState();
                //如果分账绑定已经通过，则不用处理，并告知拉卡拉已经处理成功，不用继续推送
                if (bindState == Constants.TWO) {
                    redisUtil.hdelete(redisKey,applyId);
                    log.info("拉卡拉分账绑定已经成功,无需处理，ledgerReceiverRelVO数据为{}",ledgerReceiverRelVO);
                    return VerifySignUtils.getSuccessResponse();
                }
                //更新绑定状态
                bindState = auditStatus == Constants.ONE ? LedgerBindState.BINDING.toValue() : LedgerBindState.BINDING_FAILURE.toValue();
                String rejectReason = auditStatus == Constants.ONE ? null : remark;
                ledgerReceiverRelProvider.updateBindState(LedgerReceiverRelUpdateBindStateRequest.builder()
                        .bindState(bindState)
                        .rejectReason(rejectReason)
                        .bindTime(LocalDateTime.now())
                        .id(ledgerReceiverRelVO.getId())
                        .build());
                //刷新es
                esLedgerBindInfoProvider.init(EsLedgerBindInfoInitRequest.builder()
                        .idList(Lists.newArrayList(ledgerReceiverRelVO.getId()))
                        .build());
                if (LedgerBindState.BINDING.toValue() == bindState) {
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
                redisUtil.hdelete(redisKey,applyId);
                return VerifySignUtils.getSuccessResponse();
            } else {
                log.error("验签失败");
            }
        } catch (Exception e) {
            log.error("lakala  call back io exception", e);
        } finally {
            if (Objects.nonNull(rLock)){
                rLock.unlock();
            }
        }

        return VerifySignUtils.getFailResponse();
    }

    /***
     * 拉卡拉 新增线上业务类型回调
     */
    @Operation(summary = "拉卡拉 新增线上业务类型回调")
    @RequestMapping(value = "/addB2bBusiCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public JSONObject addB2bBusiCallBack(HttpServletRequest request) {
        log.info("拉卡拉商户新增线上业务类型回调成功");
        RLock rLock = null;
        try {
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("拉卡拉商户新增线上业务类型回调成功,参数为{}",respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = true;
                    //VerifySignUtils.verify(authorization, respStr);
            if (verify) {
                log.info("拉卡拉商户新增线上业务类型回调验签成功,参数为{}",respStr);
                JSONObject jsonObject = JSONObject.parseObject(respStr);
                //开始解析报文
                JSONObject data = jsonObject.getJSONObject("data");
                String code = jsonObject.getString("code");
                String contractId = data.getString("contractId");
                //回调保存到redis中，用于回溯及补偿
                String redisKey = RedisKeyConstant.LEDGER_CALL_BACK.concat("addB2bBusiCallBack");
                redisUtil.hset(redisKey,contractId,respStr);
                if (VerifySignUtils.CALL_BACK_SUCCESS.equals(code)) {
                    // 加锁
                    rLock = redissonClient.getFairLock(contractId);
                    rLock.lock();
                    //查询清分账户
                    LedgerAccountListRequest ledgerAccountListRequest = LedgerAccountListRequest.builder()
                            .b2bAddApplyId(contractId)
                            .build();
                    LedgerAccountVO ledgerAccountVO = ledgerAccountQueryProvider
                            .findByB2bAddApplyId(ledgerAccountListRequest).getContext().getLedgerAccountVO();
                    // 获取商户新增线上业务类型审核状态
                    Integer accountState = ledgerAccountVO.getB2bAddState();
                    // 如果是审核成功 或者 审核失败，代表已经处理过，并告知拉卡拉已经处理成功，不用继续推送
                    if (accountState == Constants.TWO || accountState == Constants.THREE) {
                        redisUtil.hdelete(redisKey,contractId);
                        log.info("拉卡拉商户新增线上业务类型已经处理,无需再次处理，LedgerAccountVO数据为{}",ledgerAccountVO);
                        return VerifySignUtils.getSuccessResponse();
                    }
                    String contractStatus = data.getString("contractStatus");
                    // 审核成功
                    if (StringUtils.equals(contractStatus,"WAIT_FOR_CONTACT")) {
                        LedgerAccountModifyRequest modifyRequest1 = LedgerAccountModifyRequest.builder()
                                .businessId(ledgerAccountVO.getBusinessId())
                                .b2bAddState(Constants.TWO)
                                .b2bAddApplyId(contractId)
                                .build();
                        ledgerAccountProvider.updateB2bAddStateById(modifyRequest1);
                        redisUtil.hdelete(redisKey,contractId);
                        return VerifySignUtils.getSuccessResponse();
                    } else if(!StringUtils.equals(contractStatus,"MANUAL_AUDIT")) {
                        // 审核驳回
                        //更新清分账户
                        LedgerAccountModifyRequest modifyRequest1 = LedgerAccountModifyRequest.builder()
                                .businessId(ledgerAccountVO.getBusinessId())
                                .b2bAddState(Constants.THREE)
                                .b2bAddApplyId(contractId)
                                .build();
                        ledgerAccountProvider.updateB2bAddStateById(modifyRequest1);
                        //其他状态不用处理
                        redisUtil.hdelete(redisKey,contractId);
                        return VerifySignUtils.getSuccessResponse();
                    }
                }

            } else {
                log.error("验签失败");
            }
        } catch (Exception e) {
            log.error("lakala  call back io exception", e);
        } finally {
            if (Objects.nonNull(rLock)){
                rLock.unlock();
            }
        }

        return VerifySignUtils.getFailResponse();
    }
}
