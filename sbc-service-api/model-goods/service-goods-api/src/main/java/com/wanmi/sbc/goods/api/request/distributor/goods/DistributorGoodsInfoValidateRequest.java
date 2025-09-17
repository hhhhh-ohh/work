package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分销员商品-根据分销员的会员ID查询分销员商品分页对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class DistributorGoodsInfoValidateRequest extends GoodsMutexValidateDTO {
    private static final long serialVersionUID = 1L;
}
