package com.wanmi.sbc.customer.api.response.ledgerreceiverrel;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LakalaDistributionBindVO;
import com.wanmi.sbc.customer.bean.vo.LakalaProviderBindVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className BindStateQueryResponse
 * @description TODO
 * @date 2022/9/14 15:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class BindStateQueryResponse implements Serializable {

    @Schema(description = "供应商绑定关系")
    private MicroServicePage<LakalaProviderBindVO> lakalaProviderBindVOPage;

    @Schema(description = "分销员绑定关系")
    private MicroServicePage<LakalaDistributionBindVO> lakalaDistributionBindVOPage;
}
