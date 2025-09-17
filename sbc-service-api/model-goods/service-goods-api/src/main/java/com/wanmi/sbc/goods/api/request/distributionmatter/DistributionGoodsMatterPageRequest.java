package com.wanmi.sbc.goods.api.request.distributionmatter;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.MatterType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;

@Data
@Schema
public class DistributionGoodsMatterPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 3526136339091603088L;

    @NotBlank
    @Schema(description = "商品skuid")
    private String goodsInfoId;

    @Schema(description = "发布者id")
    private String operatorId;

    /***
     * 发布者ID集合
     */
    private List<String> operatorIds;

    @Schema(description = "发布者账号")
    private String operatorAccount;

    @Schema(description = "发布者名称")
    private String operatorName;

    @Schema(description = "引用次数范围（小）")
    private Integer recommendNumMin;

    @Schema(description = "引用次数范围（大）")
    private Integer recommendNumMax;

    @Schema(description = "引用次数排序")
    private SortType sortByRecommendNum;


    @Schema(description = "商品名称")
    private String goodsInfoName;

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
    @Schema(description ="customerId")
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

    @Schema(description = "商品素材前端查询标志")
    private Boolean webGoodsFlag = Boolean.FALSE;

    /**
     * 店铺ids
     */
    @Schema(description = "店铺ids")
    private List<Long> storeIds;
}
