package com.wanmi.sbc.elastic.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsStandardGoodsPageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateSimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品库分页查询结果
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsStandardPageResponse extends BasicResponse {

    /**
     * 商品库分页结果
     */
    private MicroServicePage<EsStandardGoodsPageVO> standardGoodsPage = new MicroServicePage<>();

    /**
     * 商品品牌所有数据
     */
    @Schema(description = "商品品牌所有数据")
    private List<GoodsBrandSimpleVO> goodsBrandList = new ArrayList<>();

    /**
     * 商品分类所有数据
     */
    @Schema(description = "商品分类所有数据")
    private List<GoodsCateSimpleVO> goodsCateList = new ArrayList<>();

    /**
     * 已被导入的商品库ID
     */
    @Schema(description = "已被导入的商品库ID")
    private List<String> usedStandard = new ArrayList<>();

    /**
     * 需要同步的商品库ID
     */
    @Schema(description = "需要同步的商品库ID")
    private List<String> needSynStandard = new ArrayList<>();
}
