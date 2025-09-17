package com.wanmi.sbc.dw.bean.recommend;

import com.wanmi.sbc.dw.bean.enums.Item;
import com.wanmi.sbc.dw.bean.enums.ManualRecommendStatus;
import com.wanmi.sbc.dw.bean.enums.PositionType;
import com.wanmi.sbc.dw.bean.enums.RecommendType;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.recommend.RecommedData
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/3 19:58
 * @Version: 1.0
 */
@Data
public class RecommendData {
    /**
     * 手动 热门推荐是否开启 '状态,0:未启用1:已启用',
     */
    private Integer manualRecommendStatus;
    /**
     * 推荐类型  热门:0 相关性:1,兴趣推荐为2
     */
    private Integer recommendType;
    /**
     * 查询类目还是商品 商品为0，类目为1,品牌为2
     */
    private Integer item;
    /**
     * 坑位
     */
    private Integer location;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 返回的商品List集合
     */
    private List<String> relationGoodsIdList;
    /**
     * 返回的类目List集合
     */
    private List<Long> relationCateIdList;
    /**
     * 返回的兴趣品牌List集合
     */
    private List<Long> interestBrandList;
    /**
     * 返回的兴趣品牌List集合
     */
    private List<Long> interestCateList;


    public String logPrint() {
        return "RelationRecommendRequest:{" +
                "\n--------------------------------------------" +
                "\n relationGoodsIdList=" + relationGoodsIdList +
                "\n relationCateIdList=" + relationCateIdList +
                "\n interestBrandList=" + interestBrandList +
                "\n interestCateList=" + interestCateList +
                '}';
    }


}
