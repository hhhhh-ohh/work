package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.order.bean.vo.RefundOrderResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author yangzhen
 * @Description //退款单返回
 * @Date 11:11 2021/1/5
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class RefundOrderVoFromEsResponse extends RefundOrderResponse implements Serializable {

}
