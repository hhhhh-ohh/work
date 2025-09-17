package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectListVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 开票项目列表
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProjectListByCompanyInfoIdResponse extends BasicResponse {

    private static final long serialVersionUID = -966954750027941823L;
    /**
     * 开票项目VO列表 {@link InvoiceProjectListVO}
     */
    @Schema(description = "开票项目VO列表")
    private List<InvoiceProjectListVO> invoiceProjectListDTOList;

}
