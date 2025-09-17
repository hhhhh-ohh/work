package com.wanmi.sbc.goods.api.provider.goodsaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyAllRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.EditLevelPriceRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditModifyRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditModifyResponse;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商品审核保存服务Provider</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsAuditProvider")
public interface GoodsAuditProvider {

	/**
	 * 修改商品审核API
	 *
	 * @author 黄昭
	 * @param goodsAuditModifyRequest 商品审核修改参数结构 {@link GoodsAuditModifyRequest}
	 * @return 修改的商品审核信息 {@link GoodsAuditModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/modify")
	BaseResponse<GoodsAuditModifyResponse> modify(@RequestBody @Valid GoodsAuditModifyRequest goodsAuditModifyRequest);

	/**
	 * 批量删除商品审核API
	 *
	 * @author 黄昭
	 * @param goodsAuditDelByIdListRequest 批量删除参数结构 {@link GoodsAuditDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsAuditDelByIdListRequest goodsAuditDelByIdListRequest);

	/**
	 * 批量修改审核商品等级价
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/edit-level-price")
	BaseResponse editLevelPrice(@RequestBody @Valid EditLevelPriceRequest request);

	/**
	 * 批量修改审核商品批发价
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/edit-stock-price")
	BaseResponse editStockPrice(@RequestBody @Valid EditLevelPriceRequest request);
}

