package com.wanmi.sbc.goods.provider.impl.flashpromotionactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.flashpromotionactivity.FlashPromotionActivityQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashPromotionActivityQueryRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityModifyStatusRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityPageRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashPromotionActivityPageResponse;
import com.wanmi.sbc.goods.bean.enums.FlashSaleGoodsStatus;
import com.wanmi.sbc.goods.bean.vo.FlashPromotionActivityVO;
import com.wanmi.sbc.goods.flashpromotionactivity.model.root.FlashPromotionActivity;
import com.wanmi.sbc.goods.flashpromotionactivity.service.FlashPromotionActivityService;
import com.wanmi.sbc.goods.flashsalegoods.service.FlashSaleGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>抢购活动查询服务接口实现</p>
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@RestController
@Validated
public class FlashPromotionActivityQueryController implements FlashPromotionActivityQueryProvider {

	@Autowired
	private FlashPromotionActivityService flashPromotionActivityService;

	@Autowired
	private FlashSaleGoodsService flashSaleGoodsService;

	@Override
	public BaseResponse<FlashPromotionActivityPageResponse> page(@RequestBody @Valid FlashPromotionActivityPageRequest
																		flashPromotionActivityPageRequest) {
		FlashPromotionActivityQueryRequest queryRequest = new FlashPromotionActivityQueryRequest();
		KsBeanUtil.copyPropertiesThird(flashPromotionActivityPageRequest, queryRequest);
		Page<FlashPromotionActivity> pages = flashPromotionActivityService.page(queryRequest);
		List<FlashPromotionActivityVO> voList = wraperVos(pages.getContent());
		FlashPromotionActivityPageResponse response = FlashPromotionActivityPageResponse.builder()
				.flashPromotionActivityVOS(new MicroServicePage<>(voList,
						flashPromotionActivityPageRequest.getPageable(),	pages.getTotalElements()))
				.build();
		return BaseResponse.success(response);
	}

	private List<FlashPromotionActivityVO> wraperVos(List<FlashPromotionActivity> promotionActivities) {
		if (CollectionUtils.isNotEmpty(promotionActivities)) {
			return promotionActivities.stream().map(info -> {
				FlashPromotionActivityVO vo = new FlashPromotionActivityVO();
				KsBeanUtil.copyPropertiesThird(info, vo);
				FlashSaleGoodsStatus flashSaleGoodsStatus = this.getFlashPromotionGoodsStatus(info);
				vo.setFlashSaleGoodsStatus(flashSaleGoodsStatus);
				vo.setGoodsNum(flashSaleGoodsService.findCountByActivityId(vo.getActivityId()));
				return vo;
			}).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public BaseResponse modifyByActivityId(@RequestBody @Valid FlashPromotionActivityModifyStatusRequest flashPromotionActivityModifyStatusRequest) {
		flashPromotionActivityService.modifyByActivityId(flashPromotionActivityModifyStatusRequest);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 获取秒杀商品活动状态
	 *
	 * @param flashPromotionActivity
	 * @return
	 */
	public FlashSaleGoodsStatus getFlashPromotionGoodsStatus(FlashPromotionActivity flashPromotionActivity) {
		// 暂停中
        if (Objects.nonNull(flashPromotionActivity.getStatus()) && Constants.ONE==flashPromotionActivity.getStatus()){
        	// 已结束的优先返回
			if (LocalDateTime.now().isAfter(flashPromotionActivity.getEndTime())) {
				return FlashSaleGoodsStatus.ENDED;
			}
			return FlashSaleGoodsStatus.PAUSED;
        }
        if (LocalDateTime.now().isBefore(flashPromotionActivity.getStartTime())) {
            return FlashSaleGoodsStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(flashPromotionActivity.getEndTime())) {
            return FlashSaleGoodsStatus.ENDED;
        } else {
            return FlashSaleGoodsStatus.STARTED;
        }
	}

}

