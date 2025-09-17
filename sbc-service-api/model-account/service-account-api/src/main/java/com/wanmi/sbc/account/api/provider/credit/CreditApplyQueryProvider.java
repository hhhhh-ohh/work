package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***
 * 授信账户申请记录查询
 * @author zhengyang
 * @since 2021-03-02
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditApplyQueryProvider")
public interface CreditApplyQueryProvider {

    /**
     * 授信账户申请记录查询
     *
     * @param request {@link CreditApplyQueryRequest}
     * @return {@link CreditApplyRecordVo}
     */
    @PostMapping("/account/${application.account.version}/find-credit-apply-info")
    BaseResponse<CreditApplyRecordVo> queryApplyInfoByCustomerId(@RequestBody @Validated CreditApplyQueryRequest request);

    /***
     * 根据登录用户查询变更额度申请记录状态
     * @param request 登录用户
     * @return 授信账户变更额度申请记录
     */
    @PostMapping("/account/${application.account.version}/find-credit-change-info")
    BaseResponse<CreditApplyRecordVo> queryChangeInfoByCustomerId(@RequestBody @Validated CreditApplyQueryRequest request);
}
