package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditApplyDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditApplyDetailResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.bean.vo.CustomerApplyRecordVo;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecordWithAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.repository.ApplyRecordRepository;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.Nutils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * 授信申请记录Service
 * @author zhengyang
 * @since 2021-03-01
 */
@Service
public class CustomerApplyRecordService {

    @Resource
    private ApplyRecordRepository applyRecordRepository;
    @Resource
    private CreditAccountRepository creditAccountRepository;

    /***
     * 查询授信审核列表
     * @param request
     * @return
     */
    public MicroServicePage<CustomerApplyRecordVo> queryApplyRecord(CreditAuditQueryRequest request) {
        PageRequest pageable = PageRequest.of(request.getPageNum(), request.getPageSize());
        String account = Nutils.defaultVal(request.getCustomerAccount(), "");
        String name = Nutils.defaultVal(request.getCustomerName(), "");
        List<CreditAuditStatus> authStatusList = null;
        if (Objects.nonNull(request.getAuditStatus())) {
            if (CreditAuditStatus.WAIT == request.getAuditStatus()) {
                authStatusList = Arrays.asList(CreditAuditStatus.WAIT, CreditAuditStatus.RESET_WAIT);
            } else {
                authStatusList = Collections.singletonList(request.getAuditStatus());
            }

        } else {
            authStatusList = Arrays.asList(CreditAuditStatus.values());
        }
        // 分页对象
        Page<CustomerApplyRecordWithAccount> pageResult = applyRecordRepository
                .queryApplyRecord(account, name, authStatusList, request.getEmployeeCustomerIds(), pageable);

        // 返回值对象
        List<CustomerApplyRecordVo> recordVoList = null;
        if (Objects.nonNull(pageResult) && pageResult.getTotalElements() > 0) {
            recordVoList = pageResult.getContent().stream()
                    .map(r -> BeanUtils.beanCopy(r, CustomerApplyRecordVo.class)).collect(Collectors.toList());
        } else {
            recordVoList = new ArrayList<>();
        }

        return new MicroServicePage<>(recordVoList, pageable, Nutils.nonNullActionRt(pageResult, Page::getTotalElements, 0L));
    }

    /***
     * 根据用户账号查询用户授信申请详情
     * @param request
     * @return
     */
    public CreditApplyDetailResponse findCreditAccountApplyDetailById(CreditApplyDetailRequest request) {
        // 判断是否存在
        Optional<CustomerApplyRecord> recordOptional = applyRecordRepository.findById(request.getApplyId());
        if (!recordOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 拉取账号
        Optional<CustomerCreditAccount> accountOptional = creditAccountRepository
                .findByCustomerIdAndDelFlag(recordOptional.get().getCustomerId(), DeleteFlag.NO);

        if (!accountOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 生成Response对象
        CreditApplyDetailResponse response = CreditApplyDetailResponse.builder().build();
        // 属性赋值
        BeanUtils.beanCopy(recordOptional.get(), response);
        BeanUtils.beanCopy(accountOptional.get(), response);
        BeanUtils.beanCopy(accountOptional.get().getCustomerCreditRecord(), response);
        return response;
    }

    /**
     * 更新注销用户为驳回状态
     */
    @Transactional
    public void modifyByCustomerId(String customerId) {
        applyRecordRepository.updateCustomerApplyRecordByCustomerId(customerId);
    }
}
