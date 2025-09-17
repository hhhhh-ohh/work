package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.account.bean.vo.InvoiceProjectVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 开票项目新增响应
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceProjectAddResponse extends InvoiceProjectVO implements Serializable{

    private static final long serialVersionUID = 6932257057133739818L;
}
