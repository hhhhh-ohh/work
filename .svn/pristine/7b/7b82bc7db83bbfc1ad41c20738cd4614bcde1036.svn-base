package com.wanmi.sbc.customer.api.request.payingmemberlevel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelModifyRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>付费会员等级表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberLevelModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	@Max(9999999999L)
	@NotNull
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	@Length(max=55)
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Schema(description = "付费会员等级昵称")
	@Length(min = 2,max = 10)
	@NotBlank
	private String levelNickName;

	/**
	 * 付费会员等级状态 0.开启 1.暂停
	 */
	@Schema(description = "付费会员等级状态 0.开启 1.暂停")
	private Integer levelState;

	/**
	 * 付费会员等级背景类型：0.背景色 1.背景图
	 */
	@Schema(description = "付费会员等级背景类型：0.背景色 1.背景图")
	@NotNull
	private Integer levelBackGroundType;

	/**
	 * 付费会员等级背景详情
	 */
	@Schema(description = "付费会员等级背景详情")
	@Length(max=128)
	@NotBlank
	private String levelBackGroundDetail;

	/**
	 * 付费会员等级字体颜色
	 */
	@Schema(description = "付费会员等级字体颜色")
	@Length(max=55)
	@NotBlank
	private String levelFontColor;

	/**
	 * 付费会员等级商家范围：0.自营商家 1.自定义选择
	 */
	@Schema(description = "付费会员等级商家范围：0.自营商家 1.自定义选择")
	@NotNull
	@Min(0)
	@Max(1)
	private Integer levelStoreRange;

	/**
	 * 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
	 */
	@Schema(description = "付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置")
	@NotNull
	@Min(0)
	@Max(1)
	private Integer levelDiscountType;

	/**
	 * 付费会员等级所有商品统一设置 折扣
	 */
	@Schema(description = "付费会员等级所有商品统一设置 折扣")
	private BigDecimal levelAllDiscount;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;


	/**
	 * 付费设置
	 */
	@Schema(description = "付费设置")
	private List<PayingMemberPriceModifyRequest> payingMemberPriceModifyRequests;

	/**
	 * 商家与付费会员等级关联
	 */
	@Schema(description = "商家与付费会员等级关联")
	private List<PayingMemberStoreRelModifyRequest> payingMemberStoreRelModifyRequests;


	/**
	 * 折扣商品与付费会员等级关联
	 */
	@Schema(description = "折扣商品与付费会员等级关联")
	@Valid
	private List<PayingMemberDiscountRelModifyRequest> payingMemberDiscountRelModifyRequests;


	/**
	 * 推荐商品与付费会员等级关联
	 */
	@Schema(description = "推荐商品与付费会员等级关联")
	private List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests;


	@Override
	public void checkParam() {
		//付费设置校验
		if (CollectionUtils.isEmpty(payingMemberPriceModifyRequests)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		} else {
			if (payingMemberPriceModifyRequests.size()>5) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		//如果付费会员等级商家范围是自定义选择，则商家与付费会员等级关联不能为空
		if (this.levelStoreRange == Constants.ONE && CollectionUtils.isEmpty(payingMemberStoreRelModifyRequests)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//如果付费会员等级折扣类型是所有商品统一设置，则 付费会员等级所有商品统一设置 折扣不能为空
		if (this.levelDiscountType == Constants.ZERO && Objects.isNull(this.getLevelAllDiscount())) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (levelDiscountType.equals(NumberUtils.INTEGER_ZERO)) {
			if(!(levelAllDiscount.toString().matches("^[+]?([0-9]+(.[0-9]{1,2})?)$"))){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			} else if (levelAllDiscount.compareTo(BigDecimal.ZERO) < 0
					|| levelAllDiscount.compareTo(new BigDecimal("10.00")) > 0){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		//如果付费会员等级折扣类型是自定义选择，则折扣商品与付费会员等级关联不能为空
		if (this.levelDiscountType == Constants.ONE && CollectionUtils.isEmpty(payingMemberDiscountRelModifyRequests)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//如果付费会员等级折扣类型是自定义选择, 上限1000
		if (this.levelDiscountType == Constants.ONE && CollectionUtils.isNotEmpty(payingMemberDiscountRelModifyRequests)
				&& payingMemberDiscountRelModifyRequests.size() > 1000 ) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//推荐商品与付费会员等级关联 不能为空
		if (CollectionUtils.isEmpty(payingMemberRecommendRelModifyRequests)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		} else {
			// 推荐商品上限50
			if (payingMemberRecommendRelModifyRequests.size() > 50) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}
}
