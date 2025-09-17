package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverPageRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRecoverPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountStatisticsResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRecord;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.repository.CreditRecordRepository;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByConditionResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/2/27 11:45
 * @description
 *     <p>授信账户查询service
 */
@Slf4j
@Service
public class CreditAccountQueryService {

    @Autowired
    private CreditAccountRepository creditAccountRepository;
    @Autowired
    private CreditRecordRepository creditRecordRepository;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    /**
     * 授信账户分页查询
     *
     * @param request {@link CreditAccountPageRequest}
     * @return {@link CreditAccountPageResponse}
     */
    public MicroServicePage<CreditAccountPageResponse> findCreditAccountPage(
            CreditAccountPageRequest request) {
//        int a = 1;
//        int b = 2;
//
//        if(a == b){
//            int c = a/0;
//            System.out.println(c);
//        }

        // 查询条件
        Specification<CustomerCreditAccount> condition =
                CreditAccountWhereCriteriaBuilder.build(request);
        // 获取数据
        PageRequest pageable = request.getPageable();
        // 因封装的排序无用，所以用的源生排序方式
        Sort createTime = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), createTime);
        Page<CustomerCreditAccount> creditAccountPage =
                creditAccountRepository.findAll(condition, pageRequest);
        List<CustomerCreditAccount> creditAccountList = creditAccountPage.getContent();
        if (CollectionUtils.isEmpty(creditAccountList)) {
            return new MicroServicePage<>(Collections.emptyList(), pageable, NumberUtils.LONG_ZERO);
        }
        List<String> customerIdList =
                creditAccountList.stream()
                        .map(CustomerCreditAccount::getCustomerId)
                        .collect(Collectors.toList());
        Map<String, String> customerNameMap = this.getCustomerName(customerIdList);
        Page<CreditAccountPageResponse> newPage =
                creditAccountPage.map(
                        target -> {
                            CreditAccountPageResponse source =
                                    CreditAccountPageResponse.builder().build();
                            BeanUtils.copyProperties(target, source);
                            BoolFlag expireStatus = this.getExpireStatus(target.getEndTime());
                            source.setExpireStatus(expireStatus);
                            Long effectiveDays =
                                    this.getEffectiveDays(
                                            target.getStartTime(), target.getEndTime());
                            source.setEffectiveDays(effectiveDays);
                            String customerName = customerNameMap.get(target.getCustomerId());
                            source.setCustomerName(customerName);
                            return source;
                        });
        return new MicroServicePage<>(newPage, newPage.getPageable());
    }

    /**
     * 分页查询授信账户
     *
     * @param request {@link CreditAccountPageRequest}
     * @return {@link CreditAccountPageResponse}
     */
    public MicroServicePage<CreditAccountPageResponse> findCreditAccountForPage(CreditAccountPageRequest request) {
        // 查询条件
        Specification<CustomerCreditAccount> condition =
                CreditAccountWhereCriteriaBuilder.build(request);
        // 获取数据
        PageRequest pageable = request.getPageable();
        // 因封装的排序无用，所以用的源生排序方式                ;
        Page<CustomerCreditAccount> creditAccountPage = creditAccountRepository.findAll(condition,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                Sort.by(Sort.Direction.DESC, "createTime")));
        if (Objects.isNull(creditAccountPage)
                || CollectionUtils.isEmpty(creditAccountPage.getContent())) {
            return new MicroServicePage<>(Collections.emptyList(), pageable, NumberUtils.LONG_ZERO);
        }
        Page<CreditAccountPageResponse> newPage = creditAccountPage.map((target) ->KsBeanUtil.copyPropertiesThird(target,CreditAccountPageResponse.class));
        return new MicroServicePage<>(newPage, newPage.getPageable());
    }

    /**
     * 查看授信账户详情
     *
     * @param id 主键
     * @return {@link CreditAccountDetailResponse}
     */
    public CreditAccountDetailResponse getCreditAccountDetail(String id) {
        // 因审核状态不在授信账户表中，所以需要联查
        CreditAccountPageRequest request =
                CreditAccountPageRequest.builder().customerId(id).build();
        Specification<CustomerCreditAccount> condition =
                CreditAccountWhereCriteriaBuilder.build(request);

        CreditAccountDetailResponse response = CreditAccountDetailResponse.builder().build();
        Optional<CustomerCreditAccount> creditAccountOptional =
                creditAccountRepository.findOne(condition);
        if (!creditAccountOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        CustomerCreditAccount creditAccount = creditAccountOptional.get();
        BeanUtils.copyProperties(creditAccount, response);
        BoolFlag expireStatus = this.getExpireStatus(creditAccount.getEndTime());
        // 若逾期可用额度为0
        if (Objects.equals(expireStatus, BoolFlag.YES)) {
            response.setUsableAmount(BigDecimal.ZERO);
        }
        response.setExpireStatus(expireStatus);
        Long effectiveDays =
                this.getEffectiveDays(creditAccount.getStartTime(), creditAccount.getEndTime());
        response.setEffectiveDays(effectiveDays);
        List<String> customerIdList = Collections.singletonList(creditAccount.getCustomerId());
        Map<String, String> customerNameMap = this.getCustomerName(customerIdList);
        response.setCustomerName(customerNameMap.get(creditAccount.getCustomerId()));
        return response;
    }

    /**
     * 获取会员名称
     *
     * @param customerIdList
     * @return
     */
    private Map<String, String> getCustomerName(List<String> customerIdList) {
        CustomerDetailListByConditionRequest request =
                CustomerDetailListByConditionRequest.builder().customerIds(customerIdList).build();
        BaseResponse<CustomerDetailListByConditionResponse> response =
                customerDetailQueryProvider.listCustomerDetailByCondition(request);
        List<CustomerDetailVO> customerDetailVOList =
                response.getContext().getCustomerDetailVOList();
        return customerDetailVOList.stream()
                .collect(
                        Collectors.toMap(
                                CustomerDetailVO::getCustomerId,
                                CustomerDetailVO::getCustomerName));
    }

    /**
     * 根据登录用户查询授信账户
     *
     * @param customerId 登录用户
     * @return {@link CreditAccountDetailResponse}
     */
    public CreditAccountDetailResponse getCreditAccountByCustomerId(String customerId) {
        Optional<CustomerCreditAccount> accountOptional =
                creditAccountRepository.findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO);
        // 返回值
        CreditAccountDetailResponse response;
        if (accountOptional.isPresent()) {
            CustomerCreditAccount account = accountOptional.get();
            BoolFlag expireStatus = this.getExpireStatus(account.getEndTime());
            response =
                    CreditAccountDetailResponse.builder()
                            .creditAmount(account.getCreditAmount())
                            .enabled(account.getEnabled())
                            .usableAmount(
                                    Objects.equals(expireStatus, BoolFlag.YES)
                                            ? BigDecimal.ZERO
                                            : account.getUsableAmount())
                            .startTime(account.getStartTime())
                            .endTime(account.getEndTime())
                            .id(account.getId())
                            .changeRecordId(account.getChangeRecordId())
                            .customerId(account.getCustomerId())
                            .customerName(account.getCustomerName())
                            .customerAccount(account.getCustomerAccount())
                            .build();
        } else {
            // 构建一个空账户返回
            response =
                    CreditAccountDetailResponse.builder()
                            .creditAmount(BigDecimal.ZERO)
                            .enabled(BoolFlag.NO)
                            .build();
        }
        return response;
    }

    /**
     * 查询历史额度恢复记录
     *
     * @param request
     * @return
     */
    public MicroServicePage<CreditRecoverPageResponse> findCreditRecoverPage(
            CreditRecoverPageRequest request) {
        // 查询条件
        String customerId = request.getCustomerId();
        // 分页参数
        PageRequest pageable = request.getPageable();
        // 因封装的排序无用，所以用的源生排序方式
        Sort createTime = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), createTime);
        // 获取数据
        Page<CustomerCreditRecord> creditRecordPage =
                creditRecordRepository.findAllByCustomerId(customerId, pageRequest);
        Page<CreditRecoverPageResponse> newPage =
                creditRecordPage.map(
                        target -> {
                            CreditRecoverPageResponse source =
                                    CreditRecoverPageResponse.builder().build();
                            BeanUtils.copyProperties(target, source);
                            BoolFlag expireStatus = this.getExpireStatus(target.getEndTime());
                            if (Objects.equals(expireStatus, BoolFlag.YES)) {
                                source.setUsedStatus(BoolFlag.NO);
                            }
                            return source;
                        });
        return new MicroServicePage<>(newPage, newPage.getPageable());
    }

    /**
     * 根据customerId获取账户信息
     *
     * @param customerId
     * @return
     */
    public CustomerCreditAccount getAccountInfoByCustomerId(String customerId) {

        return creditAccountRepository
                .findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO)
                .orElse(null);
    }

    /**
     * 判断是否过期
     *
     * @param endTime 授信账户信息
     * @return {@link BoolFlag}
     */
    private BoolFlag getExpireStatus(LocalDateTime endTime) {
        if (Objects.isNull(endTime)) {
            return BoolFlag.YES;
        }
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.isAfter(endTime) ? BoolFlag.YES : BoolFlag.NO;
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

    /**
     * 查询授信账户详情
     *
     * @param id
     * @return
     */
    public CreditRecoverPageResponse getCreditRecoverDetail(String id) {
        Optional<CustomerCreditRecord> creditRecordOptional = creditRecordRepository.findById(id);
        CreditRecoverPageResponse response = CreditRecoverPageResponse.builder().build();
        creditRecordOptional.ifPresent(
                creditRecord -> {
                    BoolFlag expireStatus = this.getExpireStatus(creditRecord.getEndTime());
                    if (Objects.equals(expireStatus, BoolFlag.YES)) {
                        creditRecord.setUsedStatus(BoolFlag.NO);
                    }

                    BeanUtils.copyProperties(creditRecord, response);
                });
        return response;
    }

    /**
     * @description 查询所有账户的授信统计数据
     * @author chenli
     * @date 2021/4/22 14:55
     * @return
     */
    public CreditAccountStatisticsResponse findCustomerCreditAccountStatistics() {
        // 授信账户数
        Long creditAccountNum = creditAccountRepository.sumCreditAccount();
        // 待还款总数
        BigDecimal creditRepayAmount = creditAccountRepository.sumCreditRepayAmount();
        // 可用额度总数
        BigDecimal creditUsableAmount = creditAccountRepository.sumCreditUsableAmount();

        return CreditAccountStatisticsResponse.builder()
                .creditAccountNum(Objects.nonNull(creditAccountNum) ? creditAccountNum : 0L)
                .creditRepayAmount(
                        Objects.nonNull(creditRepayAmount) ? creditRepayAmount : BigDecimal.ZERO)
                .creditUsableAmount(
                        Objects.nonNull(creditUsableAmount) ? creditUsableAmount : BigDecimal.ZERO)
                .build();
    }
}
