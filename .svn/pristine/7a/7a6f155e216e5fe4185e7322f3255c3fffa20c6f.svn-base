package com.wanmi.sbc.marketing.provider.impl.drawrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordSaveProvider;
import com.wanmi.sbc.marketing.api.request.drawrecord.*;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordAddResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordModifyResponse;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.repository.DrawPrizeRepository;
import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import com.wanmi.sbc.marketing.drawrecord.service.DrawRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>抽奖记录表保存服务接口实现</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@RestController
@Validated
public class DrawRecordSaveController implements DrawRecordSaveProvider {
	@Autowired
	private DrawRecordService drawRecordService;

	@Autowired
	private DrawPrizeRepository drawPrizeRepository;

	@Override
	public BaseResponse<DrawRecordAddResponse> add(@RequestBody @Valid DrawRecordAddRequest drawRecordAddRequest) {
		DrawRecord drawRecord = new DrawRecord();
		KsBeanUtil.copyPropertiesThird(drawRecordAddRequest, drawRecord);
		return BaseResponse.success(new DrawRecordAddResponse(
				drawRecordService.wrapperVo(drawRecordService.add(drawRecord))));
	}

	@Override
	public BaseResponse<DrawRecordModifyResponse> modify(@RequestBody @Valid DrawRecordModifyRequest drawRecordModifyRequest) {
		DrawRecord drawRecord = new DrawRecord();
		KsBeanUtil.copyPropertiesThird(drawRecordModifyRequest, drawRecord);
		return BaseResponse.success(new DrawRecordModifyResponse(
				drawRecordService.wrapperVo(drawRecordService.modify(drawRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid DrawRecordDelByIdRequest drawRecordDelByIdRequest) {
		drawRecordService.deleteById(drawRecordDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid DrawRecordDelByIdListRequest drawRecordDelByIdListRequest) {
		drawRecordService.deleteByIdList(drawRecordDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 中奖发货信息导入
	 * @param modifyRequests
	 * @return
	 */
	@Override
	public BaseResponse modifyImportPeizeDelivery(@RequestBody @Valid DrawRecordModifyListRequest modifyRequests) {
		drawRecordService.modifyImportPeizeDelivery(modifyRequests);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 修改领取奖品状态
	 * @param drawRecordRedeemPrizeRequest
	 * @return
	 */
	@Override
	public BaseResponse<DrawRecordModifyResponse> modifyRedeemPrizeStatus(@RequestBody @Valid DrawRecordRedeemPrizeRequest drawRecordRedeemPrizeRequest) {
		// check是否中奖 并且未兑奖
		DrawRecord drawRecord = drawRecordService.getById(drawRecordRedeemPrizeRequest.getId());
		if (Objects.isNull(drawRecord)){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080078);
		}

		if (NumberUtils.INTEGER_ZERO.equals(drawRecord.getDrawStatus())){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080080);
		}

		if (NumberUtils.INTEGER_ONE.equals(drawRecord.getRedeemPrizeStatus())){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080076);
		}
		if (!Objects.equals(drawRecord.getCustomerId(),drawRecordRedeemPrizeRequest.getCustomerId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}

		drawRecord.setRedeemPrizeStatus(drawRecordRedeemPrizeRequest.getRedeemPrizeStatus());
		drawRecord.setRedeemPrizeTime(drawRecordRedeemPrizeRequest.getRedeemPrizeTime());
		drawRecord.setUpdateTime(LocalDateTime.now());

		if (Objects.equals(DrawPrizeType.CUSTOMIZE,drawRecord.getPrizeType())){
			drawRecord.setDeliverStatus(Constants.yes);
			drawRecord.setDeliveryTime(LocalDateTime.now());
		}else if (Objects.equals(DrawPrizeType.GOODS,drawRecord.getPrizeType())){
			if (StringUtils.isBlank(drawRecordRedeemPrizeRequest.getConsigneeName())
					|| StringUtils.isBlank(drawRecordRedeemPrizeRequest.getConsigneeNumber())
					|| StringUtils.isBlank(drawRecordRedeemPrizeRequest.getDetailAddress())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			drawRecord.setConsigneeName(drawRecordRedeemPrizeRequest.getConsigneeName());
			drawRecord.setConsigneeNumber(drawRecordRedeemPrizeRequest.getConsigneeNumber());
			drawRecord.setDetailAddress(drawRecordRedeemPrizeRequest.getDetailAddress());
		}

		DrawRecordModifyResponse drawRecordModifyResponse = new DrawRecordModifyResponse(
				drawRecordService.wrapperVo(drawRecordService.modify(drawRecord)));

		if (Objects.equals(DrawPrizeType.CUSTOMIZE,drawRecord.getPrizeType())){
			DrawPrize drawPrize = drawPrizeRepository
					.findById(drawRecord.getPrizeId()).orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080075));
			drawRecordModifyResponse.getDrawRecordVO().setCustomize(drawPrize.getCustomize());
		}

		return BaseResponse.success(drawRecordModifyResponse);
	}

	/**
	 * 添加发货信息
	 * @param drawRecordAddLogisticrequest
	 * @return
	 */
	@Override
	public BaseResponse<DrawRecordModifyResponse> addLogisticByDrawRecordId(@RequestBody @Valid DrawRecordAddLogisticRequest drawRecordAddLogisticrequest) {

		DrawRecord drawRecord = drawRecordService.getById(drawRecordAddLogisticrequest.getId());
		if (Objects.isNull(drawRecord)){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080078);
		}

		if (NumberUtils.INTEGER_ZERO.equals(drawRecord.getRedeemPrizeStatus())){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080077);
		}

		if (NumberUtils.INTEGER_ONE.equals(drawRecord.getDeliverStatus())){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080079);
		}
		// 物流单号正则匹配
		String regex = "^[a-zA-Z0-9]{1,50}$";
		if (!drawRecordAddLogisticrequest.getLogisticsNo().matches(regex)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009,"物流单号必须为1-50位数字或字母");
		}

		drawRecord.setLogisticsCode(drawRecordAddLogisticrequest.getLogisticsCode());
		drawRecord.setLogisticsCompany(drawRecordAddLogisticrequest.getLogisticsCompany());
		drawRecord.setLogisticsNo(drawRecordAddLogisticrequest.getLogisticsNo());
		drawRecord.setDeliveryTime(DateUtil.parseDay(drawRecordAddLogisticrequest.getDeliveryTime()));
		drawRecord.setDeliverStatus(NumberUtils.INTEGER_ONE);
		drawRecord.setUpdatePerson(drawRecordAddLogisticrequest.getUpdatePerson());
		drawRecord.setUpdateTime(drawRecordAddLogisticrequest.getUpdateTime());
		return BaseResponse.success(new DrawRecordModifyResponse(
				drawRecordService.wrapperVo(drawRecordService.modify(drawRecord))));
	}
}

