package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CustomerApplyRecordProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditApplyDetailResponse;
import com.wanmi.sbc.account.bean.vo.CustomerApplyRecordVo;
import com.wanmi.sbc.account.credit.service.CustomerApplyRecordService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/***
 * 用户申请记录
 * @author zhengyang
 * @since 2021-03-01
 */
@RestController
@Validated
public class CustomerApplyRecordController implements CustomerApplyRecordProvider {

    @Resource
    private CustomerApplyRecordService customerApplyRecordService;

    /***
     * 查询用户申请记录列表
     * @param request
     * @return
     */
    @Override
    public BaseResponse<MicroServicePage<CustomerApplyRecordVo>> queryApplyRecord(CreditAuditQueryRequest request) {
        return BaseResponse.success(customerApplyRecordService.queryApplyRecord(request));
    }

    /***
     * 根据用户申请记录ID查询用户授信申请详情
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CreditApplyDetailResponse> findCreditAccountApplyDetailById(CreditApplyDetailRequest request) {
        return BaseResponse.success(customerApplyRecordService.findCreditAccountApplyDetailById(request));
    }

    /***
     * 更新注销用户为驳回状态
     * @param request
     * @return
     */
    @Override
    public BaseResponse modifyByCustomerId(CreditApplyQueryRequest request) {
        customerApplyRecordService.modifyByCustomerId(request.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }
}
