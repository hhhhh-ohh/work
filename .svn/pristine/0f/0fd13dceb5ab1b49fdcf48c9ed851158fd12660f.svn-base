package com.wanmi.sbc.setting.api.request.systemresource;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 批量移动素材资源请求
 * Created by yinxianzhi on 2018/10/12.
 */
@Getter
@Setter
@Schema
public class SystemResourceMoveRequest extends BaseRequest {
    /**
     * 素材分类编号
     */
    @NotNull
    @Schema(description = "素材分类编号")
    private Long cateId;

    /**
     * 批量素材资源ID
     */
    @NotEmpty
    @Schema(description = "批量素材资源ID")
    private List<Long> resourceIds;

    /**
     * 店铺标识
     */
    private Long storeId;

}
