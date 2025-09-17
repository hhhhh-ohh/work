package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>根据商品id查询商品属性列表结果</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyListForGoodsResponse extends BasicResponse {
    private static final long serialVersionUID = -5298142227690723933L;

    /**
     * 商品属性列表结果
     */
    @Schema(description = "商品属性列表结果")
    private List<GoodsPropertyVO> goodsPropertyVOList = new ArrayList<>();

    /**
     * 商品属性值列表结果
     */
    @Schema(description = "商品属性值列表结果")
    private List<GoodsPropertyDetailVO> goodsPropertyDetailVOList = new ArrayList<>();

    /**
     * 商品与属性关联列表结果
     */
    @Schema(description = "商品与属性关联列表结果")
    private List<GoodsPropertyDetailRelVO> goodsPropertyDetailRelVOList = new ArrayList<>();

    /**
     * 已选省市列表
     */
    @Schema(description = "已选省市列表")
    private List<PlatformAddressVO> provinceVOList = new ArrayList<>();

    /**
     * 已选国家地区列表
     */
    @Schema(description = "已选国家地区列表")
    private List<PlatformCountryVO> countryVOList = new ArrayList<>();
}
