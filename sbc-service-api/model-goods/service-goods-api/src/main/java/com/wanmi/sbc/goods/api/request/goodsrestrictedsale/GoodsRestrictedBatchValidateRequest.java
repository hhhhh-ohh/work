package com.wanmi.sbc.goods.api.request.goodsrestrictedsale;

import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>限售配置校验请求</p>
 * @author baijz
 * @date 2020-04-08 11:20:28  这里拼团和秒杀只校验上线（限售）
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedBatchValidateRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量校验
	 */
	List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS;

    /**
     * 会员信息
	 */
	private CustomerVO customerVO;

	/**
	 * 是否开团购买
	 */
	private Boolean openGroupon;

	/**
	 * 店铺ID
	 */
	private Long storeId;

	/**
	 * 秒杀类型
	 */
	private String snapshotType;


	/**
	 * 是否开店礼包
	 */
	private Boolean storeBagsFlag;

	/** 地域编码-多级中间用|分割 */
	private String addressId;

	/**
	 * 自提skuIds
	 */
	private List<String> pickUpSkuIds;

	/**
	 * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 如果商家修改定 则限购数量校验需要减去此商品数据
	 */
	@Schema(description = "是否商家修改订单")
	private Boolean supplierUpdate;

	/**
	 * 如果买家修改地址 则限购数量校验需要减去此商品数据
	 */
	@Schema(description = "是否买家修改地址")
	private Boolean buyerModifyConsignee;

	/**
	 * 是否是周期购订单
	 */
//	@Builder.Default
	private Boolean buyCycleFlag;

}