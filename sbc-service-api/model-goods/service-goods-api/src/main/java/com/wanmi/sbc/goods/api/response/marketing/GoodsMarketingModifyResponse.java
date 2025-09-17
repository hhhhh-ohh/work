package com.wanmi.sbc.goods.api.response.marketing;

import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>商品营销</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsMarketingModifyResponse extends GoodsMarketingVO {
    private static final long serialVersionUID = -6950186349923860969L;
}
