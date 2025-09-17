package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 商品SKU编辑视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
public class GoodsInfoModifyResponse extends GoodsInfoVO implements Serializable {
    private static final long serialVersionUID = 1013719988544197573L;
}
