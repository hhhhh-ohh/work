package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.SettlementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 结算单响应请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class LakalaSettlementGetByIdResponse extends LakalaSettlementVO implements Serializable {

    private static final long serialVersionUID = -1767293968310708315L;
}
