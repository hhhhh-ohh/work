package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 判断品牌别名是否存在
 * @author malianfeng
 * @date 2022/8/30 17:44
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandNickNamesExistRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 要判断是否存在的品牌别名列表
     */
    @Schema(description = "要判断是否存在的品牌别名列表")
    @NotNull
    private List<String> nickNames;
}
