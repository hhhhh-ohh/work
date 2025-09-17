package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailPageRequest;
import com.wanmi.sbc.elastic.bean.vo.customer.EsCustomerDetailVO;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchCreateRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchSendCreateRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.OldSendNewSchoolUniformRequest;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.dto.GiftCardSendCustomerDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.giftcard.model.root.*;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardBatchRepository;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardDetailRepository;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardRepository;
import com.wanmi.sbc.marketing.giftcard.repository.UniformsGiftCardBindingRepository;
import com.wanmi.sbc.marketing.util.common.MarketingGeneratorService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UniformsGiftCardBindingService {

    @Autowired private static final int BATCH_INSERT_MAX_SIZE = 1000;

    @Autowired private UniformsGiftCardBindingRepository uniformsGiftCardBindingRepository;

    @Autowired private GiftCardService giftCardService;

    @Autowired private EsCustomerDetailQueryProvider esCustomerDetailQueryProvider;

    @Autowired private RedissonClient redissonClient;

    @Autowired private GiftCardRepository giftCardRepository;

    @Autowired private GiftCardBatchRepository giftCardBatchRepository;

    @Autowired private GiftCardDetailRepository giftCardDetailRepository;

    @Autowired private GiftCardDetailService giftCardDetailService;

    @Autowired private UserGiftCardService userGiftCardService;

    @Autowired private AuditQueryProvider auditQueryProvider;


    @Autowired private MarketingGeneratorService generatorService;



    public void save(UniformsGiftCardBinding uniformsGiftCardBinding) {
        uniformsGiftCardBindingRepository.save(uniformsGiftCardBinding);
    }

    public UniformsGiftCardBinding findFirstByCityAndSeason(String city, String season) {
        return uniformsGiftCardBindingRepository.findByCityAndSeason(city, season);
    }


    @Transactional(rollbackFor = Exception.class)
    public void OldSendNewSchoolUniformToAccount(OldSendNewSchoolUniformRequest request) {
        log.info("校服小助手旧校服送新校服提货卡请求参数:{}", request);
        UniformsGiftCardBinding uniformsGiftCardBinding = uniformsGiftCardBindingRepository.findByCityAndSeason(request.getCityName(), request.getSeason());
        GiftCard giftCard = giftCardService.checkAndGetForBatchSend(uniformsGiftCardBinding.getGiftCardId());
        try{
            // 构造礼品卡发卡DTO列表
            Map<String, GiftCardSendCustomerDTO> giftCardSendCustomerMap = new HashMap<>();
            // 不存在，构造新DTO存入Map
            GiftCardSendCustomerDTO newSendCustomerDTO = new GiftCardSendCustomerDTO();
            newSendCustomerDTO.setCustomerId(request.getCustomerId());
            newSendCustomerDTO.setSendNum(Long.valueOf(request.getNumber()));
            giftCardSendCustomerMap.put(request.getCustomerAccount(), newSendCustomerDTO);
            // 批量发卡
            GiftCardBatchSendCreateRequest createRequest = new GiftCardBatchSendCreateRequest();
            createRequest.setGiftCardId(giftCard.getGiftCardId());
            createRequest.setSendCustomerList(new ArrayList<>(giftCardSendCustomerMap.values()));
            createRequest.setCreateNum(Long.valueOf(1));
            createRequest.setCreatePerson(request.getCreatePerson());
            createRequest.setExcelFilePath(null);
            // 锁礼品卡id
            String lockKey = CacheKeyConstant.MARKETING_GIFT_CARD_STOCK_LOCK.concat(createRequest.getGiftCardId().toString());
            RLock rLock = redissonClient.getFairLock(lockKey);
            rLock.lock();
            try {
                // 执行发卡
                this.oldsendNewSchoolUniformCreate(createRequest, request);
            }
            finally {
                rLock.unlock();
            }

        } catch (SbcRuntimeException e) {
            log.error("发卡异常", e);
            throw e;
        } catch (Exception e) {
            log.error("发卡异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    private Map<String, String> checkCustomerAccountOne(String customerAccountRequest, Map<String, String> customerAccountAndIdMap) {
        // 收集账号，排除空串，去重
        List<String> customerAccountList =new ArrayList<>();
        customerAccountList.add(customerAccountRequest);
        // map收集错误信息
        Map<String, String> errorMap = new HashMap<>();
        // 已存在的客户账号集合
        Set<String> existCustomerAccountSet = new HashSet<>();
        // 构造客户详情列表请求
        EsCustomerDetailPageRequest customerPageRequest = new EsCustomerDetailPageRequest();
        customerPageRequest.setPageNum(0);
        customerPageRequest.setPageSize(customerAccountList.size());
        customerPageRequest.setCustomerAccountList(customerAccountList);
        customerPageRequest.setFilterAllLogOutStatusFlag(Boolean.TRUE);
        // 查询客户详情列表，默认查找的是未删除的客户
        List<EsCustomerDetailVO> esCustomerList = esCustomerDetailQueryProvider.page(customerPageRequest).getContext().getDetailResponseList();
        if (CollectionUtils.isNotEmpty(esCustomerList)) {
            for (EsCustomerDetailVO detail : esCustomerList) {
                String customerAccount = detail.getCustomerAccount();
                // 1. 客户已禁用
                if (CustomerStatus.DISABLE == detail.getCustomerStatus()) {
                    errorMap.put(customerAccount, "客户已禁用");
                }
                // 2. 已注销
                if (!Long.valueOf(LogOutStatus.NORMAL.toValue())
                        .equals(detail.getLogOutStatus())) {
                    errorMap.put(customerAccount, "客户已注销");
                }
                // 记录已存在的客户账号集合，用于判断客户是否删除
                existCustomerAccountSet.add(customerAccount);
                // 客户账号和id映射map，用于构造发卡请求
                if (Objects.nonNull(customerAccountAndIdMap)) {
                    customerAccountAndIdMap.put(customerAccount, detail.getCustomerId());
                }
            }
        }
        // 3. 客户已删除  不需判断,新账号还没同步到es
        /*customerAccountList.stream()
                .filter(item -> !existCustomerAccountSet.contains(item))
                .forEach(item -> errorMap.put(item, "客户已删除"));*/
        return errorMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public void oldsendNewSchoolUniformCreate(GiftCardBatchSendCreateRequest createRequest,OldSendNewSchoolUniformRequest oldSendNewSchoolUniformRequest) {
        // 1. 校验并获取礼品卡信息(库存、过期)
        GiftCard giftCard = this.checkAndGetGiftCard(createRequest);

        // 2.1 库存有限制时，扣减库存
        if (DefaultFlag.NO == giftCard.getStockType()) {
            int rows = giftCardRepository.subStock(giftCard.getGiftCardId(), createRequest.getCreateNum());
            if (rows < 1) {
                // 库存不足
                throw new SbcRuntimeException();
            }
        }
        // 2.2 追加已发卡数量
        giftCardRepository.subSendNum(giftCard.getGiftCardId(), -createRequest.getCreateNum());



        // 3. 获取发卡审核配置
        boolean auditFlag = this.getAuditFlag(ConfigType.GIFT_CARD_SEND_CARD_AUDIT);

        // 4. 构造发卡批次实体
        GiftCardBatch giftCardBatch = this.generateBasicGiftCardBatch(createRequest, auditFlag);
        giftCardBatch.setExcelFilePath(createRequest.getExcelFilePath());
        giftCardBatch.setBatchType(GiftCardBatchType.SEND);

        // 5.1 生成礼品卡详情列表
        List<GiftCardDetail> giftCardDetails = this.generateGiftDetails4Send(createRequest, giftCard, giftCardBatch, auditFlag);
        // 5.2 为批次赋值起始和结束卡号
        this.fillStartAndEndCardNo(giftCardBatch, giftCardDetails);
        // 5.3 批量保存卡详情、用户礼品卡、交易记录
        AuditStatus auditStatus = auditFlag ? AuditStatus.WAIT_CHECK : AuditStatus.CHECKED;
        this.batchSaveCardDetailsAndCustomerCard4Send(auditStatus, giftCard, giftCardDetails, Collections.emptySet(),oldSendNewSchoolUniformRequest);
        // 5.4 分批保存卡详情
        List<List<GiftCardDetail>> splitList = IterableUtils.splitList(giftCardDetails, BATCH_INSERT_MAX_SIZE);
        for (List<GiftCardDetail> itemList : splitList) {
            giftCardDetailService.batchInsert(itemList);
        }

        // 6. 保存制卡记录
        giftCardBatchRepository.save(giftCardBatch);
    }

    /**
     * @description 校验并获取礼品卡信息
     * @author malianfeng
     * @date 2022/12/9 18:50
     * @param createRequest
     * @return void
     */
    public GiftCard checkAndGetGiftCard(GiftCardBatchCreateRequest createRequest) {
        // 1. 查询礼品卡信息
        GiftCard giftCard = giftCardRepository.findByGiftCardIdAndDelFlag(createRequest.getGiftCardId(), DeleteFlag.NO);
        if (Objects.isNull(giftCard)) {
            // 礼品卡不存在
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        // 2. 校验过期，仅校验 指定具体时间 > 当前时间
        if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()
                && Objects.nonNull(giftCard.getExpirationTime())
                && giftCard.getExpirationTime().isBefore(LocalDateTime.now())) {
            // 礼品卡已过期
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080033);
        }
        // 3. 校验库存
        if (DefaultFlag.NO == giftCard.getStockType() && giftCard.getStock() < createRequest.getCreateNum()) {
            // 库存不足
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080034);
        }
        return giftCard;
    }
    /**
     * 获取制/发卡审核开关，true为要审核，false不要审核
     * @param configType 系统配置KEY
     * @return
     */
    public boolean getAuditFlag(ConfigType configType) {
        TradeConfigGetByTypeRequest tradeConfigGetByTypeRequest = new TradeConfigGetByTypeRequest();
        tradeConfigGetByTypeRequest.setConfigType(configType);
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(tradeConfigGetByTypeRequest).getContext();
        return Objects.nonNull(config) && NumberUtils.INTEGER_ONE.equals(config.getStatus());
    }


    /**
     * @description 生成卡批次实体基本信息
     * @author malianfeng
     * @date 2022/12/10 14:43
     * @param createRequest 生成入参
     * @param auditFlag 审核开关标识
     * @return com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBatch
     */
    public GiftCardBatch generateBasicGiftCardBatch(GiftCardBatchCreateRequest createRequest, boolean auditFlag) {
        GiftCardBatch giftCardBatch = new GiftCardBatch();
        giftCardBatch.setGiftCardId(createRequest.getGiftCardId());
        giftCardBatch.setBatchNo(generatorService.generateGiftCardBatchNo());
        giftCardBatch.setAuditStatus(auditFlag ? AuditStatus.WAIT_CHECK : AuditStatus.CHECKED);
        giftCardBatch.setExportMiniCodeType(createRequest.getExportMiniCodeType());
        giftCardBatch.setExportWebCodeType(createRequest.getExportWebCodeType());
        giftCardBatch.setBatchNum(createRequest.getCreateNum());
        giftCardBatch.setDelFlag(DeleteFlag.NO);
        giftCardBatch.setGeneratePerson(createRequest.getCreatePerson());
        giftCardBatch.setGenerateTime(LocalDateTime.now());
        giftCardBatch.setCreatePerson(createRequest.getCreatePerson());
        giftCardBatch.setCreateTime(LocalDateTime.now());
        return giftCardBatch;
    }

    /**
     * @description 为发卡，生成礼品卡详情列表
     * @author malianfeng
     * @date 2022/12/10 15:14
     * @param request 发卡生成入参
     * @param giftCard 礼品卡信息
     * @param giftCardBatch 卡批次
     * @param auditFlag 审核开关标识
     * @return java.util.List<com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail>
     */
    private List<GiftCardDetail> generateGiftDetails4Send(GiftCardBatchSendCreateRequest request, GiftCard giftCard,
                                                          GiftCardBatch giftCardBatch, boolean auditFlag) {
        GiftCardSendStatus sendStatus = auditFlag ? GiftCardSendStatus.WAITING_SEND : GiftCardSendStatus.SUCCEEDED;
        List<GiftCardDetail> giftCardDetailList = new ArrayList<>(request.getCreateNum().intValue());
        for (GiftCardSendCustomerDTO sendCustomerDTO : request.getSendCustomerList()) {
            Long sendNum = sendCustomerDTO.getSendNum();
            for (long i = 0; i < sendNum; i++) {
                GiftCardDetail giftCardDetail = this.generateBasicGiftCardDetail(request, giftCard, giftCardBatch);
                // 填充来源类型为：发卡
                giftCardDetail.setSourceType(GiftCardSourceType.SEND);
                // 填充所属人
                giftCardDetail.setBelongPerson(sendCustomerDTO.getCustomerId());
                // 填充卡状态为：未激活
                giftCardDetail.setCardDetailStatus(GiftCardDetailStatus.NOT_ACTIVE);
                // 填充发卡状态，待审核为：待发，已审核为：成功
                giftCardDetail.setSendStatus(sendStatus);
                giftCardDetail.setGiftCardType(giftCard.getGiftCardType());
                giftCardDetailList.add(giftCardDetail);
            }
        }
        return giftCardDetailList;
    }

    /**
     * @description 为批次填充起止卡号
     * @author malianfeng
     * @date 2022/12/10 15:15
     * @param giftCardBatch 卡批次
     * @param giftCardDetails 卡详情列表
     * @return void
     */
    private void fillStartAndEndCardNo(GiftCardBatch giftCardBatch, List<GiftCardDetail> giftCardDetails) {
        giftCardBatch.setStartCardNo(giftCardDetails.get(0).getGiftCardNo());
        giftCardBatch.setEndCardNo(giftCardDetails.get(giftCardDetails.size() - 1).getGiftCardNo());
    }

    /**
     * @description 为发卡批次，批量保存用户卡和交易记录
     * @author malianfeng
     * @date 2022/12/10 15:12
     * @param auditStatus 审核状态
     * @param giftCard 礼品卡
     * @param giftCardDetails 礼品卡详情列表
     * @param notAvailableCustomerIds 不可用的用户id集合
     * @return void
     **/
    private void batchSaveCardDetailsAndCustomerCard4Send(AuditStatus auditStatus, GiftCard giftCard,
                                                          List<GiftCardDetail> giftCardDetails,
                                                          Set<String> notAvailableCustomerIds,
                                                          OldSendNewSchoolUniformRequest oldSendNewSchoolUniformRequest) {
        // 用户礼品卡列表
        List<UserGiftCard> userGiftCardList = new ArrayList<>();

        // 1.1 会员不可用，发卡状态为：失败，状态：未兑换
        giftCardDetails.stream()
                .filter(item -> notAvailableCustomerIds.contains(item.getBelongPerson()))
                .forEach(item -> {
                    // 发卡状态为：失败
                    item.setSendStatus(GiftCardSendStatus.FAILED);
                    // 状态为：未兑换
                    item.setCardDetailStatus(GiftCardDetailStatus.NOT_EXCHANGE);
                });

        // 1.2 会员可用，待审核时发卡状态为：待发，已审核时发卡状态为：成功
        giftCardDetails.stream()
                .filter(item -> !notAvailableCustomerIds.contains(item.getBelongPerson()))
                .forEach(item -> {
                    if (AuditStatus.WAIT_CHECK == auditStatus) {
                        // 发卡状态为：待发
                        item.setSendStatus(GiftCardSendStatus.WAITING_SEND);
                    } else if (AuditStatus.CHECKED == auditStatus) {
                        // 发卡状态为：成功
                        item.setSendStatus(GiftCardSendStatus.SUCCEEDED);
                        // 状态为：未激活
                        item.setCardDetailStatus(GiftCardDetailStatus.NOT_ACTIVE);
                        // 构造用户礼品卡
                        userGiftCardList.add(this.generateUserGiftCard(giftCard, item, oldSendNewSchoolUniformRequest));
                    }
                });

        // 2. 已审核时，保存用户礼品卡记录
        if (AuditStatus.CHECKED == auditStatus) {
            // 保存用户礼品卡记录
            List<List<UserGiftCard>> splitList = IterableUtils.splitList(userGiftCardList, BATCH_INSERT_MAX_SIZE);
            for (List<UserGiftCard> itemList : splitList) {
                userGiftCardService.batchInsert(itemList);
            }
        }
    }
    /**
     * @description 生成用户卡实体
     * @author malianfeng
     * @date 2022/12/10 14:32
     * @param giftCard 礼品卡信息
     * @param giftCardDetail 卡详情信息
     * @return com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard
     **/
    public UserGiftCard generateUserGiftCard(GiftCard giftCard, GiftCardDetail giftCardDetail,OldSendNewSchoolUniformRequest oldSendNewSchoolUniformRequest) {
        UserGiftCard userGiftCard = new UserGiftCard();
        userGiftCard.setBatchNo(giftCardDetail.getBatchNo());
        userGiftCard.setGiftCardType(giftCard.getGiftCardType());
        userGiftCard.setCardStatus(GiftCardStatus.NOT_ACTIVE);
        userGiftCard.setGiftCardName(giftCard.getName());
        userGiftCard.setSourceType(giftCardDetail.getSourceType());
        userGiftCard.setGiftCardId(giftCard.getGiftCardId());
        userGiftCard.setCustomerId(giftCardDetail.getBelongPerson());
        userGiftCard.setBelongPerson(giftCardDetail.getBelongPerson());
        userGiftCard.setGiftCardNo(giftCardDetail.getGiftCardNo());
        userGiftCard.setParValue(giftCard.getParValue());
        userGiftCard.setBalance(GiftCardType.PICKUP_CARD.equals(giftCard.getGiftCardType()) ? null : BigDecimal.ZERO);
        userGiftCard.setExpirationType(giftCard.getExpirationType());
        userGiftCard.setExpirationTime(giftCard.getExpirationTime());
        userGiftCard.setAcquireTime(LocalDateTime.now());
        //旧送新 新加参数设置
        userGiftCard.setOrderDetailRetailId(oldSendNewSchoolUniformRequest.getOrderDetailRetailId());
        userGiftCard.setOrderSn(oldSendNewSchoolUniformRequest.getOrderSn());
        userGiftCard.setAppointmentShipmentFlag(oldSendNewSchoolUniformRequest.getAppointmentShipmentFlag());
        return userGiftCard;
    }

    /**
     * @description 生成卡详情实体基本信息
     * @author malianfeng
     * @date 2022/12/10 14:31
     * @param createRequest 生成入参
     * @param giftCard 礼品卡信息
     * @param giftCardBatch 批次信息
     * @return com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail
     */
    public GiftCardDetail generateBasicGiftCardDetail(GiftCardBatchCreateRequest createRequest,
                                                      GiftCard giftCard, GiftCardBatch giftCardBatch) {
        GiftCardDetail giftCardDetail = new GiftCardDetail();
        giftCardDetail.setGiftCardId(createRequest.getGiftCardId());
        giftCardDetail.setGiftCardNo(generatorService.generateGiftCardNo(Constant.SERVICE_ID, Constant.MID));
        giftCardDetail.setBatchNo(giftCardBatch.getBatchNo());
        giftCardDetail.setDelFlag(DeleteFlag.NO);
        giftCardDetail.setCreatePerson(createRequest.getCreatePerson());
        giftCardDetail.setCreateTime(LocalDateTime.now());
        giftCardDetail.setExpirationTime(giftCard.getExpirationTime());
        return giftCardDetail;
    }
}
