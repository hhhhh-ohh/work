package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectVO;
import com.wanmi.sbc.common.base.MicroServicePage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 开票项目分页响应
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectPageByCompanyInfoIdResponse extends BasicResponse {

    private static final long serialVersionUID = 2367481834686347064L;
    /**
     * 开票项目分页数据 {@link InvoiceProjectVO}
     */
    @Schema(description = "开票项目分页数据")
    private MicroServicePage<InvoiceProjectVO> invoiceProjectVOPage;

}
