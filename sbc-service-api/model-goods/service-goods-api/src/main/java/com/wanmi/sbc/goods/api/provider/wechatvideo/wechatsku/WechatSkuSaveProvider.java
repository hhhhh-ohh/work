package com.wanmi.sbc.goods.api.provider.wechatvideo.wechatsku;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.*;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatSkuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>微信视频号带货商品保存服务Provider</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@FeignClient(value = "${application.goods.name}", contextId = "WechatSkuSaveProvider")
public interface WechatSkuSaveProvider {

	/**
	 * 新增微信视频号带货商品API
	 *
	 * @author 
	 * @param wechatSkuAddRequest 微信视频号带货商品新增参数结构 {@link WechatSkuAddRequest}
	 * @return 新增的微信视频号带货商品信息 {@link WechatSkuVO}
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/add")
	BaseResponse<WechatSkuVO> add(@RequestBody @Valid WechatSkuAddRequest wechatSkuAddRequest);

	/**
	 * 重新添加
	 * @param wechatSkuAddRequest
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/reAdd")
	BaseResponse<WechatSkuVO> reAdd(@RequestBody @Valid WechatSkuAddRequest wechatSkuAddRequest);

	/**
	 * 单个删除微信视频号带货商品API
	 *
	 * @author 
	 * @param wechatSkuDelByGoodsIdRequest 单个删除参数结构 {@link WechatSkuDelByGoodsIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/delete-by-goodsId")
	BaseResponse deleteByGoodsId(@RequestBody @Valid WechatSkuDelByGoodsIdRequest wechatSkuDelByGoodsIdRequest);


	/**
	 * 处理微信审核回调
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/audit")
	BaseResponse audit(@RequestBody @Valid AuditRequest request);

	/**
	 * 处理微信下架回调
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/dealDown")
	BaseResponse dealDown(@RequestBody @Valid DownRequest request);

	/**
	 * 更新上下架状态
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/updateShelve")
	BaseResponse updateShelve(@RequestBody @Valid UpdateShelve request);

	/**
	 * 同步微信商品库存
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/syncStock")
	BaseResponse syncStock();

}

