package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectSwitchSupportVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 包含是否支持开票的开票项目开关列表的响应结构
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectSwitchListSupportByCompanyInfoIdResponse extends BasicResponse {

    private static final long serialVersionUID = 4263791818960297055L;
    /**
     * 包含是否支持开票的开票项目开关列表 {@link InvoiceProjectSwitchSupportVO}
     */
    @Schema(description = "包含是否支持开票的开票项目开关列表")
    private List<InvoiceProjectSwitchSupportVO> invoiceProjectSwitchSupportVOList;
}
