package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.BindContractUploadType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xuyunpeng
 * @className LedgerBindContractFileSaveRequest
 * @description
 * @date 2022/9/23 4:27 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerBindContractFileSaveRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -965125822751774106L;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    @NotNull
    private Long supplierId;

    /**
     * 文件内容
     */
    @Schema(description = "文件内容")
    private byte[] contract;

    /**
     * 文件上传类型
     * @see BindContractUploadType
     */
    @Schema(description = "文件上传类型 0、商户进件 1、申请分账绑定")
    @NotNull
    private BindContractUploadType type;

    /**
     * 分账绑定关系id
     */
    @Schema(description = "分账绑定关系id")
    private String relId;

    @Override
    public void checkParam() {
        if (BindContractUploadType.APPLY_BIND == type && StringUtils.isBlank(relId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (ArrayUtils.isEmpty(contract)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
