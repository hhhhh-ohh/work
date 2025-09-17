package com.wanmi.sbc.elastic.api.response.customerInvoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsCustomerInvoicePageResponse extends BasicResponse {

    @Schema(description = "会员增票信息分页")
    private MicroServicePage<CustomerInvoiceVO> customerInvoiceVOPage;
}
