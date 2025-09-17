package com.wanmi.sbc.marketing.provider.impl.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponListResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponPageResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicSendRecordNumResponse;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordNumDTO;
import com.wanmi.sbc.marketing.bean.enums.CardSaleState;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCardService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCouponService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>电子卡券表查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@RestController
@Validated
public class ElectronicCouponQueryController implements ElectronicCouponQueryProvider {
	@Autowired
	private ElectronicCouponService electronicCouponService;

	@Autowired
	private ElectronicCardService electronicCardService;

	@Autowired
	private ElectronicSendRecordService electronicSendRecordService;

	@Override
	public BaseResponse<ElectronicCouponPageResponse> page(@RequestBody @Valid ElectronicCouponPageRequest electronicCouponPageReq) {
		ElectronicCouponQueryRequest queryReq = KsBeanUtil.convert(electronicCouponPageReq, ElectronicCouponQueryRequest.class);
		Page<ElectronicCoupon> electronicCouponPage = electronicCouponService.page(queryReq);
		Page<ElectronicCouponVO> newPage = electronicCouponPage.map(entity -> electronicCouponService.wrapperVo(entity));
		MicroServicePage<ElectronicCouponVO> microPage = new MicroServicePage<>(newPage, electronicCouponPageReq.getPageable());
		ElectronicCouponPageResponse finalRes = new ElectronicCouponPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<ElectronicCouponListResponse> list(@RequestBody @Valid ElectronicCouponListRequest electronicCouponListReq) {
		ElectronicCouponQueryRequest queryReq = KsBeanUtil.convert(electronicCouponListReq, ElectronicCouponQueryRequest.class);
		List<ElectronicCoupon> electronicCouponList = electronicCouponService.list(queryReq);
		//根据有无过销售期过滤卡券
		if (electronicCouponListReq.getSaleType() != null) {
			List<Long> ids = electronicCouponList.stream().map(ElectronicCoupon::getId).collect(Collectors.toList());
			//符合条件的卡券id
			List<Long> filterIds = electronicCardService.listBySaleType(ids, electronicCouponListReq.getSaleType());
			electronicCouponList = electronicCouponList.stream()
					.filter(electronicCoupon -> filterIds.contains(electronicCoupon.getId()))
					.collect(Collectors.toList());
		}
		List<ElectronicCouponVO> newList = electronicCouponList.stream().map(entity -> electronicCouponService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new ElectronicCouponListResponse(newList));
	}

	@Override
	public BaseResponse<ElectronicCouponByIdResponse> getById(@RequestBody @Valid ElectronicCouponByIdRequest electronicCouponByIdRequest) {
		ElectronicCoupon electronicCoupon =
		electronicCouponService.getOne(electronicCouponByIdRequest.getId());
		return BaseResponse.success(new ElectronicCouponByIdResponse(electronicCouponService.wrapperVo(electronicCoupon)));
	}

	@Override
	public BaseResponse<ElectronicSendRecordNumResponse> goodsCardInfo(@RequestBody @Valid ElectronicSendRecordNumRequest electronicSendRecordNumRequest) {
		Map<String, Long> sendMap = electronicSendRecordService.countGoodsSendNum(electronicSendRecordNumRequest.getDtos());
		List<Long> couponIds = electronicSendRecordNumRequest.getDtos().stream().map(ElectronicSendRecordNumDTO::getCouponId).collect(Collectors.toList());
		Map<Long, CardSaleState> saleStateMap = electronicCardService.getSaleStateMap(couponIds);
		return BaseResponse.success(ElectronicSendRecordNumResponse.builder().sendMap(sendMap).saleStateMap(saleStateMap).build());
	}
}

