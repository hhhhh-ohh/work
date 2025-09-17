package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.account.bean.vo.InvoiceProjectVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 开票项目修改结果
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceProjectModifyResponse extends InvoiceProjectVO implements Serializable{

    private static final long serialVersionUID = 5397131936515273776L;
}
