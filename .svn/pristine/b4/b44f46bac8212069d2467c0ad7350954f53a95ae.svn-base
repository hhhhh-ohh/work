package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.DistributorGoodsInfoModifySequenceDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分销员商品-修改分销商品顺序对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorGoodsInfoModifySequenceRequest extends BaseRequest {

    /**
     * 分销员商品对象集合
     */
    @Schema(description = "分销员商品对象集合")
    @NotNull
    private List<DistributorGoodsInfoModifySequenceDTO> distributorGoodsInfoDTOList;
}
