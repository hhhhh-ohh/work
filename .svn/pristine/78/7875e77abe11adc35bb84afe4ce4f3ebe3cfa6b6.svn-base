package com.wanmi.sbc.marketing.provider.impl.drawactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.*;
import com.wanmi.sbc.marketing.api.response.drawactivity.*;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;
import com.wanmi.sbc.marketing.bean.vo.DrawDetail;
import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import com.wanmi.sbc.marketing.drawactivity.service.DrawActivityService;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.service.DrawPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>抽奖活动表查询服务接口实现</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@RestController
@Validated
public class DrawActivityQueryController implements DrawActivityQueryProvider {
	@Autowired
	private DrawActivityService drawActivityService;

	@Autowired
	private DrawPrizeService drawPrizeService;

	@Autowired
	private CouponInfoService couponInfoService;

	@Override
	public BaseResponse<DrawActivityPageResponse> page(@RequestBody @Valid DrawActivityPageRequest drawActivityPageReq) {
		DrawActivityQueryRequest queryReq = new DrawActivityQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawActivityPageReq, queryReq);
		Page<DrawActivity> drawActivityPage = drawActivityService.page(queryReq);
		Page<DrawActivityVO> newPage = drawActivityPage.map(entity -> drawActivityService.wrapperVo(entity));
		MicroServicePage<DrawActivityVO> microPage = new MicroServicePage<>(newPage, drawActivityPageReq.getPageable());
		DrawActivityPageResponse finalRes = new DrawActivityPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}


	@Override
	public BaseResponse<DrawDetailByIdResponse> detailForWeb(@Valid DrawDetailByIdRequest detailByIdRequest) {
		DrawDetailByIdResponse response = new DrawDetailByIdResponse();
		DrawDetail drawDetail = drawActivityService.detailForWeb(detailByIdRequest.getActivityId(),
				detailByIdRequest.getCustomerId());
		response.setDrawDetail(drawDetail);
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<DrawActivityListResponse> list(@RequestBody @Valid DrawActivityListRequest drawActivityListReq) {
		DrawActivityQueryRequest queryReq = new DrawActivityQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawActivityListReq, queryReq);
		List<DrawActivity> drawActivityList = drawActivityService.list(queryReq);
		List<DrawActivityVO> newList = drawActivityList.stream().map(entity -> drawActivityService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new DrawActivityListResponse(newList));
	}

	@Override
	public BaseResponse<DrawActivityByIdResponse> getById(
			@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest) {
		DrawActivity drawActivity = drawActivityService.getById(drawActivityByIdRequest.getId());
		return BaseResponse.success(new DrawActivityByIdResponse(drawActivityService.wrapperVo(drawActivity)));
	}

	@Override
	public BaseResponse<DrawActivityGetDetailsByIdResponse> getDetailsById(
			@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest) {
		DrawActivity drawActivity = drawActivityService.getById(drawActivityByIdRequest.getId());
		List<DrawPrize> drawPrizeList = drawPrizeService
				.findAllByActivityIdAndDelFlag(drawActivityByIdRequest.getId(), DeleteFlag.NO);
		List<DrawPrizeVO> drawPrizeVOList = drawPrizeList.stream()
				.map(drawPrize -> drawPrizeService.wrapperVo(drawPrize)).collect(Collectors.toList());
		getCouponInfo(drawPrizeVOList);
		DrawActivityVO drawActivityVO = drawActivityService.wrapperVo(drawActivity);
		return BaseResponse.success(DrawActivityGetDetailsByIdResponse.builder().drawActivity(drawActivityVO)
				.drawPrizeList(drawPrizeVOList).build());
	}

	@Override
	public BaseResponse<DrawActivityForUpdateResponse> getByIdForUpdate(
			@RequestBody @Valid DrawActivityForUpdateRequest drawActivityForUpdateRequest) {
		DrawActivityForUpdateResponse response = DrawActivityForUpdateResponse.builder().build();
		//查活动主表
		DrawActivity drawActivity = drawActivityService.getById(drawActivityForUpdateRequest.getId());
		//根据活动id，查活动相关奖品
		List<DrawPrize> drawPrizeList = drawPrizeService
				.findAllByActivityIdAndDelFlag(drawActivityForUpdateRequest.getId(), DeleteFlag.NO);
		//奖品转成VO
		List<DrawPrizeVO> drawPrizeVOList = drawPrizeList.stream()
				.map(drawPrize -> drawPrizeService.wrapperVo(drawPrize)).collect(Collectors.toList());
		//获取优惠券信息
		getCouponInfo(drawPrizeVOList);
		KsBeanUtil.copyPropertiesThird(drawActivity, response);
		response.setPrizeList(drawPrizeVOList);
		return BaseResponse.success(response);
	}

	private void getCouponInfo(List<DrawPrizeVO> drawPrizeVOList) {
		List<String> couponIds = drawPrizeVOList.stream()
				.filter(v -> Objects.equals(DrawPrizeType.COUPON, v.getPrizeType()))
				.map(DrawPrizeVO::getCouponCodeId)
				.collect(Collectors.toList());
		List<CouponInfo> couponInfos = couponInfoService.queryByIds(couponIds);
		Map<String, CouponInfoVO> couponInfoMap = Objects.requireNonNull(KsBeanUtil.convert(couponInfos, CouponInfoVO.class))
				.stream()
				.collect(Collectors.toMap(CouponInfoVO::getCouponId, Function.identity()));
		drawPrizeVOList.forEach(v->{
			if (Objects.equals(DrawPrizeType.COUPON, v.getPrizeType())){
				v.setCouponInfoVO(couponInfoMap.get(v.getCouponCodeId()));
			}
		});
	}

}

