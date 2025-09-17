package com.wanmi.sbc.customer.api.response.ledgersupplier;

import com.wanmi.sbc.customer.bean.vo.LedgerSupplierPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商户分账绑定数据列表结果</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户分账绑定数据列表结果
     */
    @Schema(description = "商户分账绑定数据列表结果")
    private List<LedgerSupplierPageVO> ledgerSupplierList;
}
