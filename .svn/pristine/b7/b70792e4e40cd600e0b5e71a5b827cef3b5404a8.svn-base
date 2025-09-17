package com.wanmi.sbc.order.api.request.returnorder;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * 退单审核校验请求对象
 * @className ReturnOrderCheckRequest
 * @author zhengyang
 * @date 2022/4/25 2:30 下午
 **/
@Data
@Schema
public class ReturnOrderCheckRequest implements Serializable {

    @NotEmpty
    @Schema(description = "退单id集合")
    private List<String> rids;
}
