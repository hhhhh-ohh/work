package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.customer.bean.dto.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class MarketingPluginDTO implements Serializable {


    private static final long serialVersionUID = 8669460112975374860L;
    /**
     * 当前客户
     */
    @Schema(description = "当前客户信息")
    private String customerId;
//    private CustomerDTO customerDTO;
}
