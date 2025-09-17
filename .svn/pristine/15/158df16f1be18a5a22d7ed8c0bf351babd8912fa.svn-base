package com.wanmi.sbc.customer.api.request.quicklogin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方关系表删除
 *
 * @Author: songhanlin
 * @Date: Created In 10:01 AM 2018/8/8
 * @Description: 第三方关系表
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdLoginRelationDeleteByCustomerRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    @NotNull
    private String customerId;

    /**
     * 第三方类型 0:wechat
     */
    @Schema(description = "第三方类型")
    @NotNull
    private ThirdLoginType thirdLoginType;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
    @NotNull
    private Long storeId;

}
