package com.wanmi.sbc.order.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>付费会员支付记录表VO</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayingMemberPayRecordPageVO extends PayingMemberPayRecordVO {

}
