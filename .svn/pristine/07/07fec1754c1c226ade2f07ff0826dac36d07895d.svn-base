package com.wanmi.sbc.customer.agent.service;

import com.wanmi.sbc.customer.agent.model.root.CrmOaAuth;
import com.wanmi.sbc.customer.agent.repository.OaCrmAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OaCrmAuthService {

    @Autowired
    private OaCrmAuthRepository oaCrmAuthRepository;

    public List<CrmOaAuth> findOaCrmAuthByAccount(String oaAccount){
        List<CrmOaAuth> allByOaAccount = oaCrmAuthRepository.findByOaAccount(oaAccount);
        return allByOaAccount;
    }


}
