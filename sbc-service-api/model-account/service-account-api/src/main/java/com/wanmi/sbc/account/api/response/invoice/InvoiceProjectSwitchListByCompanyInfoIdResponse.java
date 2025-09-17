package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectSwitchVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 开票项目开关列表响应结果
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectSwitchListByCompanyInfoIdResponse extends BasicResponse {

    private static final long serialVersionUID = -5143138119950166630L;
    /**
     * 开票项目开关列表 {@link InvoiceProjectSwitchVO}
     */
    @Schema(description = "开票项目开关列表")
    private List<InvoiceProjectSwitchVO> invoiceProjectSwitchVOList;
}
