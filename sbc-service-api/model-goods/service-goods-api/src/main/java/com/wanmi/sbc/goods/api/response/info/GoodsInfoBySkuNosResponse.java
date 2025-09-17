package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.google.common.collect.Sets;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/***
 * 批量查询SKU返回数据结构
 * @className GoodsInfoBySkuNosResponse
 * @author zhengyang
 * @date 2021/8/12 17:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GoodsInfoBySkuNosResponse extends BasicResponse {

    @Schema(description = "商品SKU集合")
    private List<GoodsInfoVO> goodsInfoVoList;

    /***
     * 获得返回值对应的GoodsInfoNo Set集合
     * @return
     */
    public Set<String> getSkuNoSet(){
        if(WmCollectionUtils.isNotEmpty(goodsInfoVoList)){
            return goodsInfoVoList.parallelStream().map(GoodsInfoVO::getGoodsInfoNo).collect(Collectors.toSet());
        }
        return Sets.newHashSet();
    }

    /***
     * 获得返回值对应的GoodsInfoNo Map集合
     * @return
     */
    public Map<String,GoodsInfoVO> getSkuNoMap(){
        return WmCollectionUtils.notEmpty2Map(goodsInfoVoList, GoodsInfoVO::getGoodsInfoNo);
    }
}
