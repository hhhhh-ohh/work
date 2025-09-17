package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateModifyRequest
 * 修改商品分类信息请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午4:54
 */
@Schema
@Data
public class GoodsCateContainsVirtualRequest extends BaseRequest {

    private static final long serialVersionUID = 3593257069274533816L;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    @NotNull
    private Long cateId;

    @Schema(description = "是否可以包含虚拟商品，0-不可以；1-可以")
    @NotNull
    private BoolFlag containsVirtual;
}
