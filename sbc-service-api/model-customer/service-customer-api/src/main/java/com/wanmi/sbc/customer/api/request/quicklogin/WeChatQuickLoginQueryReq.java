package com.wanmi.sbc.customer.api.request.quicklogin;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2020-05-22 16:20
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeChatQuickLoginQueryReq extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "应用内用户唯一标示")
    private String openId;

}