package com.wanmi.sbc.account.bank.repository;

import com.wanmi.sbc.account.bank.model.root.Bank;

import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query
    List<Bank> findAllByDeleteFlag(DeleteFlag deleteFlag);
}
