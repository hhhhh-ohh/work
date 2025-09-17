package com.wanmi.sbc.elastic.api.request.distributionmatter;

import com.wanmi.sbc.common.base.EsInitRequest;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.MatterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author houshuai
 * 分销素材分页查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class EsDistributionGoodsMatterInitRequest extends EsInitRequest {

    private static final long serialVersionUID = 3526136339091603088L;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    private String goodsInfoId;

    /**
     * 发布者id
     */
    @Schema(description = "发布者id")
    private String operatorId;

    /**
     * 引用次数范围（小）
     */
    @Schema(description = "引用次数范围（小）")
    private Integer recommendNumMin;

    /**
     * 引用次数范围（大）
     */
    @Schema(description = "引用次数范围（大）")
    private Integer recommendNumMax;

    /**
     * 引用次数排序
     */
    @Schema(description = "引用次数排序")
    private SortType sortByRecommendNum;


    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * Sku编码
     */
    @Schema(description = "Sku编码")
    private String goodsInfoNo;

    /**
     * 平台类目-仅限三级类目
     */
    @Schema(description = "平台类目")
    private Long cateId;

    /**
     * 批量商品分类
     */
    @Schema(description = "批量商品分类")
    private List<Long> cateIds;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 素材类型
     */
    @Schema(description = "素材类型")
    private MatterType matterType;

    /**
     * 登录人id,用来查询分销员等级
     */
    @Schema(description = "customerId")
    private String customerId;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private DistributionGoodsAudit distributionGoodsAudit;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "分销素材id")
    public List<String> idList;
}