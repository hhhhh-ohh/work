package com.wanmi.sbc.customer.api.response.ledgersupplier;

import com.wanmi.sbc.customer.bean.vo.LedgerSupplierVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商户分账绑定数据修改结果</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的商户分账绑定数据信息
     */
    @Schema(description = "已修改的商户分账绑定数据信息")
    private LedgerSupplierVO ledgerSupplierVO;
}
