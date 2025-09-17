package com.wanmi.sbc.elastic.api.request.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.EsInitRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ES商品SKU请求
 * Created by daiyitian on 2017/3/24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsInfoRequest extends EsInitRequest {

    private static final long serialVersionUID = -1L;

    /**
     * 批量商品SkuID
     */
    @Schema(description = "批量商品SkuID")
    private List<String> skuIds;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * spuId
     */
    @Schema(description = "spuId")
    private String goodsId;

    /**
     * spuIds
     */
    @Schema(description = "spuIds")
    private List<String> goodsIds;

    /**
     * 品牌ids
     */
    @Schema(description = "品牌ids")
    private List<Long> brandIds;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private CheckStatus auditStatus;


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

    @Schema(description = "批量商品id")
    private List<String> idList;

    @Schema(description = "请求插件类型")
    private PluginType pluginType;

    @Schema(description = "是否需要删除索引重新创建，0：不要，1：需要")
    private DefaultFlag clearEsIndexFlag;

    /**
     * 如果有范围进行初始化索引，无需删索引
     *
     * @return true:clear index false:no
     */
    public boolean isClearEsIndex() {
        if (CollectionUtils.isNotEmpty(skuIds)
                || companyInfoId != null || storeId != null
                || StringUtils.isNotBlank(goodsId) || CollectionUtils.isNotEmpty(goodsIds) || CollectionUtils.isNotEmpty(brandIds)
                || createTimeBegin != null || createTimeEnd != null
                || CollectionUtils.isNotEmpty(idList)
                || super.getPageNum() != null) {
            return false;
        }
        return true;
    }
}
