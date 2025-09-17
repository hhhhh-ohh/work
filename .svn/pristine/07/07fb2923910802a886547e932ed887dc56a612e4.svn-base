package com.wanmi.sbc.goods.api.response.flashsale;

import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * program: sbc-micro-service
 * 参与活动商品详情列表数据返回值
 *
 * @date 2019-06-12 11:14
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailListResp implements Serializable {

    private static final long serialVersionUID = 3484986143686358958L;

    @Schema(description = "参与活动商品信息")
    private List<FlashSaleGoodsVO> flashSaleGoodsVOS;
}
