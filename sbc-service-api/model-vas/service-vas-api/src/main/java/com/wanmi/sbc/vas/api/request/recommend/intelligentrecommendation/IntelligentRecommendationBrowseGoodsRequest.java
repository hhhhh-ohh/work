package com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @description 商品智能推荐-浏览商品埋点参数
 * @author lvzhenwei
 * @date 2021/4/9 4:08 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationBrowseGoodsRequest extends BaseRequest {
  private static final long serialVersionUID = 1L;

  /** 商品智能推荐-商品id */
  private String goodsId;

  /** 商品智能推荐-类目id */
  private Long cateId;

  /** 商品智能推荐-品牌id */
  private Long brandId;

  /** 推荐类型 热门:0 相关性:1 */
  private Integer recommendType;

  /** 浏览类目还是商品 商品为1，类目为0, 品牌为2 */
  private Integer item;

  /** 坑位 */
  @NotNull private PositionType type;

  /** 坑位（大数据平台用） */
  private Integer location;

  /** 客户id */
  private String customerId;

  /** 事件类型 0：浏览，1：点击 */
  private Integer eventType = NumberUtils.INTEGER_ZERO;

  /** 发生时间 */
  private String createTime;

  /** 终端 */
  @Schema(description = "终端类型，PC：1，H5：2， APP：3，小程序：4")
  private Integer terminalType;
}
