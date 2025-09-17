package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 根据分类id批量查询请求结构
 *
 * @author wanggang
 * @version 1.0
 * @createDate 2018/11/1 10:00
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateListByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 2920233915543596535L;

    /**
     * 批量分类id
     */
    @Schema(description = "批量分类id")
    @NotNull
    private List<Long> cateIds;
}
