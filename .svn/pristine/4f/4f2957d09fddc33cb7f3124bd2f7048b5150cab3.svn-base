package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoAddRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>周期购spu表新增参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsAddRequest extends BaseRequest {


	private static final long serialVersionUID = -5378243556604206902L;

	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	@NotBlank
	private String goodsId;

	/**
	 * 配送周期
	 */
	@Schema(description = "配送周期")
	@Min(1)
	@Max(7)
	@NotNull
	private Integer deliveryCycle;

	/**
	 * 自选期数，当deliveryCycle = 6、7 时有值
	 */
	@Schema(description = "自选期数")
	@Min(1)
	@Max(31)
	@CanEmpty
	private Integer optionalNum;

	/**
	 * 用户可选送达日期
	 */
	@Schema(description = "用户可选送达日期")
	@Length(max=256)
	private String deliveryDate;

	/**
	 * 预留时间 日期
	 */
	@Schema(description = "预留时间 日期")
	@Min(1)
	@Max(31)
	@CanEmpty
	private Integer reserveDay;

	/**
	 * 预留时间 时间点
	 */
	@Schema(description = "预留时间 时间点")
	@Min(0)
	@Max(23)
	@CanEmpty
	private Integer reserveTime;

	/**
	 *  周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效", hidden = true)
	@Min(0)
	@Max(1)
	@CanEmpty
	private Integer cycleState;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称", hidden = true)
	private String goodsName;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * sku信息集合
	 */
	@Schema(description = "sku信息集合")
	@NotEmpty
	private List<BuyCycleGoodsInfoAddRequest> buyCycleGoodsInfoAddRequestList;


	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;


	/**
	 * 统一参数校验入口
	 */
	@Schema(description = "统一参数校验入口", hidden = true)
	public void checkParam(){
		if (deliveryCycle != Constants.ONE) {
			List<Integer> deliveryDateList = Arrays.stream(deliveryDate.split(","))
					.filter(NumberUtils::isCreatable)
					.map(Integer::valueOf)
					.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(deliveryDateList)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}

			if (deliveryDateList.size() != new HashSet<>(deliveryDateList).size()) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//如果是每周一期或者是每周固定多期
			//周一至周日，最多可选择7种，并且范围在1-7之间
			if (deliveryCycle == Constants.TWO || deliveryCycle == Constants.FOUR || deliveryCycle == Constants.SIX) {
				if (deliveryDateList.size() > Constants.SEVEN) {
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
				//找出超出限制的日期，如果有1
				long count = deliveryDateList.parallelStream().filter(num -> num < Constants.ONE || num > Constants.SEVEN).count();
				if (count > BigDecimal.ZERO.longValue()) {
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
			} else {
				if (deliveryDateList.size() > Constants.NUM_31) {
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
				//找出超出限制的日期，如果有1
				long count = deliveryDateList.parallelStream().filter(num -> num < Constants.ONE || num > Constants.NUM_31).count();
				if (count > BigDecimal.ZERO.longValue()) {
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
			}

			if(deliveryCycle == Constants.SIX && (Objects.isNull(optionalNum) || optionalNum < Constants.ONE || optionalNum > Constants.SIX)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}

			if(deliveryCycle == Constants.SEVEN && (Objects.isNull(optionalNum) || optionalNum < Constants.ONE || optionalNum > Constants.NUM_31)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		// 如果预留时间 日期不是null，那么预留时间 时间点就不能为null
		if (Objects.nonNull(reserveDay)) {
			if (Objects.isNull(reserveTime)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		} else {
			if (Objects.nonNull(reserveTime)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}

}