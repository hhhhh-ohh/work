package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Schema
public class EsGoodsInfoQueryRequest extends BaseQueryRequest {

    /**
     * 未登录时,前端采购单缓存信息
     */
    @Valid
    @Schema(description = "未登录时,前端采购单缓存信息")
    private List<EsGoodsInfoDTO> esGoodsInfoDTOList;

    /**
     * 批量商品ID
     */
    @Schema(description = "批量商品ID")
    private List<String> goodsIds;

    @Schema(description = "Not in 商品ID")
    private List<String> notGoodsIds;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;

    @Schema(description = "Not inSKU编号")
    private List<String> notGoodsInfoIds;

    /**
     * 模糊条件-商品名称
     */
    @Schema(description = "模糊条件-商品名称")
    private String likeGoodsName;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private Integer addedFlag;

    /**
     * 可售状态
     */
    @Schema(description = "可售状态")
    private Integer vendibility;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 批量分类编号
     */
    @Schema(description = "批量分类编号")
    private List<Long> cateIds;

    /**
     * 批量标签编号
     */
    @Schema(description = "批量标签编号")
    private List<Long> labelIds;

    /**
     * 批量店铺分类编号
     */
    @Schema(description = "批量店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 批量品牌编号
     */
    @Schema(description = "批量品牌编号")
    private List<Long> brandIds;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private Integer delFlag;

    /**
     * 库存状态
     * null:所有,1:有货,0:无货
     */
    @Schema(description = "库存状态")
    private Integer stockFlag;

    /***
     * 是否打开只显示有货开关
     */
    private transient Integer isOutOfStockShow;

    /**
     * 排序标识
     * 0: 销量倒序->时间倒序->市场价倒序
     * 1:上架时间倒序->销量倒序->市场价倒序
     * 2:市场价倒序->销量倒序
     * 3:市场价升序->销量倒序
     * 4:销量倒序->市场价倒序
     * 5:评论数倒序->销量倒序->市场价倒序
     * 6:好评倒序->销量倒序->市场价倒序
     * 7:收藏倒序->销量倒序->市场价倒序
     * 10:排序号倒序->创建时间倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级")
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    /**
     * 关键字，可能含空格
     */
    @Schema(description = "关键字，可能含空格")
    private String keywords;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private Integer companyType;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺IDs
     */
    @Schema(description = "店铺IDs")
    private List<Long> storeIds;

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    private String contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    private String contractEndDate;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态", contentSchema = com.wanmi.sbc.customer.bean.enums.StoreState.class)
    private Integer storeState;

    /**
     * 禁售状态
     */
    @Schema(description = "禁售状态")
    private Integer forbidStatus;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.CheckStatus.class)
    private Integer auditStatus;

