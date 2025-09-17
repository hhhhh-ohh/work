package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditApplyQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.account.credit.service.CreditApplyRecordQueryService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/***
 * 授信账户申请记录查询
 * @author zhengyang
 * @since 2021-03-02
 */
@RestController
public class CreditApplyRecordQueryController implements CreditApplyQueryProvider {

    @Resource
    private CreditApplyRecordQueryService applyRecordQueryService;

    /***
     * 根据登录用户查询授信账户申请情况
     * @param request 登录用户
     * @return 授信账户申请记录
     */
    @Override
    public BaseResponse<CreditApplyRecordVo> queryApplyInfoByCustomerId(CreditApplyQueryRequest request) {
        return BaseResponse.success(applyRecordQueryService.queryApplyInfoByCustomerId(request.getCustomerId()));
    }

    /***
     * 根据登录用户查询变更额度申请记录状态
     * @param request 登录用户
     * @return 授信账户变更额度申请记录
     */
    @Override
    public BaseResponse<CreditApplyRecordVo> queryChangeInfoByCustomerId(CreditApplyQueryRequest request) {
        return BaseResponse.success(applyRecordQueryService.queryChangeInfoByCustomerId(request.getCustomerId()));
    }
}
