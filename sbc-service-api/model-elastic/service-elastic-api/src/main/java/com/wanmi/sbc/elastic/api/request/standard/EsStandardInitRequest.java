package com.wanmi.sbc.elastic.api.request.standard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.EsInitRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ES商品初始化请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsStandardInitRequest extends EsInitRequest {

    private static final long serialVersionUID = -1L;

    /**
     * spuId
     */
    @Schema(description = "商品库id")
    private String goodsId;

    /**
     * spuIds
     */
    @Schema(description = "商品库id")
    private List<String> goodsIds;

    /**
     * 品牌ids
     */
    @Schema(description = "品牌ids")
    private List<Long> brandIds;

    /**
     * 批量商品分类
     */
    @Schema(description = "批量商品分类")
    private List<Long> cateIds;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    @Schema(description = "商品来源，0供应商，1商家，2 linkedmall")
    private Integer goodsSource;

    /**
     * 创建开始时间
     */
    @Schema(description = "创建开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;

    /**
     * 创建结束时间
     */
    @Schema(description = "创建结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 关联的商品id
     */
    @Schema(description = "关联的商品id")
    private List<String> relGoodsIds;

    /**
     * 初始化开始页码
     */
    @Schema(description = "初始化每批数量")
    private Integer pageSize;

    @Schema(description = "批量商品id")
    private List<String> idList;

    @Schema(description = "是否需要删除索引重新创建，0：不要，1：需要")
    private DefaultFlag clearEsIndexFlag;

    /**
     * 如果有范围进行初始化索引，无需删索引
     *
     * @return true:clear index false:no
     */
    public boolean isClearEsIndex() {
        if (StringUtils.isNotBlank(goodsId) || CollectionUtils.isNotEmpty(goodsIds) || CollectionUtils.isNotEmpty(brandIds)
                || CollectionUtils.isNotEmpty(cateIds)
                || CollectionUtils.isNotEmpty(relGoodsIds)
                || goodsSource != null
                || createTimeBegin != null || createTimeEnd != null
                || CollectionUtils.isNotEmpty(idList)
                || super.getPageNum() != null) {
            return false;
        }
        return true;
    }
}