    /**
     * 精确查询-规格值参数
     */
    @Schema(description = "精确查询-规格值参数")
    private List<EsSpecQueryRequest> specs = new ArrayList<>();

    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 是否需要反查分类
     */
    @Schema(description = "是否需要反查分类",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean cateAggFlag;

    /**
     * 是否需要反查品牌
     */
    @Schema(description = "是否需要反查品牌",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean brandAggFlag;

    /**
     * 是否需要反查标签
     */
    @Schema(description = "是否需要反查标签",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean labelAggFlag;

    /**
     * 是否需要反查品牌
     */
    @Schema(description = "是否需要反查品牌",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean propAggFlag;

    /**
     * 多个 属性与对应的属性值id列表
     */
    @Schema(description = "多个 属性与对应的属性值id列表")
    private List<EsPropQueryRequest> propDetails = new ArrayList<>();

    @Schema(description = "是否查询商品")
    private boolean isQueryGoods = false;

    @Schema(description = "分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private Integer distributionGoodsAudit;


    @Schema(description = "企业购商品审核状态 0:无状态 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销")
    private Integer enterPriseAuditStatus;

    @Schema(description = "隐藏已选分销商品,false:不隐藏，true:隐藏")
    private boolean hideSelectedDistributionGoods = false;

    @Schema(description = "排除分销商品")
    private boolean excludeDistributionGoods = false;

    /**
     * 分销商品状态，配合分销开关使用
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer distributionGoodsStatus;

    /**
     * 企业购商品过滤
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer enterPriseGoodsStatus;

    /**
     * 批量分销商品SKU编号
     */
    @Schema(description = "批量分销商品SKU编号")
    private List<String> distributionGoodsInfoIds;

    /**
     * 分销商品信息
     */
    @Schema(description = "分销商品信息")
    private List<DistributorGoodsInfoVO> distributionGoodsInfoList;


    /**
     * 批量分销商品SKU编号
     */
    @Schema(description = "积分抵扣选项")
    private Boolean pointsUsageFlag;

    /**
     * 是否魔方商品列表
     */
    @Schema(description = "是否魔方商品列表 true:是")
    private Boolean moFangFlag;

    /**
     * 不显示linkedMall商品
     */
    @Schema(description = "不显示linkedMall商品 true:是")
    private Boolean notShowLinkedMallFlag;

    /**
     * 不显示VOP商品
     */
    @Schema(description = "不显示VOP商品 true:是")
    private Boolean notShowVOPFlag;

    @Schema(description = "商品来源，0供应商，1商家 2 linkedMall")
    private GoodsSource goodsSource;

    /**
     * 查询数据终端，为了处理pc端分销和企业价逻辑
     */
    @Schema(description = "查询数据终端，为了处理pc端分销和企业价逻辑")
    private String terminalSource;

    /**
     * 价格区间最低价
     */
    private BigDecimal salePriceLow;

    /**
     * 价格区间最高价
     */
    private BigDecimal salePriceHigh;

    /**
     * 店铺名称
     */
    private String likeStoreName;

    /**
     * 商品标题
     */
    @Schema(description = "商品标题权重")
    private Float goodsInfoNameBoost;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签权重")
    private Float goodsLabelNameBoost;

    /**
     * 商品规格值
     */
    @Schema(description = "商品规格值权重")
    private Float specTextBoost;

    /**
     * 商品属性值
     */
    @Schema(description = "商品属性值权重")
    private Float goodsPropDetailNestNameBoost;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌权重")
    private Float brandNameBoost;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目权重")
    private Float cateNameBoost;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题权重")
    private Float goodsSubtitleBoost;

    /**
     * 模糊条件-SPU编码
     */
    @Schema(description = "模糊条件-SPU编码")
    private String likeGoodsNo;

    /**
     * 模糊条件-SKU编码
     */
    @Schema(description = "模糊条件-SKU编码")
    private String likeGoodsInfoNo;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String likeSupplierName;

    /**
     * 最小市场价
     */
    @Schema(description = "最小市场价")
    private BigDecimal minMarketPrice;

    /**
     * 最大市场价
     */
    @Schema(description = "最大市场价")
    private BigDecimal maxMarketPrice;

    /**
     * 最小实际销量
     */
    @Schema(description = "实际销量查询区间：最小实际销量")
    private Long minGoodsSalesNum;

    /**
     * 最大实际销量
     */
    @Schema(description = "实际销量查询区间：最大实际销量")
    private Long maxGoodsSalesNum;

    /**
     * 最小库存
     */
    @Schema(description = "库存查询区间：最小库存")
    private Long minStock;

    /**
     * 最大库存
     */
    @Schema(description = "库存查询区间：最大库存")
    private Long maxStock;


    /**
     * 商品名称(不分词模糊查询条件)
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品类型
     */
    @Schema(description = "商品类型 0:一般贸易,1:跨境")
    private Integer pluginType;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码")
    private String goodsInfoNo;

    /**
     *
     */
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 电子口岸
     */
    @Schema(description = "电子口岸")
    private String electronPort;

    /**
     * 备案编号
     */
    @Schema(description = "备案编号")
    private String recordNo;

    /**
     * 备案状态 0：待备案，1：备案中,2：备案成功，3：备案失败
     */
    @Schema(description = "备案状态")
    private Integer recordStatus;

    /**
     * 跨境商品删除标记
     */
    @Schema(description = "跨境商品删除标记")
    private Integer borderDelFlag;

    @Schema(description = "排序时时候是es_goods索引")
    private Boolean isGoods;

    /**
     * 分类uuid
     */
    @Schema(description = "分类uuid")
    private String cateUuid;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 是否需要聚合处理
     */
    @Schema(description = "是否需要聚合处理，false:不需要，默认需要")
    public Boolean aggFlag;

    /**
     * 是否魔方查询
     */
    @Schema(description = "是否魔方查询")
    public Boolean isMoFang;

    @Schema(description = "运费模板")
    public Long freightTemplateId;

    @Schema(description = "供应商Id")
    private Long providerStoreId;

    /**
     * 不显示VOP商品
     */
    @Schema(description = "不显示代销商品 true:是")
    private Boolean notShowProvideFlag;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购,0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 是否魔方查询
     */
    @Schema(description = "是否魔方查询")
    private Boolean magicSearch = Boolean.FALSE;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;

    /**
     * 订货号模糊
     */
    @Schema(description = "订货号模糊")
    private String likeQuickOrderNo;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    /**
     * 批量订货号
     */
    @Schema(description = "批量订货号")
    private List<String> quickOrderNos;

    /**
     * 供应商订货号
     */
    @Schema(description = "供应商订货号")
    private String providerQuickOrderNo;

    /**
     * 是否按goodsId去重
     */
    @Schema(description = "是否按goodsId去重")
    private Boolean isDistinctGoodsId = false;


    /**
     * 根据营销类型设置查询参数
     * @param marketingScopeType
     * @param scopeIds
     */
    public void dealMarketingGoods(MarketingScopeType marketingScopeType, List<String> scopeIds, Long storeId){
        if(Objects.nonNull(storeId) && storeId!=0 && !storeId.equals(Constants.BOSS_DEFAULT_STORE_ID)){
            this.setStoreId(storeId);
        }
        if(WmCollectionUtils.isEmpty(scopeIds)){
            return;
        }
        List<Long> longScopeIds = null;
        if (StringUtils.isNumeric(WmCollectionUtils.findFirst(scopeIds))) {
            longScopeIds = scopeIds.stream().map(Long::valueOf).collect(Collectors.toList());
        }
        switch (marketingScopeType) {
            case SCOPE_TYPE_ALL:
                this.setStoreId(storeId);
                break;
            case SCOPE_TYPE_CUSTOM:
                this.setGoodsInfoIds(scopeIds);
                break;
            case SCOPE_TYPE_BRAND:
                if (this.getBrandIds() == null) {
                    this.setBrandIds(longScopeIds);
                } else {
                    this.getBrandIds().addAll(longScopeIds);
                }
                break;
            case SCOPE_TYPE_STORE_CATE:
            case SCOPE_TYPE_O2O_CATE:
                if (this.getStoreCateIds() == null) {
                    this.setStoreCateIds(longScopeIds);
                } else {
                    this.getStoreCateIds().addAll(longScopeIds);
                }
                break;
            case SCOPE_TYPE_GOODS_CATE:
                if (this.getCateIds() == null) {
                    this.setCateIds(longScopeIds);
                } else {
                    this.getCateIds().addAll(longScopeIds);
                }
                break;
            default:
        }
    }

    /**
     * 部分活动限制虚拟商品、卡券商品
     * @param marketingType
     */
    public void marketingFilterVirtualGoods(MarketingType marketingType) {
        if (marketingType == null) {
            return;
        }
        switch (marketingType) {
            case GIFT:
            case HALF_PRICE_SECOND_PIECE:
            case BUYOUT_PRICE:
            case SUITS:
                this.goodsType = GoodsType.REAL_GOODS.toValue();
                break;
            case PREFERENTIAL:
                this.goodsType = GoodsType.REAL_GOODS.toValue();
                break;
            default:
        }
    }
}
