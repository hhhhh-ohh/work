package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Data;

/**
 * 商品实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsPageSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -1525761754544125818L;

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号，采用UUID")
    private String goodsId;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    @CanEmpty
    private Long brandId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图")
    @CanEmpty
    private String goodsImg;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    @CanEmpty
    private BigDecimal marketPrice;

    /**
     * 门店市场价
     */
    @Schema(description = "门店市场价")
    @CanEmpty
    private BigDecimal storeMarketPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    @CanEmpty
    private BigDecimal supplyPrice;

    /**
     * 商品来源，0供应商，1商家
     */
    @Schema(description = "商品来源，0供应商，1商家")
    private Integer goodsSource;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Schema(description = "是否定时上架 true:是,false:否")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 是否定时下架
     */
    @Schema(description = "是否定时下架 true:是,false:否")
    private Boolean takedownTimeFlag;

    /**
     * 定时下架时间
     */
    @Schema(description = "定时下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime takedownTime;

    /**
     * 是否多规格标记
     */
    @Schema(description = "是否多规格标记", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer moreSpecFlag;

    /**
     * 设价类型 0:按客户 1:按订货量 2:按市场价
     */
    @Schema(description = "设价类型", contentSchema = com.wanmi.sbc.goods.bean.enums.PriceType.class)
    private Integer priceType;

    /**
     * 是否允许独立设价 0:不允许, 1:允许
     */
    @Schema(description = "是否允许独立设价", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer allowPriceSet;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer vendibility;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String supplierName;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 所属供应商商品Id
     */
    @Schema(description = "所属供应商商品Id")
    private String providerGoodsId;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private CheckStatus auditStatus;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String auditReason;


    /**
     * 库存，根据相关所有SKU库存来合计
     */
    @Schema(description = "库存，根据相关所有SKU库存来合计")
    private Long stock;

    /**
     * 一对多关系，多个SKU编号
     */
    @Schema(description = "一对多关系，多个SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 多对多关系，多个店铺分类编号
     */
    @Schema(description = "多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 销售类别
     */
    @Schema(description = "销售类别", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 注水销量
     */
    @Schema(description = "注水销量")
    private Long shamSalesNum;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNo;

    /**
     * 是否单规格
     */
    @Schema(description = "是否单规格")
    private Boolean singleSpecFlag;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    private String deleteReason;

    /**
     * 下架原因
     */
    private String addFalseReason;


    /**
     * 购买方式 0立即购买,1购物车,内容以,相隔
     */
    @Schema(description = "购买方式 0立即购买 1购物车,内容以,相隔")
    private String goodsBuyTypes;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    @Schema(description = "供应商店铺状态 0：关店 1：开店")
    private Integer providerStatus;

    /**
     * 是否禁止在新增拼团活动时选择
     */
    @Schema(description = "是否禁止在新增拼团活动时选择")
    private boolean grouponForbiddenFlag;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积，单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 计量单位
     */
    @Schema(description = "计量单位")
    private String goodsUnit;

    /**
     * 商品视频地址
     */
    @Schema(description = "商品视频地址")
    private String goodsVideo;

    /**
     * 商品详情
     */
    @Schema(description = "商品详情")
    private String goodsDetail;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 品牌
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 平台类目
     */
    @Schema(description = "平台类目名称")
    private String cateName;

    /**
     * 平台类目
     */
    @Schema(description = "商家平台名称")
    private String storeCateName;

    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private String specText;

    /**
     * 三方平台类型，0，linkedmall
     */
    @Schema(description = "三方平台类型，0，linkedmall")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 商品插件类型
     */
    @Schema(description = "商品插件类型 0:普通商品 1：跨境商品 2：O2O")
    private PluginType pluginType;

    /**
     * 供应商id
     */
    @Schema(description = "供应商id")
    private Long providerId;

    public Integer getVendibility(){
        if (Objects.nonNull(providerGoodsId)) {
            //供应商商品可售（商品上架、未删除、已审核，店铺开店）
            if((Objects.nonNull(vendibility) && DeleteFlag.YES.toValue() == vendibility)
                    && Constants.yes.equals(providerStatus)){
                return Constants.yes;
            } else {
                return Constants.no;
            }
        }
        return Constants.yes;
    }

    /**
     * 扩展属性
     */
    @Schema(description = "扩展属性 pluginType=1时（tradeType:交易类型， electronPort:电子口岸）")
    private Object extendedAttributes;

    /**
     * 是否可编辑
     */
    @Schema(description = "是否可编辑")
    private BoolFlag editFlag = BoolFlag.YES;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    @CanEmpty
    private BigDecimal linePrice;

    /**
     * 标识前端是都展示"库存不足"
     */
    @Schema(description = "标识前端是都展示'库存不足'")
    private Boolean showStockOutFlag;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签列表")
    private List<GoodsLabelVO> goodsLabelList;
}
