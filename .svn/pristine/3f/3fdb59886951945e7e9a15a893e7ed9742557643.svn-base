package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 品牌名称存在查询出参
 * @author malianfeng
 * @date 2022/9/7 15:46
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandNameExistResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 存在的名称列表
     */
    @Schema(description = "存在的名称列表")
    private List<String> existNames;
}
