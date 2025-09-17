package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByCompanyInfoIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.AddB2bBusiRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaAddB2bBusiRequest;
import com.wanmi.sbc.empower.api.response.ledger.lakala.AddB2bBusiResponse;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * b2b网银新增 补偿
 */
@Component
@Slf4j
public class LedgerB2bAddJobHandler {

    @Autowired
    LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    LedgerProvider ledgerProvider;

    @Autowired
    GeneratorService generatorService;

    @Autowired
    StoreQueryProvider storeQueryProvider;

    @Autowired
    LedgerAccountProvider ledgerAccountProvider;

    @XxlJob(value = "LedgerB2bAddJobHandler")
    public void execute() throws Exception {
        log.info("b2b网银新增  LedgerBindQueryJobHandler start=======");
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(param)) {
            //获取商户清分账户
            LedgerAccountVO supplierLedgerAccountVO = ledgerAccountQueryProvider.findByBusiness(LedgerAccountFindRequest.builder()
                            .businessId(param)
                            .setFileFlag(Boolean.FALSE)
                            .build())
                    .getContext().getLedgerAccountVO();
            StoreByCompanyInfoIdResponse store = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                            .companyInfoId(Long.valueOf(supplierLedgerAccountVO.getBusinessId()))
                            .build())
                    .getContext();
            if (Integer.valueOf(2).equals(supplierLedgerAccountVO.getAccountState()) &&
                    Objects.nonNull(store) &&
                    Objects.nonNull(store.getStoreVO())) {
                //新增线上业务类型
                BaseResponse addB2bBusiBaseResponse = ledgerProvider.addB2bBusi(AddB2bBusiRequest.builder()
                        .lakalaAddB2bBusiRequest(LakalaAddB2bBusiRequest.builder()
                                .merInnerNo(supplierLedgerAccountVO.getThirdMemNo())
                                .merCupNo(supplierLedgerAccountVO.getMerCupNo())
                                .termNo(supplierLedgerAccountVO.getTermNo())
                                .orderNo(generatorService.generateLedgerOrderNo())
                                .build())
                        .build());
                AddB2bBusiResponse addB2bBusiResponse = BeanUtils.beanCovert(addB2bBusiBaseResponse.getContext(), AddB2bBusiResponse.class);
                if (StringUtils.isNotBlank(addB2bBusiResponse.getContractId())) {
                    //成功，修改账户b2b开通状态
                    LedgerAccountModifyRequest modifyRequest = LedgerAccountModifyRequest.builder()
                            .businessId(supplierLedgerAccountVO.getBusinessId())
                            .b2bAddApplyId(addB2bBusiResponse.getContractId())
                            .b2bAddState(Constants.ONE).build();
                    ledgerAccountProvider.updateB2bAddStateById(modifyRequest);
                }
            }
        }
    }
}
