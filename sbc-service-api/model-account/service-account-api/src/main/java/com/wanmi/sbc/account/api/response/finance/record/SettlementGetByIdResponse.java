package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.SettlementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 结算单响应请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementGetByIdResponse extends SettlementVO implements Serializable {

    private static final long serialVersionUID = -1767293968310708315L;
}
