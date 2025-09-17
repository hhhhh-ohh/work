package com.wanmi.sbc.customer.api.response.ledgersupplier;

import com.wanmi.sbc.customer.bean.vo.LedgerSupplierVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商户分账绑定数据信息response</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户分账绑定数据信息
     */
    @Schema(description = "商户分账绑定数据信息")
    private LedgerSupplierVO ledgerSupplierVO;
}
