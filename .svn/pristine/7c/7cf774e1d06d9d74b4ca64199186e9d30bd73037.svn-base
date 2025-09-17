package com.wanmi.sbc.marketing.api.provider.bargaingoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.bargaingoods.*;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>砍价商品保存服务Provider</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainGoodsSaveProvider")
public interface BargainGoodsSaveProvider {

	/**
	 * 新增砍价商品API
	 *
	 * @param addRequest 砍价商品新增参数结构 {@link BargainGoodsActivityAddRequest}
	 * @return 新增的砍价商品信息 {@link BargainGoodsVO}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/add")
	BaseResponse<BargainGoodsVO> add(@RequestBody @Valid BargainGoodsActivityAddRequest addRequest);

	/**
	 * 新增砍价商品API
	 *
	 * @param bargainGoodsAddRequest 砍价商品修改参数结构 {@link BargainGoodsAddRequest}
	 * @return 修改的砍价商品信息 {@link BargainGoodsVO}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/modify")
	BaseResponse modify(@RequestBody @Valid BargainGoodsModifyRequest bargainGoodsAddRequest);

	/**
	 * @description  砍价商品审核
	 * @author  lipeixian
	 * @date 2022/5/23 8:02 下午
	 * @param bargainCheckRequest
	 * @return void
	 **/
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/check")
	BaseResponse bargainGoodsCheck(@RequestBody @Valid BargainCheckRequest bargainCheckRequest);

	/**
	 * @description  砍价商品批量审核
	 * @author  lipeixian
	 * @date 2022/5/23 8:02 下午
	 * @param bargainCheckRequest
	 * @return void
	 **/
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/system/check")
	BaseResponse bargainGoodsSystemCheck(@RequestBody @Valid BargainCheckRequest bargainCheckRequest);

	/**
	 * @description 砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:40 下午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/terminal")
	BaseResponse terminalActivity(@RequestBody @Valid TerminalActivityRequest request);

	/**
	 * @description 店铺关店-砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:40 下午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/store-terminal")
	BaseResponse storeTerminalActivity(@RequestBody @Valid StoreTerminalActivityRequest request);

	/**
	 * @description 删除砍价活动
	 * @author  lipeixian
	 * @date 2022/5/24 8:43 上午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/delete")
	BaseResponse deleteBargainGoods(@RequestBody @Valid TerminalActivityRequest request);

	/**
	 * 处理商品可售状态
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/update-goods-status")
	BaseResponse updateGoodsStatus(@RequestBody @Valid UpdateGoodsStatusRequest request);


}

