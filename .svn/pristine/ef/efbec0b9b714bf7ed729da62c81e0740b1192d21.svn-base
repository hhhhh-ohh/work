package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 黄昭
 * @className EditHeadImgRequest
 * @description 用户更换头像
 * @date 2022/2/22 10:32
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditHeadImgRequest extends BaseRequest {

    private static final long serialVersionUID = -5522032044638985956L;
    @Schema(description = "头像")
    private String headImg;

    @Schema(description = "用户Id")
    private String customerId;
}