package com.wanmi.sbc.marketing.api.request.drawactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.dto.DrawPrizeDTO;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>抽奖活动表修改参数</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 抽奖形式（0：九宫格，1：大转盘）
	 */
	@Schema(description = "抽奖形式（0：九宫格，1：大转盘）")
	private DrawFromType formType;

	/**
	 * 抽奖类型（0：无限制，1：积分）
	 */
	@Schema(description = "抽奖类型（0：无限制，1：积分）")
	private DrawType drawType;

	/**
	 * 消耗积分
	 */
	@Schema(description = "消耗积分 当drawType为1时有值")
	private Long consumePoints;

	/**
	 * 抽奖次数限制类型（0：每日，1：每人）
	 */
	@Schema(description = "抽奖次数限制类型（0：每日，1：每人）")
	private DrawTimesType drawTimesType;

	/**
	 * 抽奖次数，默认为0
	 */
	@Schema(description = "抽奖次数，默认为0")
	private Integer drawTimes;

	/**
	 * 中奖次数限制类型 （0：无限制，1：每人每天）
	 */
	@Schema(description = "中奖次数限制类型 （0：无限制，1：每人每天）")
	private DrawWinTimesType winTimesType;

	/**
	 * 每人每天最多中奖次数，默认为0
	 */
	@Schema(description = "每人每天最多中奖次数，默认为0")
	private Integer winTimes;

	/**
	 * 客户等级
	 */
	@Schema(description = "客户等级")
	private String joinLevel;

	/**
	 * 未中奖提示
	 */
	@Schema(description = "未中奖提示")
	private String notAwardTip;

	/**
	 * 抽奖次数上限提示
	 */
	@Schema(description = "抽奖次数上限提示")
	private String maxAwardTip;

	/**
	 * 活动规则说明
	 */
	@Schema(description = "活动规则说明")
	private String activityContent;

	/**
	 * 奖品集合
	 */
	@Schema(description = "奖品集合")
	private List<DrawPrizeDTO> prizeDTOList;

	@Override
	public void checkParam() {
		//抽奖活动名称不为空且不超过40个字符
		if (StringUtils.isBlank(activityName) || activityName.length()> Constants.NUM_40){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//开始结束时间不可为空
		if(Objects.isNull(startTime) || Objects.isNull(endTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//开始时间不早于当前时间
		if (startTime.isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//结束时间不早于开始时间
		if (endTime.isBefore(startTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//抽奖形式不可为空
		if (Objects.isNull(formType)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//抽奖类型不可为空
		if (Objects.isNull(drawType)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//当抽奖类型为积分抽奖时，积分不可为空且为1-9999的整数
		if (Objects.equals(DrawType.POINTS,drawType)){
			if (Objects.isNull(consumePoints) || consumePoints < Constants.ONE || consumePoints > Constants.NUM_9999L){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		//抽奖次数限制类型不可为空
		if (Objects.isNull(drawTimesType)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//抽奖次数不可为空且为1-999的整数
		if (Objects.isNull(drawTimes) || drawTimes < Constants.ONE || drawTimes > Constants.NUM_999){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//中奖次数限制类型不可为空
		if (Objects.isNull(winTimesType)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//中奖次数为每人每天或每人每次时，中奖次数不可为空且为1-999的整数
		if (Objects.equals(DrawWinTimesType.PER_PERSON_PER_DAY,winTimesType)
				|| Objects.equals(DrawWinTimesType.PER_PERSON_PER_FREQUENCY,winTimesType)){
			if (Objects.isNull(winTimes) || winTimes < Constants.ONE || winTimes > Constants.NUM_999){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		//未中奖提示不为空且不超过40个字符
		if (StringUtils.isBlank(notAwardTip) || notAwardTip.length()>Constants.NUM_40){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//抽奖次数上限提示不为空且不超过40个字符
		if (StringUtils.isBlank(maxAwardTip) || maxAwardTip.length()>Constants.NUM_40){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//客户等级不为空
		if (StringUtils.isBlank(joinLevel)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		if (StringUtils.isNotBlank(activityContent) && activityContent.length() > 50000){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//校验奖品
		checkPrizeList(prizeDTOList);
	}

	private void checkPrizeList(List<DrawPrizeDTO> prizeDTOList) {
		prizeDTOList.forEach(prizeDTO -> {
			//抽奖活动名称不为空且不超过40个字符
			if (StringUtils.isBlank(prizeDTO.getPrizeName()) || prizeDTO.getPrizeName().length()>Constants.EIGHT) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(prizeDTO.getPrizeName());
			//抽奖活动名称是否包含特殊字符
			if (m.find()){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//奖品图片不可为空
			if (StringUtils.isBlank(prizeDTO.getPrizeUrl())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//奖品类型不可为空
			if (Objects.isNull(prizeDTO.getPrizeType())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//商品总量不可为空且为1-99999999的整数
			if (Objects.isNull(prizeDTO.getPrizeNum())
					|| prizeDTO.getPrizeNum() < Constants.ONE
					|| prizeDTO.getPrizeNum() > Constants.NUM_99999999){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//中奖概率不可为空且为大于0.01
			if (Objects.isNull(prizeDTO.getWinPercent())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (prizeDTO.getWinPercent().compareTo(new BigDecimal(Constants.DRAW_MIN_WIN_PERCENT)) == Constants.MINUS_ONE){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//当奖品类型为优惠券时，优惠券Id不可为空
			if (Objects.equals(DrawPrizeType.COUPON,prizeDTO.getPrizeType()) && StringUtils.isBlank(prizeDTO.getCouponCodeId())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//当奖品类型为优惠券时，积分数值不可为空
			if (Objects.equals(DrawPrizeType.POINTS,prizeDTO.getPrizeType()) && Objects.isNull(prizeDTO.getPointsNum())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//当奖品类型为自定义，自定义奖品不可为空
			if (Objects.equals(DrawPrizeType.CUSTOMIZE,prizeDTO.getPrizeType()) && StringUtils.isBlank(prizeDTO.getCustomize())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		});
	}

}