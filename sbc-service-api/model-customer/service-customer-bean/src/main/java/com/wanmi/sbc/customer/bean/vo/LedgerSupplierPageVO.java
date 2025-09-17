package com.wanmi.sbc.customer.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>商户分账绑定数据VO</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LedgerSupplierPageVO extends LedgerSupplierVO {

}
