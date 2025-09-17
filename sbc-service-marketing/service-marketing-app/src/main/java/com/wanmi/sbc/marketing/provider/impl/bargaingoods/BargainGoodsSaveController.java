package com.wanmi.sbc.marketing.provider.impl.bargaingoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.*;
import com.wanmi.sbc.marketing.bargaingoods.service.BargainGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>砍价商品保存服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@RestController
@Validated
public class BargainGoodsSaveController implements BargainGoodsSaveProvider {
	@Autowired
	private BargainGoodsService bargainGoodsService;

	/**
	 * @description 新增砍价商品
	 * @author  lipeixian
	 * @date 2022/5/23 8:03 下午
	 * @param bargainGoodsAddRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@Override
	public BaseResponse add(@RequestBody @Valid BargainGoodsActivityAddRequest bargainGoodsAddRequest) {
		bargainGoodsService.add(bargainGoodsAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid BargainGoodsModifyRequest bargainGoodsModifyRequest) {
		bargainGoodsService.modify(bargainGoodsModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}


	/**
	 * @description 砍价商品审核
	 * @author  lipeixian
	 * @date 2022/5/23 8:04 下午
	 * @param bargainCheckRequest
	 * @return void
	 **/
	@Override
	public BaseResponse bargainGoodsCheck(@RequestBody @Valid BargainCheckRequest bargainCheckRequest) {
		bargainGoodsService.bargainGoodsCheck(bargainCheckRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse bargainGoodsSystemCheck(@RequestBody @Valid BargainCheckRequest bargainCheckRequest) {
		bargainGoodsService.bargainGoodsSystemCheck(bargainCheckRequest);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * @description 砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:41 下午
	 * @param terminalActivityRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@Override
	public BaseResponse terminalActivity(@RequestBody @Valid TerminalActivityRequest terminalActivityRequest) {
		bargainGoodsService.terminalActivity(terminalActivityRequest);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * @description 店铺-砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:41 下午
	 * @param storeTerminalActivityRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@Override
	public BaseResponse storeTerminalActivity(@RequestBody @Valid StoreTerminalActivityRequest storeTerminalActivityRequest) {
		bargainGoodsService.terminalActivity(storeTerminalActivityRequest);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * @description 删除砍价活动
	 * @author  lipeixian
	 * @date 2022/5/24 8:44 上午
	 * @param terminalActivityRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@Override
	public BaseResponse deleteBargainGoods(@RequestBody @Valid TerminalActivityRequest terminalActivityRequest) {
		bargainGoodsService.deleteBargainGoods(terminalActivityRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateGoodsStatus(@RequestBody @Valid UpdateGoodsStatusRequest request) {
		bargainGoodsService.updateGoodsStatus(request);
		return BaseResponse.SUCCESSFUL();
	}

}

