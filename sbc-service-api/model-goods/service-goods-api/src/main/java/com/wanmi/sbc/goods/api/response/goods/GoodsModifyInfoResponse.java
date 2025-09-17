package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * 商品修改响应详情信息
 * @className GoodsModifyInfoResponse
 * @author zhengyang
 * @date 2021/7/15 17:50
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsModifyInfoResponse extends BasicResponse {

    /***
     * 编辑后商品信息
     */
    @Schema(description = "编辑后商品信息")
    private List<GoodsInfoVO> newGoodsInfo;

    /***
     * 编辑前商品信息
     */
    @Schema(description = "编辑前商品信息")
    private GoodsVO oldGoods;

    /***
     * 编辑前商品详情信息
     */
    @Schema(description = "编辑前商品详情信息")
    private List<GoodsInfoVO> oldGoodsInfos;

    /***
     * 删除的商品信息
     */
    @Schema(description = "删除的商品信息")
    private List<String> delInfoIds = new ArrayList<>();

    /***
     * 上下架商家代销商品的可售性
     */
    @Schema(description = "上下架商家代销商品的可售性")
    private Boolean isDealGoodsVendibility;
}
