package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据主键删除商品分类请求类</p>
 * author: sunkun
 * Date: 2018-11-06
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BossGoodsCateDeleteByIdResponse extends BasicResponse {

    private static final long serialVersionUID = -7175082114837663845L;

    @Schema(description = "商品分类Id")
    private List<Long> longList;
}
