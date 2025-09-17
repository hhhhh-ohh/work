package com.wanmi.sbc.marketing.provider.impl.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicImportRecordVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicSendRecordVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicImportRecord;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicSendRecord;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCardService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCouponService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicImportRecordService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>电子卡密表查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@RestController
@Validated
public class ElectronicCardQueryController implements ElectronicCardQueryProvider {
	@Autowired
	private ElectronicCardService electronicCardService;

	@Autowired
	private ElectronicImportRecordService electronicImportRecordService;

	@Autowired
	private ElectronicSendRecordService electronicSendRecordService;

	@Autowired
	private ElectronicCouponService electronicCouponService;

	@Override
	public BaseResponse<ElectronicImportRecordPageResponse> getImportRecordPage(@RequestBody @Valid ElectronicImportRecordPageRequest electronicImportRecordPageReq) {
		ElectronicImportRecordQueryRequest queryReq = KsBeanUtil.convert(electronicImportRecordPageReq, ElectronicImportRecordQueryRequest.class);
		Page<ElectronicImportRecord> electronicImportRecordPage = electronicImportRecordService.page(queryReq);
		Page<ElectronicImportRecordVO> newPage = electronicImportRecordPage.map(entity -> electronicImportRecordService.wrapperVo(entity));
		MicroServicePage<ElectronicImportRecordVO> microPage = new MicroServicePage<>(newPage, electronicImportRecordPageReq.getPageable());
		ElectronicImportRecordPageResponse finalRes = new ElectronicImportRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<ElectronicImportCheckResponse> getExistsData(@RequestBody @Valid ElectronicImportCheckRequest request) {
		ElectronicImportCheckResponse response = electronicCardService
				.getExistsData(request.getNumbers(), request.getPasswords(), request.getCodes(), request.getCouponId());
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<ElectronicCardPageResponse> page(@RequestBody @Valid ElectronicCardPageRequest electronicCardPageReq) {
		ElectronicCardQueryRequest queryReq = KsBeanUtil.convert(electronicCardPageReq, ElectronicCardQueryRequest.class);
		Page<ElectronicCard> electronicCardPage = electronicCardService.page(queryReq);
		Page<ElectronicCardVO> newPage = electronicCardPage.map(entity -> electronicCardService.wrapperVo(entity));
		//设置批次时间
		List<String> recordIds = newPage.stream().map(ElectronicCardVO::getRecordId).distinct().collect(Collectors.toList());
		List<ElectronicImportRecord> records = electronicImportRecordService
				.page(ElectronicImportRecordQueryRequest.builder().idList(recordIds).build()).getContent();
		Map<String, ElectronicImportRecord> recordMap = records.stream()
				.collect(Collectors.toMap(ElectronicImportRecord::getId, Function.identity()));
		newPage.forEach(card -> {
			ElectronicImportRecord electronicImportRecord = recordMap.get(card.getRecordId());
			if (electronicImportRecord != null) {
				card.setImportTime(electronicImportRecord.getCreateTime());
			}
			if (electronicCardPageReq.getEncrypt()) {
				card.setCardPassword(electronicCardService.encryptData(card.getCardPassword()));
				card.setCardPromoCode(electronicCardService.encryptData(card.getCardPromoCode()));
			}
		});
		MicroServicePage<ElectronicCardVO> microPage = new MicroServicePage<>(newPage, electronicCardPageReq.getPageable());
		ElectronicCardPageResponse finalRes = new ElectronicCardPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<Long> countForExport(@RequestBody @Valid ElectronicCardExportRequest request) {
		ElectronicCardQueryRequest queryReq = KsBeanUtil.convert(request, ElectronicCardQueryRequest.class);
		queryReq.setDelFlag(DeleteFlag.NO);
		Long total = electronicCardService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<ElectronicSendRecordPageResponse> sendRecordPage(@RequestBody @Valid ElectronicSendRecordPageRequest electronicSendRecordPageReq) {
		ElectronicSendRecordQueryRequest queryReq = KsBeanUtil.convert(electronicSendRecordPageReq, ElectronicSendRecordQueryRequest.class);
		Page<ElectronicSendRecord> electronicSendRecordPage = electronicSendRecordService.page(queryReq);
		Page<ElectronicSendRecordVO> newPage = electronicSendRecordPage.map(entity -> electronicSendRecordService.wrapperVo(entity));
		MicroServicePage<ElectronicSendRecordVO> microPage = new MicroServicePage<>(newPage, electronicSendRecordPageReq.getPageable());
		ElectronicSendRecordPageResponse finalRes = new ElectronicSendRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}


	/**
	 * 根据卡券id批量修改状态未已失效
	 * @param electronicCardInvalidRequest 批量失效参数结构 {@link ElectronicCardInvalidRequest}
	 * @return
	 */
	@Override
	public BaseResponse<ElectronicCardModifyCountResponse> countCardInvalidByCouponId(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest) {
		return BaseResponse.success(ElectronicCardModifyCountResponse.builder()
				.count(electronicCardService.countCardInvalidByCouponId(electronicCardInvalidRequest.getCouponId(),electronicCardInvalidRequest.getTime()))
				.build());
	}

	/**
	 * 根据卡券id统计有效卡密API
	 * @param electronicCardInvalidRequest 批量失效参数结构 {@link ElectronicCardInvalidRequest}
	 * @return
	 */
	@Override
	public BaseResponse<ElectronicCardModifyCountResponse> countEffectiveCoupon(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest) {
		Long count = electronicCardService.countEffectiveCoupon(electronicCardInvalidRequest.getCouponId(), electronicCardInvalidRequest.getTime());
		return BaseResponse.success(ElectronicCardModifyCountResponse.builder()
				.count(count)
				.build());
	}

	@Override
	public BaseResponse<ElectronicCardByIdResponse> findById(@RequestBody @Valid ElectronicCardByIdRequest electronicCardByIdRequest) {
		ElectronicCard card = electronicCardService.getOne(electronicCardByIdRequest.getId());
		ElectronicCardVO cardVO = KsBeanUtil.convert(card, ElectronicCardVO.class);
		if (cardVO != null) {
			ElectronicCoupon coupon = electronicCouponService.getOne(card.getCouponId());
			if (coupon != null) {
				cardVO.setStoreId(coupon.getStoreId());
			}
		}
		return BaseResponse.success(ElectronicCardByIdResponse.builder().electronicCard(cardVO).build());
	}

	@Override
	public BaseResponse<ElectronicCardNumByOrderNoResponse> countByOrderNoAndCouponId(@RequestBody @Valid ElectronicCardNumByOrderNoRequest request) {
		Integer num = electronicCardService.countByOrderNoAndCouponId(request.getOrderNo(), request.getCouponId());
		return BaseResponse.success(new ElectronicCardNumByOrderNoResponse(num));
	}
}

