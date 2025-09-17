package com.wanmi.sbc.elastic.goods.model.root;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ES存储的商品属性值关系
 *
 * @auther bail
 * @create 2018/03/23 10:04
 */
@Data
@Schema
public class GoodsPropAddressNest {

  /** 省市id */
  @Schema(description = "省市id")
  private String addrId;

  /** 省市名称 */
  @Schema(description = "省市名称")
  private String addrName;
}
