package com.wanmi.sbc.goods.api.request.goodsrestrictedsale;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;
import com.wanmi.sbc.goods.bean.enums.PersonRestrictedCycle;
import com.wanmi.sbc.goods.bean.enums.PersonRestrictedType;
import com.wanmi.sbc.goods.bean.enums.RestrictedType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>限售配置新增参数</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSaleAddRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 货品的skuId
	 */
	@Schema(description = "货品的skuId")
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * 限售方式 0: 按订单 1：按会员
	 */
	@Schema(description = "限售方式 0: 按订单 1：按会员")
	
	private RestrictedType restrictedType;

	/**
	 * 是否每人限售标识 
	 */
	@Schema(description = "是否每人限售标识 ")
	
	private DefaultFlag restrictedPrePersonFlag;

	/**
	 * 是否每单限售的标识
	 */
	@Schema(description = "是否每单限售的标识")
	
	private DefaultFlag restrictedPreOrderFlag;

	/**
	 * 是否指定会员限售的标识
	 */
	@Schema(description = "是否指定会员限售的标识")
	
	private DefaultFlag restrictedAssignFlag;

	/**
	 * 个人限售的方式(  0:终生限售  1:周期限售)
	 */
	@Schema(description = "个人限售的方式(  0:终生限售  1:周期限售)")
	
	private PersonRestrictedType personRestrictedType;

	/**
	 * 个人限售的周期 (0:周   1:月  2:年)
	 */
	@Schema(description = "个人限售的周期 (0:周   1:月  2:年)")
	
	private PersonRestrictedCycle personRestrictedCycle;

	/**
	 * 特定会员的限售类型 0: 会员等级  1：指定会员
	 */
	@Schema(description = "特定会员的限售类型 0: 会员等级  1：指定会员")
	private AssignPersonRestrictedType assignPersonRestrictedType;

	/**
	 * 限售数量
	 */
	@Schema(description = "限售数量")
	private Long restrictedNum;

	/**
	 * 起售数量
	 */
	@Schema(description = "起售数量")
	private Long startSaleNum;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;

	/**
	 * 客户会员的IDS
	 */
	@Schema(description = "会员的Id")
	private List<String> customerIds;

	/**
	 * 客户等级IDS
	 */
	@Schema(description = "客户等级IDS")
	private List<Long> customerLevelIds;

	/**
	 * 货品的Id
	 */
	@Schema(description = "货品的Id")
	private List<String> goodsInfoIds;
	/**
	 * 是否打开限售方式开关
	 */
	@Schema(description = "是否打开限售方式开关 ")
	private DefaultFlag restrictedWay;
	/**
	 * 是否打开起售数量开关
	 */
	@Schema(description = "是否打开起售数量开关 ")
	private DefaultFlag restrictedStartNum;

	/**
	 * 地域编码-多级中间用|分割
	 */
	@Schema(description = "地域编码-多级中间用|分割")
	private List<String> addressIds;

	@Override
	public void checkParam() {

		if (CollectionUtils.isEmpty(goodsInfoIds)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		if (Objects.isNull(restrictedType)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		// 按订单
		if (RestrictedType.RESTRICTED_ORDER.equals(restrictedType)){
			if (Objects.isNull(restrictedNum) || restrictedNum < Constants.ONE
				|| restrictedNum >= Constants.NUM_1000){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}else if (RestrictedType.RESTRICTED_HUMAN.equals(restrictedType)){
			// 每人限售标识
			if (DefaultFlag.YES.equals(restrictedPrePersonFlag)){
				if (Objects.isNull(restrictedNum) || restrictedNum < Constants.ONE
						|| restrictedNum >= Constants.NUM_1000){
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
			}

			// 指定会员限售的标识
			if (DefaultFlag.YES.equals(restrictedAssignFlag)){
				// 会员等级
				if (AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL.equals(assignPersonRestrictedType)){
					if (CollectionUtils.isEmpty(customerLevelIds)){
						throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
					}
				}else if (AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER.equals(assignPersonRestrictedType)){
					if (CollectionUtils.isEmpty(customerIds)){
						throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
					}
				}
			}
		}
		//打开起售数量开关
		if (DefaultFlag.YES.equals(restrictedStartNum)){
			if (Objects.isNull(startSaleNum) || startSaleNum < Constants.ONE
					|| startSaleNum >= Constants.NUM_1000){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}
}