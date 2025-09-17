package com.wanmi.sbc.account.credit.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.constant.AccountConstants;
import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CreditRepayDetailResponse;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.repository.CreditOrderRepository;
import com.wanmi.sbc.account.credit.repository.CreditRepayRepository;
import com.wanmi.sbc.account.message.StoreMessageBizService;
import com.wanmi.sbc.account.mq.CreditAccountOutService;
import com.wanmi.sbc.account.mq.CreditProducerMqService;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.CreditStateChangeType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/3/2 17:02
 * @description
 *     <p>授信还款service
 */
@Service
public class CreditRepayQueryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private CreditRepayRepository creditRepayRepository;

    @Autowired private CreditOrderRepository creditOrderRepository;

    @Autowired private GeneratorService generatorService;

    @Autowired private CreditProducerMqService creditProducerMqService;

    @Autowired private CreditAccountRepository creditAccountRepository;

    @Autowired private RedissonClient redissonClient;

    @Autowired private CreditAccountOutService creditAccountSink;

    @Autowired private StoreMessageBizService storeMessageBizService;

    /**
     * 查询授信还款信息
     *
     * @param request
     * @return
     */
    public MicroServicePage<CreditRepayPageResponse> findCreditHasRepaidPage(
            CreditRepayPageRequest request) {

        // 查询当前周期还款单号
        List<String> repayOrderCodeList = creditOrderRepository.findRepayOrderCode(request);

        // 如果没有取到订单中的还款单号，代表并没有关联订单还款，则直接返回空
        if (CollectionUtils.isEmpty(repayOrderCodeList)) {
            return new MicroServicePage<>(
                    Collections.emptyList(), request.getPageable(), NumberUtils.LONG_ZERO);
        }
        Optional<CustomerCreditAccount> accountOptional =
                creditAccountRepository.findByCustomerIdAndDelFlag(
                        request.getCustomerId(), DeleteFlag.NO);
        if (!accountOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        CustomerCreditAccount creditAccount = accountOptional.get();
        request.setRepayOrderCodeList(repayOrderCodeList);
        // 排序
        PageRequest pageable = request.getPageable();
        // 获取数据
        Specification<CustomerCreditRepay> condition =
                CreditRepayWhereCriteriaBuilder.build(request);
        Page<CustomerCreditRepay> creditRepayPage =
                creditRepayRepository.findAll(condition, pageable);

        Page<CreditRepayPageResponse> newPage =
                creditRepayPage.map(
                        target -> {
                            CreditRepayPageResponse source =
                                    CreditRepayPageResponse.builder().build();
                            BeanUtils.copyProperties(target, source);
                            source.setCreditAmount(creditAccount.getCreditAmount());
                            Long effectiveDays =
                                    this.getEffectiveDays(
                                            creditAccount.getStartTime(),
                                            creditAccount.getEndTime());
                            source.setEffectiveDays(effectiveDays);
                            return source;
                        });
        return new MicroServicePage<>(newPage, request.getPageable());
    }

    /**
     * 查询订单详情列表
     *
     * @param request
     * @return
     */
    public MicroServicePage<CreditRepayDetailResponse> getCreditRepay(
            CreditRepayDetailRequest request) {
        // 查询条件
        String repayOrderCode = request.getRepayOrderCode();
        // 分页参数
        // 获取数据
        PageRequest pageable = request.getPageable();
        // 因封装的排序无用，所以用的源生排序方式
        Sort createTime = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), createTime);
        Page<CustomerCreditOrder> creditOrderPage =
                creditOrderRepository.findByRepayOrderCode(repayOrderCode, pageRequest);
        Page<CreditRepayDetailResponse> newPage =
                creditOrderPage.map(
                        target ->
                                CreditRepayDetailResponse.builder()
                                        .orderNo(target.getOrderId())
                                        .build());
        return new MicroServicePage<>(newPage, pageable);
    }

    /**
     * 根据客户id查询该客户存在的待还款的记录，如果存在待还款记录则每个客户的待还款记录只能有一条
     *
     * @param customerId
     * @return CustomerCreditRepay
     * @author chenli
     * @date 2021/3/3 17:44
     */
    public CustomerCreditRepay findCreditRepayByCustomerId(String customerId) {
        return creditRepayRepository.findByCustomerIdAndRepayStatusAndDelFlag(
                customerId, CreditRepayStatus.getCheckStatus(), DeleteFlag.NO);
    }

    /**
     * * 根据订单查询还款单号和还款状态
     *
     * @param request
     * @return
     */
    public CreditRepayPageResponse findRepayOrderByOrderId(CreditOrderQueryRequest request) {
        Optional<CustomerCreditRepay> creditRepayOptional =
                creditRepayRepository.findRepayOrderCodeByOrderId(request.getOrderId());

        CreditRepayPageResponse response;
        if (creditRepayOptional.isPresent()) {
            response =
                    com.wanmi.sbc.common.util.BeanUtils.beanCopy(
                            creditRepayOptional.get(), CreditRepayPageResponse.class);
        } else {
            response = new CreditRepayPageResponse();
        }
        return response;
    }

    /**
     * * 分页查询授信还款概览列表
     *
     * @param request
     * @return
     */
    public MicroServicePage<CreditRepayOverviewPageResponse> findRepayOrderPage(
            CreditRepayOverviewPageRequest request) {
        Specification<CustomerCreditRepay> condition =
                CreditRepayListWhereCriteriaBuilder.build(request);
        // 分页参数
        PageRequest pageable = request.getPageable();
        Page<CustomerCreditRepay> creditRepayPage =
                creditRepayRepository.findAll(condition, pageable);
        Page<CreditRepayOverviewPageResponse> newPage =
                creditRepayPage.map(
                        target -> {
                            CreditRepayOverviewPageResponse source =
                                    CreditRepayOverviewPageResponse.builder().build();
                            BeanUtils.copyProperties(target, source);
                            CustomerCreditAccount creditAccount = target.getCustomerCreditAccount();
                            source.setCustomerAccount(creditAccount.getCustomerAccount());
                            source.setCustomerName(creditAccount.getCustomerName());
                            return source;
                        });
        return new MicroServicePage<>(newPage, pageable);
    }

    /** 新增客户授信还款 */
    @Transactional(rollbackFor = Exception.class)
    public CustomerCreditRepay add(CustomerCreditRepay entity) {
        creditRepayRepository.save(entity);
        return entity;
    }

    /** 修改客户授信还款 */
    @Transactional(rollbackFor = Exception.class)
    public CustomerCreditRepay modify(CustomerCreditRepay entity) {
        creditRepayRepository.save(entity);
        return entity;
    }

    /**
     * 列表查询客户授信还款
     *
     * @author zhongjichuan
     */
    public List<CustomerCreditRepay> list(CustomerCreditRepayQueryRequest queryReq) {
        return creditRepayRepository.findAll(
                CustomerCreditRepayWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 新增客户授信还款
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public CustomerCreditRepay addCreditRepay(CustomerCreditRepayAddRequest request) {
        logger.info("新增客户授信还款:{}", JSONObject.toJSONString(request));
        // 判断是否存在还款中还款记录
        CustomerCreditRepay repayOptional =
                creditRepayRepository.findByCustomerIdAndRepayStatusAndDelFlag(
                        request.getCustomerId(), CreditRepayStatus.getCheckStatus(), DeleteFlag.NO);
        // 您有一笔还款中的还款单，请完成后再进行新的还款申请
        if (Objects.nonNull(repayOptional)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020017);
        }
        // 新增授信在线还款申请
        CustomerCreditRepay customerCreditRepay =
                KsBeanUtil.convert(request, CustomerCreditRepay.class);
        customerCreditRepay.setCreatePerson(request.getCustomerId());
        customerCreditRepay.setRepayOrderCode(generatorService.generateRepayOrderCode());
        customerCreditRepay.setRepayStatus(CreditRepayStatus.WAIT);
        if (Objects.equals(Constants.ONE,request.getRepayWay())){
            customerCreditRepay.setRepayTime(request.getRepayTime());
            customerCreditRepay.setRepayStatus(CreditRepayStatus.AUDIT);
        }
        customerCreditRepay.setDelFlag(DeleteFlag.NO);
        customerCreditRepay.setCreateTime(LocalDateTime.now());

        CustomerCreditRepay newRepay = this.add(customerCreditRepay);

        // 查询本次所关联订单的支付时间
        List<Object> objectList =
                creditOrderRepository.findCreditOrderListByOrderIdInAndDelFlag(
                        request.getOrderIds());
        // 转换成授信还款对象
        Map<String, CustomerCreditOrder> creditOrderListMap =
                objectList.stream()
                        .map(
                                obj -> {
                                    CustomerCreditOrder customerCreditOrder =
                                            new CustomerCreditOrder();
                                    customerCreditOrder.setOrderId(((String) ((Object[]) obj)[0]));
                                    String payTime = (((Object[]) obj)[1]).toString();
                                    customerCreditOrder.setCreateTime(this.getPayTime(payTime));
                                    return customerCreditOrder;
                                })
                        .collect(
                                Collectors.toMap(
                                        CustomerCreditOrder::getOrderId, Function.identity()));

        // 新增还款记录
        creditOrderRepository.saveAll(
                request.getOrderIds().stream()
                        .map(
                                orderId -> {
                                    CustomerCreditOrder customerCreditOrder =
                                            creditOrderListMap.get(orderId);
                                    customerCreditOrder.setRepayOrderCode(
                                            customerCreditRepay.getRepayOrderCode());
                                    customerCreditOrder.setCustomerId(request.getCustomerId());
                                    customerCreditOrder.setDelFlag(DeleteFlag.NO);
                                    customerCreditOrder.setCreatePerson(request.getCustomerId());
                                    return customerCreditOrder;
                                })
                        .collect(Collectors.toList()));

        //线下还款直接返回
        if (Objects.equals(Constants.ONE,request.getRepayWay())){
            return newRepay;
        }

        // 发送延时mq 10分钟后取消支付
        creditProducerMqService.sendMQForRepayCancel(
                newRepay.getId(), newRepay.getCustomerId(), 10 * 60 * 1000L);
        return newRepay;
    }

    /**
     * 自动取消还款记录
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoCancel(CustomerCreditRepayCancelRequest request) {
        logger.info("自动取消还款记录:{}", JSONObject.toJSONString(request));
        CustomerCreditRepay customerCreditRepay =
                creditRepayRepository.findById(request.getId()).orElse(null);
        if (Objects.nonNull(customerCreditRepay)) {
            if (Objects.equals(CreditRepayStatus.VOID, customerCreditRepay.getRepayStatus())) {
                return;
            }
            List<CustomerCreditRepay> list =
                    this.list(
                            CustomerCreditRepayQueryRequest.builder()
                                    .customerId(request.getCustomerId())
                                    .repayStatus(CreditRepayStatus.WAIT)
                                    .delFlag(DeleteFlag.NO)
                                    .build());
            if (list.size() == 1 && request.getId().equals(list.get(0).getId())) {
                customerCreditRepay.setRepayStatus(CreditRepayStatus.VOID);
                customerCreditRepay.setUpdatePerson(request.getCustomerId());
                customerCreditRepay.setUpdateTime(LocalDateTime.now());
                creditRepayRepository.save(customerCreditRepay);
                redissonClient
                        .getFairLock(
                                AccountConstants.CUSTOMER_CREDIT_REPAY + request.getCustomerId())
                        .unlock();
            }
        }
    }

    /**
     * 根据还款单号查询还款单
     *
     * @param repayCode
     * @return
     */
    public CustomerCreditRepay getCreditRepayByRepayCode(String repayCode) {
        return creditRepayRepository.findByRepayOrderCodeAndDelFlag(repayCode, DeleteFlag.NO);
    }

    /** 将实体包装成VO */
    public CustomerCreditRepayVO wrapperVo(CustomerCreditRepay customerCreditRepay) {
        if (customerCreditRepay != null) {
            CustomerCreditRepayVO customerCreditRepayVO =
                    KsBeanUtil.convert(customerCreditRepay, CustomerCreditRepayVO.class);
            customerCreditRepayVO.setCreateTime(customerCreditRepay.getCreateTime());
            return customerCreditRepayVO;
        }
        return null;
    }

    /**
     * 获取还款详情信息
     *
     * @param id
     * @return
     */
    public CreditRepayPageResponse getCreditHasRepaidDetail(String id, String cId) {
        Optional<CustomerCreditRepay> creditRepayOptional = creditRepayRepository.findById(id);
        CreditRepayPageResponse repayResponse = CreditRepayPageResponse.builder().build();
        creditRepayOptional.ifPresent(
                creditRepay -> {
                    String customerId = creditRepay.getCustomerId();
                    if (!StringUtils.equals(cId, customerId)){
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000015);
                    }
                    boolean isExists =
                            creditAccountRepository.existsByCustomerIdAndDelFlag(
                                    customerId, DeleteFlag.NO);
                    if (!isExists) {
                        throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
                    }
                    BeanUtils.copyProperties(creditRepay, repayResponse);
                    String repayOrderCode = creditRepay.getRepayOrderCode();
                    long orderNum = creditOrderRepository.countByRepayOrderCode(repayOrderCode);
                    repayResponse.setOrderNum(orderNum);
                });
        return repayResponse;
    }

    /**
     * 授信还款支付回调
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public CustomerCreditRepay modifyByPaySuccess(CustomerCreditRepayModifyRequest request) {
        logger.info("授信还款支付回调-修改授信账户的数据:{}", JSONObject.toJSONString(request));
        CustomerCreditRepay customerCreditRepay =
                creditRepayRepository.findByRepayOrderCodeAndDelFlag(
                        request.getRepayOrderCode(), DeleteFlag.NO);
        // 还款状态为已还款
        customerCreditRepay.setRepayStatus(CreditRepayStatus.FINISH);
        customerCreditRepay.setRepayType(request.getRepayType());
        customerCreditRepay.setRepayTime(request.getRepayTime());
        customerCreditRepay.setUpdatePerson(request.getUpdatePerson());
        repaySuccess(customerCreditRepay);

        return creditRepayRepository.save(customerCreditRepay);
    }

    /**
     * 还款成功
     * @param customerCreditRepay
     */
    private void repaySuccess(CustomerCreditRepay customerCreditRepay) {
        // 无论授信账户是否过期，在线还款都须修改授信账户的数据
        creditAccountRepository.repayUpdateCreditAccount(
                customerCreditRepay.getCustomerId(), customerCreditRepay.getRepayAmount());

        // 还款时，授信账户是否已过期
        Boolean isExpired = Boolean.FALSE;
        CustomerCreditAccount creditAccount;
        // 判断账户是否过期 如果过期的话 不需要恢复统计中可用总额度，待还款、已还款 还是正常做变动
        if (Objects.nonNull(customerCreditRepay.getCustomerCreditAccount())) {
            creditAccount = customerCreditRepay.getCustomerCreditAccount();
            // 如果账户过期
            if (LocalDateTime.now().isAfter(creditAccount.getEndTime())) {
                isExpired = Boolean.TRUE;
            }
        } else {
            creditAccount =
                    creditAccountRepository
                            .findByCustomerIdAndDelFlag(
                                    customerCreditRepay.getCustomerId(), DeleteFlag.NO)
                            .orElseThrow(
                                    () ->
                                            new SbcRuntimeException(
                                                    AccountErrorCodeEnum.K020013));
            // 如果账户过期
            if (LocalDateTime.now().isAfter(creditAccount.getEndTime())) {
                isExpired = Boolean.TRUE;
            }
        }

        // 发消息修改授信统计数据
        creditAccountSink.sendCreditStateChangeEvent(
                JSONObject.toJSONString(
                        CreditStateChangeEvent.builder()
                                .creditStateChangeType(CreditStateChangeType.REPAY)
                                .expiredFlag(isExpired)
                                .amount(customerCreditRepay.getRepayAmount())
                                .customerId(customerCreditRepay.getCustomerId())
                                .build()));

        // ============= 处理平台的消息发送：授信还款成功 START =============
        storeMessageBizService.handleForCreditRepaymentSuccess(creditAccount);
        // ============= 处理平台的消息发送：授信还款成功 END =============
    }

    /**
     * 获取授信开始时间和结束时间相差天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private Long getEffectiveDays(LocalDateTime startTime, LocalDateTime endTime) {
        return ObjectUtils.allNotNull(startTime, endTime)
                ? Long.valueOf(Duration.between(startTime, endTime).toDays())
                : NumberUtils.LONG_ZERO;
    }

    public LocalDateTime getPayTime(String payTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            Date date = new SimpleDateFormat(DateUtil.FMT_TIME_4).parse(payTime);
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();

            localDateTime = instant.atZone(zoneId).toLocalDateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    /**
     * 线下还款审核
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void checkCreditRepay(CheckCreditRepayRequest request) {
        CustomerCreditRepay customerCreditRepay =
                creditRepayRepository.findByRepayOrderCodeAndDelFlag(
                        request.getRepayOrderCode(), DeleteFlag.NO);
        if (!Objects.equals(CreditRepayStatus.AUDIT,customerCreditRepay.getRepayStatus())){
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020018);
        }
        if (Objects.equals(Constants.ZERO,request.getAuditStatus())){
            customerCreditRepay.setAuditPerson(request.getUserId());
            customerCreditRepay.setAuditTime(LocalDateTime.now());
            customerCreditRepay.setRepayStatus(CreditRepayStatus.FINISH);
            repaySuccess(customerCreditRepay);
        }else {
            customerCreditRepay.setAuditPerson(request.getUserId());
            customerCreditRepay.setAuditTime(LocalDateTime.now());
            customerCreditRepay.setRepayStatus(CreditRepayStatus.TURN_DOWN);
            customerCreditRepay.setAuditRemark(request.getAuditRemark());

        }
        customerCreditRepay.setUpdateTime(LocalDateTime.now());
        customerCreditRepay.setUpdatePerson(request.getUserId());
        creditRepayRepository.save(customerCreditRepay);
    }

    public CreditRepayPageResponse findFinishRepayByOrderId(String tid) {
        CustomerCreditRepay repay = creditRepayRepository.findFinishRepayByOrderId(tid);
        CreditRepayPageResponse response;
        if (repay != null) {
            response =
                    com.wanmi.sbc.common.util.BeanUtils.beanCopy(
                            repay, CreditRepayPageResponse.class);
        } else {
            response = new CreditRepayPageResponse();
        }
        return response;
    }
}
