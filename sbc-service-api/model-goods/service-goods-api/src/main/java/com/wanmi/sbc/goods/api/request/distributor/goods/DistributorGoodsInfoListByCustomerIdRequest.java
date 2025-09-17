package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分销员商品-根据分销员的会员ID查询分销员商品对象
 *
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsInfoListByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 8209961419657581816L;

    /**
     * 分销员对应的会员ID
     */
    @Schema(description = "分销员对应的会员ID")
    private String customerId;

    @Schema(description = "店铺Id")
    private Long storeId;
}
