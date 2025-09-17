package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsMainImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description
 * @author  wur
 * @date: 2022/7/28 15:03
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsMainImageResponse extends BasicResponse {

    private static final long serialVersionUID = 2435750105858150399L;

    /**
     * 商品图片信息
     */
    @Schema(description = "商品图片信息")
    private List<GoodsMainImageVO> goodsMainImageVOList;

}
