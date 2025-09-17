package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className LedgerBindInfoToEsRequest
 * @description
 * @date 2022/7/14 5:11 PM
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerBindInfoToEsRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -619381541055244327L;

    @Schema(description = "分账绑定信息")
    @NotEmpty
    private List<LedgerReceiverRelVO> relVOList;
}
