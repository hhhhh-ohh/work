package com.wanmi.sbc.goods.api.provider.wechatvideo.wechatsku;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuByIdRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatSkuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
/**
 * <p>微信视频号带货商品查询服务Provider</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@FeignClient(value = "${application.goods.name}", contextId = "WechatSkuQueryProvider")
public interface WechatSkuQueryProvider {

	/**
	 * 分页查询微信视频号带货商品API
	 *
	 * @author 
	 * @param wechatSkuPageReq 分页请求参数和筛选对象 {@link WechatSkuQueryRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/page")
	BaseResponse<MicroServicePage<WechatSkuVO>> page(@RequestBody @Valid WechatSkuQueryRequest wechatSkuPageReq);

	/**
	 * 列表查询微信视频号带货商品API
	 *
	 * @author 
	 * @param wechatSkuListReq 列表请求参数和筛选对象 {@link WechatSkuQueryRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/list")
	BaseResponse<List<WechatSkuVO>> list(@RequestBody @Valid WechatSkuQueryRequest wechatSkuListReq);

	@PostMapping("/goods/${application.goods.version}/wechatsku/listGoodsInfoId")
	BaseResponse<List<String>> listGoodsId(@RequestBody WechatSkuQueryRequest wechatSkuListReq);

	/**
	 * 单个查询微信视频号带货商品API
	 *
	 * @author 
	 * @param wechatSkuByIdRequest 单个查询微信视频号带货商品请求参数 {@link WechatSkuByIdRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/wechatsku/get-by-id")
	BaseResponse<WechatSkuVO> getById(@RequestBody @Valid WechatSkuByIdRequest wechatSkuByIdRequest);

}

