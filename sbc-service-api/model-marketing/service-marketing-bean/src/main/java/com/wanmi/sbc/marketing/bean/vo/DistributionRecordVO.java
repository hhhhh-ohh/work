package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.marketing.bean.enums.CommissionReceived;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>DistributionRecordVO</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Schema
public class DistributionRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 分销记录表主键
	 */
	@Schema(description = "分销记录表主键")
	private String recordId;

	/**
	 * 货品Id
	 */
	@Schema(description = "货品Id")
	private String goodsInfoId;

	/**
	 * 订单交易号
	 */
	@Schema(description = "订单交易号")
	private String tradeId;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	private Long storeId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 分销员标识UUID
	 */
	@Schema(description = "分销员标识UUID")
	private String distributorId;

	/**
	 * 付款时间
	 */
	@Schema(description = "付款时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime payTime;

	/**
	 * 订单完成时间
	 */
	@Schema(description = "订单完成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTime;

	/**
	 * 佣金入账时间
	 */
	@Schema(description = "佣金入账时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime missionReceivedTime;

	/**
	 * 订单商品总金额
	 */
	@Schema(description = "订单商品总金额")
	private BigDecimal orderGoodsPrice;

	/**
	 * 商品的数量
	 */
	@Schema(description = "商品的数量")
	private Long orderGoodsCount;

	/**
	 * 货品的总佣金
	 */
	@Schema(description = "货品的总佣金")
	private BigDecimal commissionGoods;

	/**
	 * 佣金比例
	 */
	@Schema(description = "佣金比例")
	private BigDecimal commissionRate;

	/**
	 * 佣金是否入账
	 */
	@Schema(description = "佣金是否入账")
	private CommissionReceived commissionState;

	/**
	 * 规格值信息
	 */
	@Schema(description = "规格值信息")
	private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOS;

	/**
	 * 分销记录使用的货品信息
	 */
	@Schema(description = "分销记录使用的货品信息")
	private GoodsInfoForDistribution goodsInfo;

	/**
	 * 会员信息
	 */
	@Schema(description = "会员信息")
	private CustomerDetailVO customerDetailVO;

	/**
	 * 分销员信息
	 */
	@Schema(description = "分销员信息")
	private DistributionCustomerVO distributionCustomerVO;

	/**
	 * 店铺信息
	 */
	@Schema(description = "店铺信息")
	private StoreVO storeVO;

	/**
	 * 商家信息
	 */
	@Schema(description = "商家信息")
	private CompanyInfoVO companyInfoVO;

	@Schema(description = "分销员的客户id")
	private String distributorCustomerId;

	/**
	 * 是否删除 0：未删除  1：已删除
	 */
	@Schema(description = "是否删除 0：未删除  1：已删除")
	private DeleteFlag deleteFlag;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus distributorLogOutStatus;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus customerLogOutStatus;
}