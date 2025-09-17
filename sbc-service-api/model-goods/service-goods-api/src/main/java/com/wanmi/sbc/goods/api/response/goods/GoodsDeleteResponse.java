package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.goods.bean.vo.GoodsDeleteVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品删除返回对象
 * @author zhengyang
 * @className GoodsDeleteResponse
 * @date 2022/4/10 7:52 下午
 **/
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDeleteResponse {

    @Schema(description = "删除商品对象")
    private List<GoodsDeleteVO> goodsDeleteVOList;

    /** 商品库id */
    @Schema(description = "商品库id")
    private List<String> standardIds;
}

