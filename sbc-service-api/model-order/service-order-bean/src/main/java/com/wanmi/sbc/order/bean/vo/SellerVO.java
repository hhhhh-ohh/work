package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家
 * Created by Administrator on 2017/5/1.
 */
@Data
@Schema
public class SellerVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 卖家ID
     */
    @Schema(description = "卖家ID")
    private String adminId;

    /**
     * 代理人Id，用于代客下单
     */
    @Schema(description = "代理人Id")
    private String proxyId;

    /**
     * 代理人名称，用于代客下单，相当于OptUserName
     */
    @Schema(description = "代理人名称")
    private String proxyName;
}
