package com.wanmi.sbc.marketing.provider.impl.drawrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityQueryRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordListRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordQueryRequest;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordListResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordPageResponse;
import com.wanmi.sbc.marketing.bean.enums.DrawActivityStatus;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import com.wanmi.sbc.marketing.drawactivity.service.DrawActivityService;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.service.DrawPrizeService;
import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import com.wanmi.sbc.marketing.drawrecord.service.DrawRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>抽奖记录表查询服务接口实现</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@RestController
@Validated
public class DrawRecordQueryController implements DrawRecordQueryProvider {
	@Autowired
	private DrawRecordService drawRecordService;

	@Autowired
	private DrawActivityService drawActivityService;

	@Autowired
	private DrawPrizeService drawPrizeService;

	@Override
	public BaseResponse<DrawRecordPageResponse> page(@RequestBody @Valid DrawRecordPageRequest drawRecordPageReq) {
		DrawRecordQueryRequest queryReq = new DrawRecordQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawRecordPageReq, queryReq);
		Page<DrawRecord> drawRecordPage = drawRecordService.page(queryReq);
		Page<DrawRecordVO> newPage = drawRecordPage.map(entity -> drawRecordService.wrapperVo(entity));
		//获取活动名称
		List<Long> activityIds = newPage.getContent().stream()
				.map(DrawRecordVO::getActivityId)
				.distinct()
				.collect(Collectors.toList());
		Map<Long, String> map = drawActivityService
				.list(DrawActivityQueryRequest.builder().idList(activityIds).queryTab(DrawActivityStatus.ALL).build())
				.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(DrawActivity::getId, DrawActivity::getActivityName));
		newPage.getContent().forEach(v->v.setActivityName(map.get(v.getActivityId())));
		MicroServicePage<DrawRecordVO> microPage = new MicroServicePage<>(newPage, drawRecordPageReq.getPageable());
		DrawRecordPageResponse finalRes = new DrawRecordPageResponse();
		finalRes.setDrawRecordVOPage(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<DrawRecordListResponse> list(@RequestBody @Valid DrawRecordListRequest drawRecordListReq) {
		DrawRecordQueryRequest queryReq = new DrawRecordQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawRecordListReq, queryReq);
		List<DrawRecord> drawRecordList = drawRecordService.list(queryReq);
		List<DrawRecordVO> newList = drawRecordList.stream().map(entity -> drawRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new DrawRecordListResponse(newList));
	}

	@Override
	public BaseResponse<DrawRecordByIdResponse> getById(@RequestBody @Valid DrawRecordByIdRequest drawRecordByIdRequest) {
		DrawRecord drawRecord = drawRecordService.getById(drawRecordByIdRequest.getId());
		DrawRecordVO drawRecordVO = drawRecordService.wrapperVo(drawRecord);
		//自定义奖品已兑奖直接展示奖品内容
		if (Objects.equals(DrawPrizeType.CUSTOMIZE, drawRecord.getPrizeType())
				&& Objects.equals(Constants.yes, drawRecord.getDrawStatus())
				&& Objects.equals(Constants.yes, drawRecord.getRedeemPrizeStatus())
				&& Objects.equals(Constants.yes, drawRecord.getDeliverStatus())) {
			DrawPrize drawPrize = drawPrizeService.getById(drawRecord.getPrizeId());
			if (Objects.equals(DrawPrizeType.CUSTOMIZE, drawPrize.getPrizeType())){
				drawRecordVO.setCustomize(drawPrize.getCustomize());
			}
		}
		return BaseResponse.success(new DrawRecordByIdResponse(drawRecordVO));
	}

	@Override
	public BaseResponse<Long> total(DrawRecordPageRequest request) {
		DrawRecordQueryRequest queryReq = new DrawRecordQueryRequest();
		KsBeanUtil.copyPropertiesThird(request, queryReq);
		return BaseResponse.success(drawRecordService.total(queryReq));
	}

}

