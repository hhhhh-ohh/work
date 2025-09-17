package com.wanmi.sbc.goods.api.response.distributor.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分销员商品-根据分销员的会员ID和SPU编号查询分销员商品对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsInfoListByCustomerIdAndGoodsIdResponse extends BasicResponse {

    /**
     * 分销员商品列表
     */
    @Schema(description = "分销员商品列表")
    private List<DistributorGoodsInfoVO> distributorGoodsInfoVOList;
}
