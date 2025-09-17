package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.GoodsBuyType;
import com.wanmi.sbc.goods.bean.enums.SaleType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description 商品新增请求
 * @author daiyitian
 * @date 2021/4/9 15:22
 */
@Schema
@Data
public class GoodsInfoModifySimpleBySkuNoRequest extends BaseRequest {


  @NotNull
  @Schema(description = "店铺id")
  private Long storeId;

  @NotBlank
  @Schema(description = "商品sku编码")
  private String skuNo;

  @NotBlank
  @Schema(description = "商品名称")
  private String spuName;

  @NotEmpty
  @Schema(description = "店铺分类id")
  private List<Long> storeCateIds;

  @Schema(description = "商品副标题")
  private String goodsSubtitle;

  @NotBlank
  @Schema(description = "计量单位")
  private String goodsUnit;

  @Schema(description = "销售类型 0: 批发 1: 零售")
  private SaleType goodsSaleType;

  @Schema(description = "下单方式 0立即购买 1购物车")
  private List<GoodsBuyType> goodsBuyTypeList;

  @Schema(description = "商品图片地址")
  private List<String> imageUrls;

  @Schema(description = "商品视频地址")
  private String goodsVideo;

  @NotNull
  @Schema(description = "商品重量")
  private BigDecimal goodsWeight;

  @NotNull
  @Schema(description = "商品体积 单位：m3")
  private BigDecimal goodsVolume;

  @Schema(description = "商品详情")
  private String goodsDetail;

  @Schema(description = "sku图片地址")
  private String goodsInfoImg;

  @Schema(description = "市场价")
  private BigDecimal marketPrice;

  @Schema(description = "供货价")
  private BigDecimal supplyPrice;

  @Schema(description = "花费积分")
  private Long buyPoint;

  @Schema(description = "条形码")
  private String goodsInfoBarcode;

  @Schema(description = "商品规格值列表")
  private List<String> goodsSpecDetails;

  @Schema(description = "电子卡券ID")
  private Long electronicCouponsId;

  @Schema(description = "库存")
  private Long stock;
}
