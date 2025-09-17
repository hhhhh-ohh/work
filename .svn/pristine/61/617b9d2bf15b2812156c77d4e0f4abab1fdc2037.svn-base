package com.wanmi.sbc.dw.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.dw.bean.enums.Item;
import com.wanmi.sbc.dw.bean.enums.ManualRecommendStatus;
import com.wanmi.sbc.dw.bean.enums.PositionType;
import com.wanmi.sbc.dw.bean.enums.RecommendType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: com.wanmi.sbc.dw.api.request.GoodsRelationRecommend
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/1 14:17
 * @Version: 1.0
 */

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationRecommendRequest extends BaseRequest {

    /**
     * {
     *     "queryType":1,
     *      "location":"",
     *       "customerId":"123456789",
     *        "goods_ids":["6666666","2222222"],
     *         "pageSize":2,
     * "row_number":0
     *
     * }
     */
    /**
     * 手动 热门推荐是否开启 '状态,0:未启用1:已启用',
     */
    private Integer manualRecommendStatus;
    /**
     * 推荐类型  热门:0 相关性:1 2新区
     */
    private Integer recommendType;
    /**
     * 查询类目还是商品 商品为0，类目为1 2就是品牌
     */
    private Integer item;
    /**
     * 兴趣推荐 类目和品牌
     */
    private String interestItem;
    /**
     * 坑位
     */
    private Integer location;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 相关性推荐需要查询的商品集合
     */
    private List<String> relationGoodsIdList;
    /**
     * 相关性推荐需要查询的品类集合
     */
    private List<Long> relationCateIdList;

    /**
     * 一页展示多少条
     */
    private Integer pageSize;
    /**
     * 从第几条开始展示
     */
    private Integer pageIndex;

    /**
     * 相关性推荐浏览商品记录
     */
    private String viewGoods = "";

    /**
     * 浏览类目记录
     */
    private Long viewCate;

    /**
     * 相关性推荐浏览商品记录
     */
    private Integer rowNumber =0;

    private String createTime;

    @Override
    public String toString() {
        return "RelationRecommendRequest:{" +
                "\n--------------------------------------------" +
                "\n manualRecommendStatus=" + manualRecommendStatus +
                "\n recommendType=" + recommendType +
                "\n item=" + item +
                "\n location='" + location +
                "\n customerId='" + customerId +
                "\n goods_ids=" + relationGoodsIdList +
                "\n relationCateIdList" + relationCateIdList +
                "\n pageSize=" + pageSize +
                "\n pageIndex=" + pageIndex +
                "\n--------------------------------------------" +
                '}';
    }

    public String logPrint() {
        return "RelationRecommendRequest:{" +
                "\n--------------------------------------------" +
                "\n manualRecommendStatus=" + ManualRecommendStatus.getValue(manualRecommendStatus) +
                "\n recommendType=" + RecommendType.getValue(recommendType) +
                "\n item=" + Item.getValue(item) +
                "\n location=" + PositionType.fromValue(location.intValue()) +
                "\n customerId=" + customerId +
                "\n relationGoodsIdList=" + relationGoodsIdList +
                "\n relationCateIdList=" + relationCateIdList +
                "\n pageSize=" + pageSize +
                "\n pageIndex=" + pageIndex +
                "\n--------------------------------------------" +
                '}';
    }
}
