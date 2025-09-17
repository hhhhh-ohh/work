package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;

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
public class LakalaSettlementGetViewResponse extends LakalaSettlementViewVO implements Serializable {

    private static final long serialVersionUID = -6081164448530279269L;
}
