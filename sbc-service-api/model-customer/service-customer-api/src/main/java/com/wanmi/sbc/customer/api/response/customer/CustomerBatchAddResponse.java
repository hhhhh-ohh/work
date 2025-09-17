package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 批量会员新增响应类
 * @author  daiyitian
 * @date 2021/4/25 16:02
 **/
@Schema
@Data
public class CustomerBatchAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员id列表
     */
    @Schema(description = "会员id列表")
    private List<String> customerIds;
}
