package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 增专资质配置
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceConfigGetResponse extends ConfigVO {
    private static final long serialVersionUID = 1L;
}
