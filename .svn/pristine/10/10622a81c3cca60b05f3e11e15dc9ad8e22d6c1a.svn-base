package com.wanmi.sbc.customer.api.request.quicklogin;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据关联Id&第三方登录方式查询第三方登录关系request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdLoginRelationByUidRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方登录主键
     */
    @Schema(description = "第三方登录主键")
    @NotNull
    private String thirdLoginUid;

    /**
     * 第三方类型 0:wechat
     */
    @Schema(description = "第三方类型")
    @NotNull
    private ThirdLoginType thirdLoginType;

    /**
     * 删除标记
     */
    @Schema(description = "第三方类型")
    @NotNull
    private DeleteFlag delFlag;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
    @NotNull
    private Long storeId;

}
