package com.wanmi.sbc.account.api.response.invoice;

import com.wanmi.sbc.account.bean.vo.InvoiceProjectSwitchVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 根据公司信息Id返回的开票项目开关响应
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceProjectSwitchByCompanyInfoIdResponse extends InvoiceProjectSwitchVO implements Serializable {

    private static final long serialVersionUID = -8810391288620875453L;
}
