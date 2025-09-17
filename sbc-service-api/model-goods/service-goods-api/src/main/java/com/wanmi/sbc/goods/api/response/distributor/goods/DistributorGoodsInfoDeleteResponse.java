package com.wanmi.sbc.goods.api.response.distributor.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分销员商品-根据分销员-会员ID和SKU编号删除分销员商品对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsInfoDeleteResponse extends BasicResponse {

    @Schema(description = "结果数")
    private Integer result;
}
