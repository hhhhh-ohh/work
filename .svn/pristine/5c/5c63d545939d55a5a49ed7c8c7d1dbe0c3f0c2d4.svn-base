package com.wanmi.sbc.elastic.api.request.pointsgoods;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * <p>积分商品表通用初始化请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsPointsGoodsInitRequest extends EsInitRequest {
    private static final long serialVersionUID = 1L;


    @Schema(description = "积分商品ids-批量查询")
    private List<String> pointsGoodsIds;

    /**
     * SpuId
     */
    @Schema(description = "SpuId")
    private List<String> goodsIds;

    /**
     * SkuId
     */
    @Schema(description = "SkuId")
    private List<String> goodsInfoIds;

    @Schema(description = "批量积分商品id")
    private List<String> idList;

    /**
     * 如果有范围进行初始化索引，无需删索引
     *
     * @return true:clear index false:no
     */
    public boolean isClearEsIndex() {
        if (CollectionUtils.isNotEmpty(goodsIds)
                || CollectionUtils.isNotEmpty(pointsGoodsIds)
                || CollectionUtils.isNotEmpty(goodsInfoIds)
                || CollectionUtils.isNotEmpty(idList)
                || (this.getPageNum() != null && this.getPageNum() > 0)) {
            return false;
        }
        return true;
    }
}