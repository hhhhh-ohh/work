package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单设置
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeConfigGetByTypeResponse extends ConfigVO {
    private static final long serialVersionUID = 1L;
}
