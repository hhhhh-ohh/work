package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.goods.bean.dto.DistributionGoodsInfoModifyDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsModifyRequest
 * 编辑分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class DistributionGoodsModifyRequest extends DistributionGoodsInfoModifyDTO implements Serializable {
    private static final long serialVersionUID = 7302772596661074274L;
}
