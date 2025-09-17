package com.wanmi.sbc.goods.provider.impl.buycyclegoodsinfo;

import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoPageResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoListResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoExportResponse;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoPageVO;
import com.wanmi.sbc.goods.buycyclegoodsinfo.service.BuyCycleGoodsInfoService;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>周期购sku表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@RestController
@Validated
public class BuyCycleGoodsInfoQueryController implements BuyCycleGoodsInfoQueryProvider {
	@Autowired
	private BuyCycleGoodsInfoService buyCycleGoodsInfoService;

	@Override
	public BaseResponse<BuyCycleGoodsInfoPageResponse> page(@RequestBody @Valid BuyCycleGoodsInfoPageRequest buyCycleGoodsInfoPageReq) {
		BuyCycleGoodsInfoQueryRequest queryReq = KsBeanUtil.convert(buyCycleGoodsInfoPageReq, BuyCycleGoodsInfoQueryRequest.class);
		Page<BuyCycleGoodsInfo> buyCycleGoodsInfoPage = buyCycleGoodsInfoService.page(queryReq);
		Page<BuyCycleGoodsInfoVO> newPage = buyCycleGoodsInfoPage.map(entity -> buyCycleGoodsInfoService.wrapperVo(entity));
		MicroServicePage<BuyCycleGoodsInfoVO> microPage = new MicroServicePage<>(newPage, buyCycleGoodsInfoPageReq.getPageable());
		BuyCycleGoodsInfoPageResponse finalRes = new BuyCycleGoodsInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<BuyCycleGoodsInfoListResponse> list(@RequestBody @Valid BuyCycleGoodsInfoListRequest buyCycleGoodsInfoListReq) {
		BuyCycleGoodsInfoQueryRequest queryReq = KsBeanUtil.convert(buyCycleGoodsInfoListReq, BuyCycleGoodsInfoQueryRequest.class);
		List<BuyCycleGoodsInfo> buyCycleGoodsInfoList = buyCycleGoodsInfoService.list(queryReq);
		List<BuyCycleGoodsInfoVO> newList = buyCycleGoodsInfoList.stream().map(entity -> buyCycleGoodsInfoService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new BuyCycleGoodsInfoListResponse(newList));
	}

	@Override
	public BaseResponse<BuyCycleGoodsInfoByIdResponse> getById(@RequestBody @Valid BuyCycleGoodsInfoByGoodsIdRequest buyCycleGoodsInfoByIdRequest) {
		BuyCycleGoodsInfo buyCycleGoodsInfo =
		buyCycleGoodsInfoService.getOne(buyCycleGoodsInfoByIdRequest.getGoodsInfoId());
		return BaseResponse.success(new BuyCycleGoodsInfoByIdResponse(buyCycleGoodsInfoService.wrapperVo(buyCycleGoodsInfo)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid BuyCycleGoodsInfoExportRequest request) {
		BuyCycleGoodsInfoQueryRequest queryReq = KsBeanUtil.convert(request, BuyCycleGoodsInfoQueryRequest.class);
		Long total = buyCycleGoodsInfoService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<BuyCycleGoodsInfoExportResponse> exportBuyCycleGoodsInfoRecord(@RequestBody @Valid BuyCycleGoodsInfoExportRequest request) {
		BuyCycleGoodsInfoQueryRequest queryReq = KsBeanUtil.convert(request, BuyCycleGoodsInfoQueryRequest.class);
		List<BuyCycleGoodsInfoPageVO> data = KsBeanUtil.convert(buyCycleGoodsInfoService.page(queryReq).getContent(),BuyCycleGoodsInfoPageVO.class);
		return BaseResponse.success(new BuyCycleGoodsInfoExportResponse(data));
	}

	@Override
	public BaseResponse<BuyCycleGoodsInfoByIdResponse> findById(@RequestBody @Valid BuyCycleGoodsInfoByIdRequest buyCycleGoodsInfoByIdRequest) {
		BuyCycleGoodsInfo buyCycleGoodsInfo =
				buyCycleGoodsInfoService.getOne(buyCycleGoodsInfoByIdRequest.getId());
		return BaseResponse.success(new BuyCycleGoodsInfoByIdResponse(buyCycleGoodsInfoService.wrapperVo(buyCycleGoodsInfo)));
	}

	@Override
	public BaseResponse validate(@RequestBody @Valid BuyCycleGoodsInfoValidateRequest request) {
		buyCycleGoodsInfoService.validate(request);
		return BaseResponse.SUCCESSFUL();
	}
}

