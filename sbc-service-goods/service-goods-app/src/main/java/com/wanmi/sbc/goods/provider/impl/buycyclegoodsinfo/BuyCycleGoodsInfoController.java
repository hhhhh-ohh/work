package com.wanmi.sbc.goods.provider.impl.buycyclegoodsinfo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoAddResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoodsinfo.BuyCycleGoodsInfoModifyResponse;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoDelByIdRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoDelByIdListRequest;
import com.wanmi.sbc.goods.buycyclegoodsinfo.service.BuyCycleGoodsInfoService;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>周期购sku表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@RestController
@Validated
public class BuyCycleGoodsInfoController implements BuyCycleGoodsInfoProvider {
	@Autowired
	private BuyCycleGoodsInfoService buyCycleGoodsInfoService;

	@Override
	public BaseResponse<BuyCycleGoodsInfoAddResponse> add(@RequestBody @Valid BuyCycleGoodsInfoAddRequest buyCycleGoodsInfoAddRequest) {
		BuyCycleGoodsInfo buyCycleGoodsInfo = KsBeanUtil.convert(buyCycleGoodsInfoAddRequest, BuyCycleGoodsInfo.class);
		return BaseResponse.success(new BuyCycleGoodsInfoAddResponse(
				buyCycleGoodsInfoService.wrapperVo(buyCycleGoodsInfoService.add(buyCycleGoodsInfo))));
	}

	@Override
	public BaseResponse<BuyCycleGoodsInfoModifyResponse> modify(@RequestBody @Valid BuyCycleGoodsInfoModifyRequest buyCycleGoodsInfoModifyRequest) {
		BuyCycleGoodsInfo buyCycleGoodsInfo = KsBeanUtil.convert(buyCycleGoodsInfoModifyRequest, BuyCycleGoodsInfo.class);
		return BaseResponse.success(new BuyCycleGoodsInfoModifyResponse(
				buyCycleGoodsInfoService.wrapperVo(buyCycleGoodsInfoService.modify(buyCycleGoodsInfo))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid BuyCycleGoodsInfoDelByIdRequest buyCycleGoodsInfoDelByIdRequest) {
		BuyCycleGoodsInfo buyCycleGoodsInfo = KsBeanUtil.convert(buyCycleGoodsInfoDelByIdRequest, BuyCycleGoodsInfo.class);
		buyCycleGoodsInfo.setDelFlag(DeleteFlag.YES);
		buyCycleGoodsInfoService.deleteById(buyCycleGoodsInfo);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid BuyCycleGoodsInfoDelByIdListRequest buyCycleGoodsInfoDelByIdListRequest) {
		buyCycleGoodsInfoService.deleteByIdList(buyCycleGoodsInfoDelByIdListRequest.getIds());
		return BaseResponse.SUCCESSFUL();
	}

}

