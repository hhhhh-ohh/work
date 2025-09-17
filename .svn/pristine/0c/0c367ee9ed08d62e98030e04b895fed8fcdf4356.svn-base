package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName TradeTimeOutCancelResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/9 21:50
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeTimeOutCancelResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    private List<String> sucTidList;

    private List<String> failTidList;
}
