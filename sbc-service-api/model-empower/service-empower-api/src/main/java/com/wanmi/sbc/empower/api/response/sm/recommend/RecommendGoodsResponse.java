package com.wanmi.sbc.empower.api.response.sm.recommend;

import com.wanmi.sbc.empower.bean.vo.sm.recommend.RecommendPositionVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
*
 * @description  查询智能推荐商品
 * @author  wur
 * @date: 2022/11/17 10:18
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RecommendGoodsResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商品ID
     */
    @Schema(description = "商品Id")
    private List<String> goodsIdList = new ArrayList<>();

    /**
     * 商品类目信息
     */
    @Schema(description = "商品类目Id")
    private List<String> goodsCateList = new ArrayList<>();

    /**
     * 坑位设置
     */
    @Schema(description = "坑位信息， 0: 购物车，1：商品详情，2: 商品列表，3：个人中心，4：会员中心，5: 收藏商品，6：支付成功页，7: 分类，8: 魔方")
    private int type;

    /**
     * 商品品牌信息
     */
    @Schema(description = "商品品牌Id")
    private List<String> goodsBrandList = new ArrayList<>();

    @Schema(description = "坑位信息")
    private RecommendPositionVO positionVO;

}