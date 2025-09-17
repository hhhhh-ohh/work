package com.wanmi.sbc.customer.api.response.ledgercontract;

import com.wanmi.sbc.customer.bean.vo.LedgerContractVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）分账合同协议配置信息response</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContractByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分账合同协议配置信息
     */
    @Schema(description = "分账合同协议配置信息")
    private LedgerContractVO ledgerContractVO;
}
