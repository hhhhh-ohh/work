package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>验证签约分类下是否有商品请求类</p>
 * author: sunkun
 * Date: 2018-11-06
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BossGoodsCateCheckSignGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 7324372394206914695L;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long cateId;
}
