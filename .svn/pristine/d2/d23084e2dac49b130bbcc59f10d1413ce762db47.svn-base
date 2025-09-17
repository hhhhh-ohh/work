package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.FlashPromotionActivityVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>抢购商品表新增参数</p>
 * @author xufeng
 * @date 2022-02-10 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsBatchModifyRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动Id
	 */
	@Schema(description = "activityId")
	@NotNull
	private String activityId;

	/**
	 * 活动名称
	 */
	@Schema(description = "activityName")
	private String activityName;

	/**
	 * 活动开始时间
	 */
	@Schema(description = "startTimeStr")
	private String startTimeStr;

	/**
	 * 活动结束时间
	 */
	@Schema(description = "endTimeStr")
	private String endTimeStr;


	/**
	 * 预热时间
	 */
	@Schema(description = "预热时间")
	private Integer preTime;

	/**
	 * 状态 0:开始 1:暂停
	 */
	@Schema(description = "status")
	private Integer status;

	/**
	 * 类型 1:限时购 0:秒杀
	 */
	@Schema(description = "类型 1:限时购 0:秒杀")
	private Integer type;

	private FlashPromotionActivityVO flashPromotionActivityVO;

	@NotEmpty
	private List<FlashSaleGoodsVO> flashSaleGoodsVOList;

	@Override
	public void checkParam() {
		List<String> skuIds = flashSaleGoodsVOList.stream()
						.map(FlashSaleGoodsVO::getGoodsInfoId)
						.filter(StringUtils::isNotBlank)
						.collect(Collectors.toList());
		if (skuIds.size() > Constants.MARKETING_GOODS_SIZE_MAX) {
			throw new SbcRuntimeException(GoodsErrorCodeEnum.K030113,
							new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
		}
	}
}