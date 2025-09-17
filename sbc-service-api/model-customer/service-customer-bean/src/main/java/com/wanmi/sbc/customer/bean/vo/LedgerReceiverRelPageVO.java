package com.wanmi.sbc.customer.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>分账绑定关系VO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LedgerReceiverRelPageVO extends LedgerReceiverRelVO {

}
