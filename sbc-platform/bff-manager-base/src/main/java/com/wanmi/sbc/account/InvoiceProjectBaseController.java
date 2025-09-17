package com.wanmi.sbc.account;

import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectProvider;
import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectByIdRequest;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectDeleteByIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectByIdResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 开票项目管理
 * Created by yuanlinling on 2017/4/25.
 */
@Tag(name = "InvoiceProjectBaseController", description = "开票项目管理 Api")
@RestController
@Validated
@RequestMapping("/account")
public class InvoiceProjectBaseController {

    @Autowired
    private InvoiceProjectQueryProvider invoiceProjectQueryProvider;

    @Autowired
    private InvoiceProjectProvider invoiceProjectProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 删除
     *
     * @param invoiceProjectRequest
     * @return
     */
    @Operation(summary = "删除")
    @RequestMapping(value = "/invoiceProject", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> deleteInvoiceProject(@RequestBody @Valid InvoiceProjectDeleteByIdRequest invoiceProjectRequest) {
        InvoiceProjectByIdResponse invoice =
                invoiceProjectQueryProvider.getById(new InvoiceProjectByIdRequest(invoiceProjectRequest.getProjectId())).getContext();

        if (invoice == null
                || StringUtils.isBlank(invoice.getProjectId())
                || !commonUtil.getCompanyInfoId().equals(invoice.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }

        invoiceProjectProvider.delete(invoiceProjectRequest);
        // 记录日志
        operateLogMQUtil.convertAndSend("财务", "删除开票项目","删除开票项目：" + invoice.getProjectName());

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }
}
