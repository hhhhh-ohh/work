package com.wanmi.sbc.job;

import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.bean.vo.LedgerCallbackVO;
import com.wanmi.sbc.job.service.LedgerCallBackJobService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 分账回调 补偿
 */
@Component
@Slf4j
public class LedgerCallBackJobHandler {

    @Autowired
    LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    LedgerCallBackJobService ledgerCallBackJobService;

    @XxlJob(value = "LedgerCallBackJobHandler")
    public void execute() throws Exception {
        LedgerCallbackVO ledgerCallbackVO = ledgerAccountQueryProvider.findForCallback().getContext().getLedgerCallbackVO();
        executeEcApplyCallBack(ledgerCallbackVO.getEcApplyIdList());
        executeEc003ApplyCallBack(ledgerCallbackVO.getEc003ApplyIdList());
        executeAddMerCallBack(ledgerCallbackVO.getContractIdList());
        executeApplySplitMerCallBack(ledgerCallbackVO.getMerInnerNoList());
        executeBindCallBack(ledgerCallbackVO.getBindApplyIdList());
    }



    /**
     *  拉卡拉 电子合同申请回调
     */
    private void executeEcApplyCallBack(List<String> ecApplyIdList){
        ecApplyIdList.parallelStream().forEach(ecApplyId -> ledgerCallBackJobService.executeEcApply(ecApplyId));
    }

    /**
     *  拉卡拉 电子合同EC003申请回调
     */
    private void executeEc003ApplyCallBack(List<String> ec003ApplyIdList){
        ec003ApplyIdList.parallelStream().forEach(ecApplyId -> ledgerCallBackJobService.executeEc003Apply(ecApplyId));
    }




    /**
     *  拉卡拉 商户进件申请回调
     */
    private void executeAddMerCallBack(List<String> contractIdList){
        contractIdList.parallelStream().forEach(contractId -> ledgerCallBackJobService.executeContract(contractId));
    }



    /**
     *  拉卡拉 商户分账申请回调
     */
    private void executeApplySplitMerCallBack(List<String> merInnerNoList){
        merInnerNoList.parallelStream().forEach(merInnerNo -> ledgerCallBackJobService.executeApplySplitMer(merInnerNo));
    }




    /**
     * 拉卡拉分账绑定回调 补偿
     * @param bindApplyIdList
     */
    private void executeBindCallBack(List<String> bindApplyIdList) {
        bindApplyIdList.parallelStream().forEach(applyId -> ledgerCallBackJobService.executeBind(applyId));
    }


}
