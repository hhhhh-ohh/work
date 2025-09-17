package com.wanmi.sbc.account.bank.service;

import com.wanmi.sbc.account.api.constant.BaseBank;
import com.wanmi.sbc.account.api.constant.BaseBankConfiguration;
import com.wanmi.sbc.account.bank.model.root.Bank;
import com.wanmi.sbc.account.bank.repository.BankRepository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    /**
     * 获取银行列表
     * @return
     */
    public List<Bank> list() {
        List<Bank> banks = bankRepository.findAllByDeleteFlag(DeleteFlag.NO);
        if (CollectionUtils.isEmpty(banks)) {
            List<BaseBank> baseBanks = BaseBankConfiguration.getBankList();
            banks = KsBeanUtil.convertList(baseBanks, Bank.class)
                    .parallelStream().peek(bank -> bank.setDeleteFlag(DeleteFlag.NO)).collect(Collectors.toList());
            bankRepository.saveAll(banks);
        }
        return banks;
    }
}
