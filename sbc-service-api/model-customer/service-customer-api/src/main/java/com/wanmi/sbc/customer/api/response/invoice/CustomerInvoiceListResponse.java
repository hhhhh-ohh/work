package com.wanmi.sbc.customer.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceListResponse{


    private static final long serialVersionUID = 1L;

    @Schema(description = "会员发票信息分页")
    private List<CustomerInvoiceVO> customerInvoiceVOList;

}
