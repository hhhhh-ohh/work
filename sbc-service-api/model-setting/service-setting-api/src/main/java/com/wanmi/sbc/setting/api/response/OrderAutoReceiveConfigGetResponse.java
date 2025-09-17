package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
/**
 * 订单代发货自动收货配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderAutoReceiveConfigGetResponse extends ConfigVO {
    private static final long serialVersionUID = 1L;
}
