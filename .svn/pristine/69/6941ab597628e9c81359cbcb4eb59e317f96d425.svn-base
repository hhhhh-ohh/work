package com.wanmi.sbc.customer.api.response.invoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Schema
@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerInvoiceByCustomerIdResponse extends BasicResponse {
    /**
     * 是否有增票资质
     */
    @Schema(description = "是否有增票资质")
    private boolean flag = false;

    /**
     * 是否支持增票资质
     */
    @Schema(description = "是否支持增票资质")
    private boolean configFlag = false;

    /**
     * 是否支持纸质发票
     */
    @Schema(description = "是否支持纸质发票")
    private boolean paperInvoice = false;

    /**
     * 是否支持开票 pc端使用
     */
    @Schema(description = "是否支持开票")
    private boolean support = false;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 增票资质信息
     */
    @Schema(description = "增票资质信息")
    private CustomerInvoiceVO customerInvoiceResponse;
}
