package com.wanmi.sbc.account.provider.impl.bank;


import com.wanmi.sbc.account.api.provider.bank.BankQueryProvider;
import com.wanmi.sbc.account.api.response.bank.BankListResponse;
import com.wanmi.sbc.account.bank.model.root.Bank;
import com.wanmi.sbc.account.bank.service.BankService;
import com.wanmi.sbc.account.bean.vo.BankVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>银行查询接口</p>
 * Created by daiyitian on 2018-10-13-下午6:23.
 */
@RestController
@Validated
public class BankQueryController implements BankQueryProvider {

    @Autowired
    private BankService bankService;

    @Override
    public BaseResponse<BankListResponse> list() {
        List<Bank> list = bankService.list();
        return BaseResponse.success(BankListResponse.builder()
                .bankVOList(KsBeanUtil.convertList(list, BankVO.class))
                .build());
    }
}
