package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.customer.agent.model.root.CrmOaAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OaCrmAuthRepository extends JpaRepository<CrmOaAuth, Long>, JpaSpecificationExecutor<CrmOaAuth> {
    List<CrmOaAuth> findByOaAccount(String oaAccount);
}
